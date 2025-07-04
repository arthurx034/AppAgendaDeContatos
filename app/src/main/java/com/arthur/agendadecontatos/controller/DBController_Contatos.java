package com.arthur.agendadecontatos.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arthur.agendadecontatos.model.BancoDeDados_Contatos;
import com.arthur.agendadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class DBController_Contatos {
    private final SQLiteDatabase db;

    public DBController_Contatos(Context context) {
        BancoDeDados_Contatos banco = new BancoDeDados_Contatos(context);
        db = banco.getWritableDatabase();
    }

    public void adicionarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Contatos.COLUNA_NOME, contato.getNome());
        valores.put(BancoDeDados_Contatos.COLUNA_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Contatos.COLUNA_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Contatos.COLUNA_PAIS, contato.getPais());

        db.insert(BancoDeDados_Contatos.TABELA_CONTATOS, null, valores);
    }

    public List<Contato> listarContatos() {
        List<Contato> listaContatos = new ArrayList<>();

        Cursor cursor = db.query(
                BancoDeDados_Contatos.TABELA_CONTATOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados_Contatos.COLUNA_NOME + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_NOME)));
                contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_SOBRENOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_TELEFONE)));
                contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_PAIS)));

                listaContatos.add(contato);
            }
            cursor.close();
        }

        return listaContatos;
    }

    public void excluirContato(int id) {
        db.delete(BancoDeDados_Contatos.TABELA_CONTATOS, BancoDeDados_Contatos.COLUNA_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void editarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Contatos.COLUNA_NOME, contato.getNome());
        valores.put(BancoDeDados_Contatos.COLUNA_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Contatos.COLUNA_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Contatos.COLUNA_PAIS, contato.getPais());

        db.update(BancoDeDados_Contatos.TABELA_CONTATOS, valores, BancoDeDados_Contatos.COLUNA_ID + "=?", new String[]{String.valueOf(contato.getId())});
    }

    public Contato buscarContatoPorId(int id) {
        Contato contato = null;

        Cursor cursor = db.query(
                BancoDeDados_Contatos.TABELA_CONTATOS,
                null,
                BancoDeDados_Contatos.COLUNA_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_ID)));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_NOME)));
            contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_SOBRENOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_TELEFONE)));
            contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Contatos.COLUNA_PAIS)));

            cursor.close();
        }

        return contato;
    }
}