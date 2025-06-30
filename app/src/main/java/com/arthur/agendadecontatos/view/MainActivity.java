package com.arthur.agendadecontatos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.arthur.agendadecontatos.R;
import com.arthur.agendadecontatos.model.ListaContatos;
import com.arthur.agendadecontatos.model.Pessoas;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> nomes;
    ArrayAdapter<String> adapter;
    ImageButton fabAdicionar;
    ImageButton imagemBack;
    ImageButton imagemSearch;
    EditText editTextText;
    TextView textView2;
    Pessoas pessoa;
    ListaContatos listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lista_contatos);

        // Aplicar insets (certifique-se de que R.id.main existe no layout XML)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar os componentes da tela
        listView = findViewById(R.id.listView);
        fabAdicionar = findViewById(R.id.fabAdicionar);
        imagemBack = findViewById(R.id.imagemBack);
        imagemSearch = findViewById(R.id.imagemSearch);
        editTextText = findViewById(R.id.editTextText);
        textView2 = findViewById(R.id.textView2);

        listaContatos = new ListaContatos();
        nomes = new ArrayList<>(); // Corrigido: evitar NullPointerException

        // Botão para abrir a tela de adicionar contato
        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarContato.class);
            startActivity(intent);
        });

        // Ordenar e exibir os nomes
        Collections.sort(nomes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
    }
}
