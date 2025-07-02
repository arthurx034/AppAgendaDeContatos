package com.arthur.agendadecontatos.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arthur.agendadecontatos.model.BancoDeDados;
import com.arthur.agendadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class DBController {
    private final SQLiteDatabase db;

    public DBController(Context context) {
        BancoDeDados banco = new BancoDeDados(context);
        db = banco.getWritableDatabase();
    }

    public void adicionarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados.COLUNA_NOME, contato.getNome());
        valores.put(BancoDeDados.COLUNA_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados.COLUNA_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados.COLUNA_PAIS, contato.getPais());

        db.insert(BancoDeDados.TABELA_CONTATOS, null, valores);
    }

    public List<Contato> listarContatos() {
        List<Contato> lista = new ArrayList<>();

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
                Contato contato = new Contato();
                contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_NOME)));
                contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_SOBRENOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_TELEFONE)));
                contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_PAIS)));

                lista.add(contato);
            }
            cursor.close();
        }

        return lista;
    }

    public void excluirContato(int id) {
        db.delete(BancoDeDados.TABELA_CONTATOS, BancoDeDados.COLUNA_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void editarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados.COLUNA_NOME, contato.getNome());
        valores.put(BancoDeDados.COLUNA_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados.COLUNA_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados.COLUNA_PAIS, contato.getPais());

        db.update(BancoDeDados.TABELA_CONTATOS, valores, BancoDeDados.COLUNA_ID + "=?", new String[]{String.valueOf(contato.getId())});
    }

    public Contato buscarContatoPorId(int id) {
        Contato contato = null;

        Cursor cursor = db.query(
                BancoDeDados.TABELA_CONTATOS,
                null,
                BancoDeDados.COLUNA_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_ID)));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_NOME)));
            contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_SOBRENOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_TELEFONE)));
            contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.COLUNA_PAIS)));

            cursor.close();
        }

        return contato;
    }
}