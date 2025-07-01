package com.arthur.agendadecontatos.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arthur.agendadecontatos.model.BancoDeDados;

import java.util.ArrayList;

public class DBController {
    private final SQLiteDatabase db;

    public DBController(Context context) {
        BancoDeDados banco = new BancoDeDados(context);
        db = banco.getWritableDatabase();
    }

    public void adicionarContato(String nome, String sobrenome, String telefone, String pais) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados.COLUNA_NOME, nome);
        valores.put(BancoDeDados.COLUNA_SOBRENOME, sobrenome);
        valores.put(BancoDeDados.COLUNA_TELEFONE, telefone);
        valores.put(BancoDeDados.COLUNA_PAIS, pais);

        db.insert(BancoDeDados.TABELA_CONTATOS, null, valores);
    }

    public ArrayList<String> listarContatos() {
        ArrayList<String> lista = new ArrayList<>();

        Cursor cursor = db.query(
                BancoDeDados.TABELA_CONTATOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados.COLUNA_NOME + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_NOME));
                String sobrenome = cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_SOBRENOME));
                lista.add(nome + " " + sobrenome);
            }
            cursor.close();
        }

        return lista;
    }
}
