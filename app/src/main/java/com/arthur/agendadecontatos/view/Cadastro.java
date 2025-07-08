package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Contato;
import com.arthur.agendadecontatos.model.Lista;
import com.arthur.agendadecontatos.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Cadastro extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    private ImageView imageViewFotoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        Lista.getInstance().limpar();

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSelecionarFoto = findViewById(R.id.btnSelecionarFoto);
        imageViewFotoContato = findViewById(R.id.imageViewFotoContato);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        EditText editTextNome = findViewById(R.id.editTextNomeCompleto);
        EditText editTextSenha = findViewById(R.id.editTextTextPassword);
        EditText editTextTelefone = findViewById(R.id.editTextTelefoneContato);
        Spinner spinnerPais = findViewById(R.id.spinPais);
        EditText editTextEmail = findViewById(R.id.editTextTextEmailAddress);

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
            String pais = (String) spinnerPais.getSelectedItem();

            Drawable drawable = imageViewFotoContato.getDrawable();
            boolean imagemValida = imageViewFotoContato.getTag() != null &&
                    !((BitmapDrawable) drawable).getBitmap()
                            .sameAs(((BitmapDrawable) getResources().getDrawable(R.drawable.foto_de_perfil)).getBitmap());

            if (nome.isEmpty() || senha.isEmpty() || telefone.isEmpty() || email == null || pais == null || pais.isEmpty() || !imagemValida) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            String imageView = salvarImagemEmArquivo(bitmap, nome);

            DBController_Agenda dbControllerAgenda = new DBController_Agenda(this);
            Usuario novoUsuario = new Usuario(imageView, nome, senha, telefone, email, pais);
            dbControllerAgenda.cadastrarUsuario(novoUsuario);
            int usuarioId = dbControllerAgenda.buscarIdUsuarioPorNome(nome);

            // ✅ Corrigido: salva o novo usuário no SharedPreferences
            SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
            prefs.edit().putInt("usuarioId", usuarioId).apply();

            // ✅ Garante que só contatos da sessão atual são salvos
            // Somente salva se a lista estiver vazia (nenhum contato anterior)
            if (!Lista.getInstance().getContatos().isEmpty()) {
                for (Contato contato : Lista.getInstance().getContatos()) {
                    contato.setUsuarioId(usuarioId);
                    dbControllerAgenda.adicionarContato(contato);
                }
                Lista.getInstance().limpar(); // limpa só se usou
            }


            // ✅ Limpa os contatos temporários da memória
            Lista.getInstance().limpar();

            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Cadastro.this, MainActivity.class);
            intent.putExtra("novoCadastro", nome);
            setResult(RESULT_OK, intent);
            startActivity(intent);

            // Limpa os campos visuais
            imageViewFotoContato.setImageResource(R.drawable.foto_de_perfil);
            editTextNome.setText("");
            editTextSenha.setText("");
            editTextTelefone.setText("");
            editTextEmail.setText("");
            spinnerPais.setSelection(0);
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Login.class);
            startActivity(intent);
            finish();
        });
    }


    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "FotoContato", null);
        return Uri.parse(path);
    }

    private String salvarImagemEmArquivo(Bitmap bitmap, String nomeArquivo) {
        File diretorio = new File(getFilesDir(), "usuarios");
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // cria a pasta se não existir
        }

        File arquivoImagem = new File(diretorio, nomeArquivo + ".jpg");

        try (FileOutputStream out = new FileOutputStream(arquivoImagem)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            return arquivoImagem.getAbsolutePath(); // caminho completo da imagem
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
                // Imagem da galeria
                Uri imageUri = data.getData();
                imageViewFotoContato.setImageURI(imageUri);
                imageViewFotoContato.setTag("imagem_selecionada"); // Marcar que imagem foi escolhida

            } else if (requestCode == CAMERA_REQUEST && data != null && data.getExtras() != null) {
                // Foto tirada pela câmera
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                imageViewFotoContato.setImageBitmap(foto);
                imageViewFotoContato.setTag("imagem_selecionada"); // Marcar que imagem foi escolhida

                // Opcional: salvar no MediaStore e obter URI
                Uri uri = getImageUri(foto);
            }
        }
    }
}
