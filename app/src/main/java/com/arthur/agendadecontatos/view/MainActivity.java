package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAdicionarContato, btnListaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdicionarContato = findViewById(R.id.btnAdicionarContato);
        Button btnListaContatos = findViewById(R.id.btnListaContatos);

        btnAdicionarContato.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarContato.class);
            startActivity(intent);
        });

        btnListaContatos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaContatos.class);
            startActivity(intent);
        });
    }
}
