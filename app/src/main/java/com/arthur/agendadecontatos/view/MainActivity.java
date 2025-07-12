package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.arthur.agendadecontatos.R;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        drawerLayout = findViewById(R.id.drawerLayout);
        ImageButton btnMenu = findViewById(R.id.imageButtonFotoContato);
        navigationView = findViewById(R.id.navigationView);
        Button btnAdicionarContato = findViewById(R.id.btnAdicionarContato);
        Button btnListaContatos = findViewById(R.id.btnListaContatos);

        carregarFotoUsuario();

        btnMenu.setOnClickListener(v -> {
            // Abre o menu lateral da esquerda (start)
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        btnAdicionarContato.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AdicionarContato.class)));

        btnListaContatos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListaContatos.class)));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_sair) {
                // Logout, voltar para Login
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void carregarFotoUsuario() {
        ImageView imageViewFotoContato = findViewById(R.id.imageButtonFotoContato);

        // Pega header do NavigationView e a imagem dele
        View headerView = navigationView.getHeaderView(0);
        ImageView header_image = headerView.findViewById(R.id.header_image);

        SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
        String caminhoFoto = prefs.getString("fotoUsuario", null);

        Uri uri = null;
        if (caminhoFoto != null) {
            File arquivoImagem = new File(caminhoFoto);
            if (arquivoImagem.exists()) {
                uri = Uri.fromFile(arquivoImagem);
            }
        }

        // Carrega foto com Glide - botão menu e header
        Glide.with(this)
                .load(uri != null ? uri : R.drawable.foto_de_perfil)
                .circleCrop()
                .placeholder(R.drawable.foto_de_perfil)
                .into(imageViewFotoContato);

        Glide.with(this)
                .load(uri != null ? uri : R.drawable.foto_de_perfil)
                .circleCrop()
                .placeholder(R.drawable.foto_de_perfil)
                .into(header_image);
    }
}
