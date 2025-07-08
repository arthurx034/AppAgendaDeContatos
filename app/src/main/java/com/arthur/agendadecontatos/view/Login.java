package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            // Aqui, após validação, você pode abrir a MainActivity ou outra tela
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
