package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Contato;
import com.arthur.agendadecontatos.model.Lista;
import com.arthur.agendadecontatos.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        DBController_Agenda dbController = new DBController_Agenda(this);

        TextInputEditText editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        TextInputEditText editTextPassword = findViewById(R.id.editTextPassword);
        Button btnCadastrar = findViewById(R.id.btnCadastrarLogin);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = Objects.requireNonNull(editTextEmailAddress.getText()).toString().trim();
            String senha = Objects.requireNonNull(editTextPassword.getText()).toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuarioEncontrado = null;
            for (Usuario usuario : dbController.listarUsuarios()) {
                if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                    usuarioEncontrado = usuario;
                    break;
                }
            }

            if (usuarioEncontrado != null) {
                // Salva o id do usuário logado no SharedPreferences
                SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
                prefs.edit().putInt("usuarioId", usuarioEncontrado.getId()).apply();

                // Salva contatos temporários, se houver, associando ao usuário atual
                if (!Lista.getInstance().getContatos().isEmpty()) {
                    for (Contato contato : Lista.getInstance().getContatos()) {
                        contato.setUsuarioId(usuarioEncontrado.getId());
                        dbController.adicionarContato(contato);
                    }
                    Lista.getInstance().limpar();
                }

                Toast.makeText(Login.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Usuário ou senha não encontrados", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
            finish();
        });
    }
}
