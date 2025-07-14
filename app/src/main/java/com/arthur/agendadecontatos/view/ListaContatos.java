package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.controller.DBController_Agenda;
import com.arthur.agendadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ListaContatos extends AppCompatActivity {

    private List<Contato> listaContatos = new ArrayList<>();
    private final ArrayList<String> nomes = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText editTextBuscar;
    private com.google.android.material.textfield.TextInputLayout textInputLayoutBusca;

    private DBController_Agenda dbControllerContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contatos);

        ImageButton fabAdicionar = findViewById(R.id.fabAdicionar);
        ImageButton imagemBack = findViewById(R.id.imagemBack);
        ImageButton imagemSearch = findViewById(R.id.imagemSearch);
        editTextBuscar = findViewById(R.id.editTextBuscar);
        textInputLayoutBusca = findViewById(R.id.textInputLayoutBusca);
        ListView listView = findViewById(R.id.listView);
        TextView textViewVoltar = findViewById(R.id.textViewVoltar);

        dbControllerContatos = new DBController_Agenda(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);

        carregarContatos();

        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(ListaContatos.this, AdicionarContato.class);
            startActivity(intent);
        });

        imagemBack.setOnClickListener(v -> finish());
        textViewVoltar.setOnClickListener(v -> finish());

        imagemSearch.setOnClickListener(v -> {
            if (textInputLayoutBusca.getVisibility() == View.VISIBLE) {
                textInputLayoutBusca.setVisibility(View.GONE);
                editTextBuscar.setText("");
                adapter.getFilter().filter("");
            } else {
                textInputLayoutBusca.setVisibility(View.VISIBLE);
                textInputLayoutBusca.requestFocus();
            }
        });

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contato contatoSelecionado = listaContatos.get(position);
            Intent intent = new Intent(ListaContatos.this, EditarContato.class);
            intent.putExtra("idContato", contatoSelecionado.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarContatos();
    }

    private void carregarContatos() {
        SharedPreferences prefs = getSharedPreferences("usuarioLogado", MODE_PRIVATE);
        int usuarioId = prefs.getInt("usuarioId", -1);

        // Verifica se nenhum usuário está logado
        if (usuarioId == -1) {
            limparListaContatos();
            return;
        }

        // Recupera os contatos do banco filtrando pelo usuário
        List<Contato> contatosDoUsuario = dbControllerContatos.listarContatosPorUsuario(usuarioId);

        // Atualiza a lista principal e os nomes
        atualizarListas(contatosDoUsuario);
    }

    // Método para limpar as listas e atualizar a interface
    private void limparListaContatos() {
        listaContatos.clear();
        nomes.clear();
        adapter.notifyDataSetChanged();
    }

    // Método para atualizar os dados exibidos com base na nova lista de contatos
    private void atualizarListas(List<Contato> contatos) {
        listaContatos.clear();
        listaContatos.addAll(contatos);

        nomes.clear();
        for (Contato c : contatos) {
            String nomeCompleto = (c.getNome() + " " + c.getSobrenome()).trim();
            nomes.add(nomeCompleto);
        }

        adapter.notifyDataSetChanged();
    }
}
