package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Usuario;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 2500; // 2.5 segundos
    private DBController_Agenda dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logoImage);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        logoImage.startAnimation(fadeIn);

        dbController = new DBController_Agenda(this);

        new Handler().postDelayed(this::verificarUsuarios, SPLASH_DURATION);
    }

    private void verificarUsuarios() {
        List<Usuario> usuarios = dbController.listarUsuarios();

        Intent intent;
        if (usuarios != null && !usuarios.isEmpty()) {
            // Usuário(s) existente(s)
            intent = new Intent(this, Login.class);
        } else {
            // Nenhum usuário → precisa se cadastrar
            intent = new Intent(this, Cadastro.class);
        }

        startActivity(intent);
        finish();
    }
}
