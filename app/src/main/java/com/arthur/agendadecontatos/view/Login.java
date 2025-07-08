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
import com.arthur.agendadecontatos.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        DBController_Agenda dbController_Agenda = new DBController_Agenda(this);

        TextInputEditText editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        TextInputEditText editTextPassword = findViewById(R.id.editTextPassword);
        Button btnCadastrar = findViewById(R.id.btnCadastrarLogin);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = editTextEmailAddress.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean usuarioEncontrado = false;
            for (Usuario usuario : dbController_Agenda.listarUsuarios()) {
                if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                    usuarioEncontrado = true;

                    int usuarioId = dbController_Agenda.buscarIdUsuarioPorNome(usuario.getNomeCompleto());

                    SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("usuarioId", usuarioId);
                    editor.apply();

                    // Associa todos os contatos em memória (Lista) ao usuário logado
                    for (Contato contato : com.arthur.agendadecontatos.model.Lista.getInstance().getContatos()) {
                        contato.setUsuarioId(usuarioId);
                        dbController_Agenda.adicionarContato(contato);
                    }

                    // Limpa a lista em memória depois de salvar
                    com.arthur.agendadecontatos.model.Lista.getInstance().limpar();

                    Toast.makeText(Login.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                if (!usuarioEncontrado) {
                    Toast.makeText(Login.this, "Usuario ou senha nao encontrado", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this, "Clique no botao abaixo para se cadastrar", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
            finish();
        });
    }
}
