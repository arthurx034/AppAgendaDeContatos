package com.arthur.agendadecontatos.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.model.ListaContatos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdicionarContato extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextSobrenome;
    Spinner spinPais;
    EditText editTextCelular;
    Button buttonAdicionarContato;
    ListaContatos listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_contato);

        editTextNome = findViewById(R.id.editTextNome);
        editTextSobrenome = findViewById(R.id.editTextSobrenome);
        spinPais = findViewById(R.id.spinPais);
        editTextCelular = findViewById(R.id.editTextCelular);
        buttonAdicionarContato = findViewById(R.id.buttonAdicionarContato);
        listaContatos = new ListaContatos();

        buttonAdicionarContato.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString();
            String sobrenome = editTextSobrenome.getText().toString();
            String celularStr = editTextCelular.getText().toString();
            String pais = (String) spinPais.getSelectedItem();

            if (nome.isEmpty() || sobrenome.isEmpty() || celularStr.isEmpty() || pais == null) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int celular;
            try {
                celular = Integer.parseInt(celularStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Número de celular inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            listaContatos.adicionarContato(nome, celular, sobrenome, pais);
            Toast.makeText(this, "Contato adicionado!", Toast.LENGTH_SHORT).show();

            // Limpa campos (opcional)
            editTextNome.setText("");
            editTextSobrenome.setText("");
            editTextCelular.setText("");
            spinPais.setSelection(0);
        });
    }
}
