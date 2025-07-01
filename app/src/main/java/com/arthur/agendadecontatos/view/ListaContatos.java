package com.arthur.agendadecontatos.view;

import android.content.Intent;
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
import com.arthur.agendadecontatos.controller.DBController;

import java.util.ArrayList;
import java.util.Collections;

public class ListaContatos extends AppCompatActivity {

    private ArrayList<String> nomes = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText editTextBuscar;
    private static final int REQUEST_ADICIONAR_CONTATO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contatos);

        ImageButton fabAdicionar = findViewById(R.id.fabAdicionar);
        ImageButton imagemBack = findViewById(R.id.imagemBack);
        ImageButton imagemSearch = findViewById(R.id.imagemSearch);
        ListView listView = findViewById(R.id.listView);
        editTextBuscar = findViewById(R.id.editTextBuscar);
        TextView textViewVoltar = findViewById(R.id.textViewVoltar);


        DBController dbController = new DBController(this);
        nomes = dbController.listarContatos(); // contatos do banco
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);


        // Exemplo: adicionar nomes para teste

        // Ordenar os nomes
        Collections.sort(nomes);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);

        // Botão para abrir a tela de adicionar contato
        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(ListaContatos.this, AdicionarContato.class);
            startActivityForResult(intent, REQUEST_ADICIONAR_CONTATO);
        });

        // Botão de voltar
        imagemBack.setOnClickListener(v -> finish());

        textViewVoltar.setOnClickListener(v -> finish());

        // Mostrar/ocultar campo de busca
        imagemSearch.setOnClickListener(v -> {
            if (editTextBuscar.getVisibility() == View.VISIBLE) {
                editTextBuscar.setVisibility(View.GONE);
                // Limpa filtro quando esconde o campo
                editTextBuscar.setText("");
                adapter.getFilter().filter("");
            } else {
                editTextBuscar.setVisibility(View.VISIBLE);
                editTextBuscar.requestFocus();
            }
        });

        // Filtrar lista enquanto digita
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
            Intent intent = new Intent(ListaContatos.this, EditarContato.class);
            intent.putExtra("nome", nomes.get(position));
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADICIONAR_CONTATO && resultCode == RESULT_OK) {
            String novoContato = data.getStringExtra("novoContato");
            if (novoContato != null && !novoContato.isEmpty()) {
                nomes.add(novoContato);
                Collections.sort(nomes); // mantém ordem alfabética
                adapter.notifyDataSetChanged();
            }
        }
    }
}
