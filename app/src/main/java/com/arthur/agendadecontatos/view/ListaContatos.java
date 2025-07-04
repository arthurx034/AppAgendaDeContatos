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
import com.arthur.agendadecontatos.controller.DBController_Contatos;
import com.arthur.agendadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ListaContatos extends AppCompatActivity {

    private List<Contato> listaContatos = new ArrayList<>(); // Lista real de contatos
    private ArrayList<String> nomes = new ArrayList<>();    // Lista de nomes para mostrar no adapter
    private ArrayAdapter<String> adapter;
    private EditText editTextBuscar;
    private ListView listView;

    private DBController_Contatos dbControllerContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contatos);

        ImageButton fabAdicionar = findViewById(R.id.fabAdicionar);
        ImageButton imagemBack = findViewById(R.id.imagemBack);
        ImageButton imagemSearch = findViewById(R.id.imagemSearch);
        editTextBuscar = findViewById(R.id.editTextBuscar);
        listView = findViewById(R.id.listView);
        TextView textViewVoltar = findViewById(R.id.textViewVoltar);

        dbControllerContatos = new DBController_Contatos(this);

        // Inicializa o adapter com a lista vazia
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);

        carregarContatos(); // carrega a lista inicial

        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(ListaContatos.this, AdicionarContato.class);
            startActivity(intent);
        });

        imagemBack.setOnClickListener(v -> finish());
        textViewVoltar.setOnClickListener(v -> finish());

        imagemSearch.setOnClickListener(v -> {
            if (editTextBuscar.getVisibility() == View.VISIBLE) {
                editTextBuscar.setVisibility(View.GONE);
                editTextBuscar.setText("");
                adapter.getFilter().filter("");
            } else {
                editTextBuscar.setVisibility(View.VISIBLE);
                editTextBuscar.requestFocus();
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
            Contato contatoSelecionado = listaContatos.get(position); // Agora existe!
            Intent intent = new Intent(ListaContatos.this, EditarContato.class);
            intent.putExtra("idContato", contatoSelecionado.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarContatos(); // Recarrega a lista sempre que a activity voltar
    }

    private void carregarContatos() {
        listaContatos = dbControllerContatos.listarContatos();  // pega a lista real
        nomes.clear();
        for (Contato c : listaContatos) {
            nomes.add(c.getNome() + " " + c.getSobrenome());
        }
        adapter.notifyDataSetChanged();
    }
}
