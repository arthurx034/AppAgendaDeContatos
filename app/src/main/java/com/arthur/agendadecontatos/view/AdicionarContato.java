package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Contato;

public class AdicionarContato extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextSobrenome;
    private Spinner spinPais;
    private EditText editTextCelular;

    private DBController_Agenda dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_contato);

        editTextNome = findViewById(R.id.editTextNome);
        editTextSobrenome = findViewById(R.id.editTextSobrenome);
        spinPais = findViewById(R.id.spinPais);
        editTextCelular = findViewById(R.id.editTextCelular);
        Button buttonAdicionarContato = findViewById(R.id.buttonAdicionarContato);
        ImageButton imagemButtonVoltar = findViewById(R.id.imageButtonVoltar);

        dbController = new DBController_Agenda(this);

        buttonAdicionarContato.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString().trim();
            String sobrenome = editTextSobrenome.getText().toString().trim();
            String celular = editTextCelular.getText().toString().trim();
            String pais = (String) spinPais.getSelectedItem();

            if (nome.isEmpty() || sobrenome.isEmpty() || celular.isEmpty() || pais == null || pais.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pega o ID do usuário logado do SharedPreferences
            int usuarioId = getSharedPreferences("usuarioLogado", MODE_PRIVATE).getInt("usuarioId", -1);
            if (usuarioId == -1) {
                Toast.makeText(this, "Usuário não está logado.", Toast.LENGTH_SHORT).show();
                return;
            }

            Contato novoContato = new Contato(nome, sobrenome, pais, celular, usuarioId);
            dbController.adicionarContato(novoContato);

            Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra("novoContato", nome + " " + sobrenome);
            setResult(RESULT_OK, intent);

            finish();
        });

        imagemButtonVoltar.setOnClickListener(v -> finish());
    }
}
