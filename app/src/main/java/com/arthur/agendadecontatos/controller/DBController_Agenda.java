package com.arthur.agendadecontatos.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arthur.agendadecontatos.model.BancoDeDados_Agenda;
import com.arthur.agendadecontatos.model.Contato;
import com.arthur.agendadecontatos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DBController_Agenda {
    private final SQLiteDatabase db;

    public DBController_Agenda(final Context context) {
        BancoDeDados_Agenda banco = new BancoDeDados_Agenda(context);
        this.db = banco.getWritableDatabase();
    }

    // --- USUÁRIOS ---

    public void cadastrarUsuario(final Usuario usuario) {
        final ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.USUARIO_COL_IMAGEVIEW, usuario.getImageView());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO, usuario.getNomeCompleto());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_SENHA, usuario.getSenha());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_TELEFONE, usuario.getTelefone());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_EMAIL, usuario.getEmail());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_PAIS, usuario.getPais());

        db.insert(BancoDeDados_Agenda.TABELA_USUARIOS, null, valores);
    }

    public List<Usuario> listarUsuarios() {
        final List<Usuario> lista = new ArrayList<>();

        try (Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_USUARIOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO + " ASC")) {

            while (cursor.moveToNext()) {
                lista.add(mapearUsuario(cursor));
            }
        }

        return lista;
    }

    public int buscarIdUsuarioPorNome(final String nome) {
        int id = -1;

        try (Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_USUARIOS,
                new String[]{BancoDeDados_Agenda.USUARIO_COL_ID},
                BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO + "=?",
                new String[]{nome},
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_ID));
            }
        }

        return id;
    }

    private Usuario mapearUsuario(final Cursor cursor) {
        final Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_ID)));
        usuario.setImageView(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_IMAGEVIEW)));
        usuario.setNomeCompleto(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_SENHA)));
        usuario.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_TELEFONE)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_EMAIL)));
        usuario.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_PAIS)));
        return usuario;
    }

    // --- CONTATOS ---

    public void adicionarContato(final Contato contato) {
        final ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.CONTATO_COL_NOME, contato.getNome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_PAIS, contato.getPais());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_USUARIO, contato.getUsuarioId());

        db.insert(BancoDeDados_Agenda.TABELA_CONTATOS, null, valores);
    }

    public List<Contato> listarContatosPorUsuario(final int usuarioId) {
        final List<Contato> lista = new ArrayList<>();

        try (Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                null,
                BancoDeDados_Agenda.CONTATO_COL_USUARIO + "=?",
                new String[]{String.valueOf(usuarioId)},
                null,
                null,
                BancoDeDados_Agenda.CONTATO_COL_NOME + " ASC")) {

            while (cursor.moveToNext()) {
                lista.add(mapearContato(cursor));
            }
        }

        return lista;
    }

    public Contato buscarContatoPorId(final int id) {
        Contato contato = null;

        try (Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                null,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                contato = mapearContato(cursor);
            }
        }

        return contato;
    }

    public void editarContato(final Contato contato) {
        final ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.CONTATO_COL_NOME, contato.getNome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_PAIS, contato.getPais());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_USUARIO, contato.getUsuarioId());

        db.update(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                valores,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(contato.getId())});
    }

    public void excluirContato(final int id) {
        db.delete(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    private Contato mapearContato(final Cursor cursor) {
        final Contato contato = new Contato();
        contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_ID)));
        contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_NOME)));
        contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME)));
        contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_TELEFONE)));
        contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_PAIS)));
        contato.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_USUARIO)));
        return contato;
    }
}
