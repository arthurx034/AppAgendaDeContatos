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
import com.arthur.agendadecontatos.controller.DBController_Contatos;
import com.arthur.agendadecontatos.model.Contato;

public class EditarContato extends AppCompatActivity {

    private EditText editTextNome, editTextSobrenome, editTextTelefone;
    private Spinner spinnerPais;
    private Button buttonSalvar, buttonExcluir;
    private ImageButton buttonVoltar;

    private DBController_Contatos dbControllerContatos;
    private Contato contatoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_contato);

        // Inicializa views
        editTextNome = findViewById(R.id.editTextNome);
        editTextSobrenome = findViewById(R.id.editTextSobrenome);
        editTextTelefone = findViewById(R.id.editTextCelular);
        spinnerPais = findViewById(R.id.spinPais);
        buttonSalvar = findViewById(R.id.buttonSalvarEdicao);
        buttonExcluir = findViewById(R.id.buttonExcluirContato);
        buttonVoltar = findViewById(R.id.imageButtonVoltar);

        dbControllerContatos = new DBController_Contatos(this);

        // Recebe o ID do contato da intent
        Intent intent = getIntent();
        int contatoId = intent.getIntExtra("idContato", -1);
        if (contatoId == -1) {
            Toast.makeText(this, "Contato inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Busca contato no banco
        contatoAtual = dbControllerContatos.buscarContatoPorId(contatoId);
        if (contatoAtual == null) {
            Toast.makeText(this, "Contato não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Preenche campos com os dados do contato
        editTextNome.setText(contatoAtual.getNome());
        editTextSobrenome.setText(contatoAtual.getSobrenome());
        editTextTelefone.setText(contatoAtual.getTelefone());

        // Seleciona país no spinner
        String[] listaPaises = getResources().getStringArray(R.array.lista_paises);
        for (int i = 0; i < listaPaises.length; i++) {
            if (listaPaises[i].equals(contatoAtual.getPais())) {
                spinnerPais.setSelection(i);
                break;
            }
        }

        // Botão voltar
        buttonVoltar.setOnClickListener(v -> finish());

        // Botão salvar
        buttonSalvar.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString().trim();
            String sobrenome = editTextSobrenome.getText().toString().trim();
            String telefone = editTextTelefone.getText().toString().trim();
            String pais = spinnerPais.getSelectedItem().toString();

            if (nome.isEmpty() || sobrenome.isEmpty() || telefone.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            contatoAtual.setNome(nome);
            contatoAtual.setSobrenome(sobrenome);
            contatoAtual.setTelefone(telefone);
            contatoAtual.setPais(pais);

            dbControllerContatos.editarContato(contatoAtual);

            Toast.makeText(this, "Contato atualizado", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Botão excluir
        buttonExcluir.setOnClickListener(v -> {
            dbControllerContatos.excluirContato(contatoAtual.getId());
            Toast.makeText(this, "Contato excluído", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
