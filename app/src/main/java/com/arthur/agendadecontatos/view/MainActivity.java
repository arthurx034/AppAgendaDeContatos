package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageViewFotoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdicionarContato = findViewById(R.id.btnAdicionarContato);
        Button btnListaContatos = findViewById(R.id.btnListaContatos);
        imageViewFotoContato = findViewById(R.id.imageViewFotoContato); // pega o botão de imagem

        carregarFotoUsuario(); // carrega a imagem da galeria ou câmera, se houver

        btnAdicionarContato.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarContato.class);
            startActivity(intent);
        });

        btnListaContatos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaContatos.class);
            startActivity(intent);
        });
    }

    private void carregarFotoUsuario() {
        SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
        String caminhoFoto = prefs.getString("fotoUsuario", null);

        if (caminhoFoto != null) {
            File arquivoImagem = new File(caminhoFoto);
            if (arquivoImagem.exists()) {
                Uri uri = Uri.fromFile(arquivoImagem);
                imageViewFotoContato.setImageURI(uri);
            } else {
                imageViewFotoContato.setImageResource(R.drawable.foto_de_perfil); // imagem padrão
            }
        } else {
            imageViewFotoContato.setImageResource(R.drawable.foto_de_perfil); // imagem padrão
        }
    }
}
