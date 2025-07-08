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
    private Button buttonAdicionarContato;
    private ImageButton imagemButtonVoltar;

    private DBController_Agenda dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_contato);

        editTextNome = findViewById(R.id.editTextNome);
        editTextSobrenome = findViewById(R.id.editTextSobrenome);
        spinPais = findViewById(R.id.spinPais);
        editTextCelular = findViewById(R.id.editTextCelular);
        buttonAdicionarContato = findViewById(R.id.buttonAdicionarContato);
        imagemButtonVoltar = findViewById(R.id.imageButtonVoltar);

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

            // 🔒 Pegue o ID do usuário logado ou um ID válido
            int usuarioId = 1; // Exemplo fixo — substitua com ID real do usuário logado

            Contato novoContato = new Contato(nome, sobrenome, pais, celular, usuarioId);
            dbController.adicionarContato(novoContato);

            Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();

            // Retorna nome do novo contato
            Intent intent = new Intent();
            intent.putExtra("novoContato", nome + " " + sobrenome);
            setResult(RESULT_OK, intent);

            finish(); // finaliza e volta para tela anterior
        });

        imagemButtonVoltar.setOnClickListener(v -> finish());
    }
}
