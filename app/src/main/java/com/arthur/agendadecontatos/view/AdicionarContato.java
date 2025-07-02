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
import com.arthur.agendadecontatos.controller.DBController;
import com.arthur.agendadecontatos.model.Contato;
import com.arthur.agendadecontatos.model.Lista;

public class AdicionarContato extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextSobrenome;
    private Spinner spinPais;
    private EditText editTextCelular;
    private Button buttonAdicionarContato;
    private Lista lista;
    private ImageButton imagemButtonVoltar;

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

        lista = new Lista();

        buttonAdicionarContato.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString().trim();
            String sobrenome = editTextSobrenome.getText().toString().trim();
            String celular = editTextCelular.getText().toString().trim();
            String pais = (String) spinPais.getSelectedItem();

            if (nome.isEmpty() || sobrenome.isEmpty() || celular.isEmpty() || pais == null || pais.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aqui celular é String, então não precisa converter para int

            DBController dbController = new DBController(this);
            dbController.adicionarContato(new Contato(nome, sobrenome, pais, celular));
            Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish();

            Intent intent = new Intent();
            intent.putExtra("novoContato", nome + " " + sobrenome);
            setResult(RESULT_OK, intent);
            finish();


            // Limpa campos após adicionar
            editTextNome.setText("");
            editTextSobrenome.setText("");
            editTextCelular.setText("");
            spinPais.setSelection(0);
        });

        imagemButtonVoltar.setOnClickListener(v -> finish());
    }
}
