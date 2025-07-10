package com.arthur.agendadecontatos.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Lista;
import com.arthur.agendadecontatos.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;

public class Cadastro extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    private ImageView imageViewFotoContato;

    private boolean isEmailValido(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(regex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Lista.getInstance().limpar();

        Button btnSelecionarFoto = findViewById(R.id.btnSelecionarFoto);
        imageViewFotoContato = findViewById(R.id.imageViewFotoContato);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        EditText editTextNome = findViewById(R.id.editTextNomeCompleto);
        EditText editTextSenha = findViewById(R.id.editTextTextPassword);
        EditText editTextTelefone = findViewById(R.id.editTextTelefoneContato);
        Spinner spinnerPais = findViewById(R.id.spinPais);
        EditText editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        btnSelecionarFoto.setOnClickListener(v -> {
            String[] options = {"Galeria", "Câmera"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecionar Foto");
            builder.setItems(options, (dialog, which) -> {
                if (which == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            });
            builder.show();
        });

        btnCadastrar.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString().trim();
            String senha = editTextSenha.getText().toString().trim();
            String telefone = editTextTelefone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String pais = (spinnerPais.getSelectedItem() != null) ? spinnerPais.getSelectedItem().toString().trim() : "";

            Drawable drawable = imageViewFotoContato.getDrawable();
            @SuppressLint("UseCompatLoadingForDrawables") boolean imagemValida = imageViewFotoContato.getTag() != null &&
                    !((BitmapDrawable) drawable).getBitmap().sameAs(
                            ((BitmapDrawable) getResources().getDrawable(R.drawable.foto_de_perfil)).getBitmap()
                    );

            if (!isEmailValido(email)) {
                Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nome.isEmpty() || senha.isEmpty() || telefone.isEmpty() || email.isEmpty() || pais.isEmpty() || !imagemValida) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            String imageViewPath = salvarImagemEmArquivo(bitmap, nome);

            DBController_Agenda db = new DBController_Agenda(this);
            Usuario novoUsuario = new Usuario(imageViewPath, nome, senha, telefone, email, pais);
            db.cadastrarUsuario(novoUsuario);
            int usuarioId = db.buscarIdUsuarioPorNome(nome);

            SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("usuarioId", usuarioId);
            editor.putString("fotoUsuario", imageViewPath); // salva o caminho da imagem
            editor.apply();


            Lista.getInstance().limpar();

            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Cadastro.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private String salvarImagemEmArquivo(Bitmap bitmap, String nomeArquivo) {
        File diretorio = new File(getFilesDir(), "usuarios");
        if (!diretorio.exists()) diretorio.mkdirs();

        File arquivoImagem = new File(diretorio, nomeArquivo + ".jpg");
        try (FileOutputStream out = new FileOutputStream(arquivoImagem)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            return arquivoImagem.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageViewFotoContato.setImageURI(data.getData());
                imageViewFotoContato.setTag("imagem_selecionada");
            } else if (requestCode == CAMERA_REQUEST && data != null && data.getExtras() != null) {
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                imageViewFotoContato.setImageBitmap(foto);
                imageViewFotoContato.setTag("imagem_selecionada");
            }
        }
    }
}
