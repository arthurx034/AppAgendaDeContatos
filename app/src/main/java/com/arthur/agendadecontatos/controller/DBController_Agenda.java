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

    public DBController_Agenda(Context context) {
        BancoDeDados_Agenda banco = new BancoDeDados_Agenda(context);
        db = banco.getWritableDatabase();
    }

    // --- USUÁRIOS ---

    public void cadastrarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.USUARIO_COL_IMAGEVIEW, usuario.getImageView());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO, usuario.getNomeCompleto());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_SENHA, usuario.getSenha());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_TELEFONE, usuario.getTelefone());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_EMAIL, usuario.getEmail());
        valores.put(BancoDeDados_Agenda.USUARIO_COL_PAIS, usuario.getPais());

        db.insert(BancoDeDados_Agenda.TABELA_USUARIOS, null, valores);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();

        Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_USUARIOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_ID)));
                usuario.setImageView(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_IMAGEVIEW)));
                usuario.setNomeCompleto(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_SENHA)));
                usuario.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_TELEFONE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_EMAIL)));
                usuario.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_PAIS)));
                lista.add(usuario);
            }
            cursor.close();
        }

        return lista;
    }

    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = null;

        Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_USUARIOS,
                null,
                BancoDeDados_Agenda.USUARIO_COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_ID)));
            usuario.setImageView(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_IMAGEVIEW)));
            usuario.setNomeCompleto(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_NOME_COMPLETO)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_SENHA)));
            usuario.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_TELEFONE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_EMAIL)));
            usuario.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.USUARIO_COL_PAIS)));
            cursor.close();
        }

        return usuario;
    }

    // --- CONTATOS ---

    public void adicionarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.CONTATO_COL_NOME, contato.getNome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_PAIS, contato.getPais());

        db.insert(BancoDeDados_Agenda.TABELA_CONTATOS, null, valores);
    }

    public List<Contato> listarContatos() {
        List<Contato> lista = new ArrayList<>();

        Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados_Agenda.CONTATO_COL_NOME + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_NOME)));
                contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_TELEFONE)));
                contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_PAIS)));
                lista.add(contato);
            }
            cursor.close();
        }

        return lista;
    }

    public Contato buscarContatoPorId(int id) {
        Contato contato = null;

        Cursor cursor = db.query(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                null,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_ID)));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_NOME)));
            contato.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_TELEFONE)));
            contato.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Agenda.CONTATO_COL_PAIS)));
            cursor.close();
        }

        return contato;
    }

    public void editarContato(Contato contato) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Agenda.CONTATO_COL_NOME, contato.getNome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_SOBRENOME, contato.getSobrenome());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_TELEFONE, contato.getTelefone());
        valores.put(BancoDeDados_Agenda.CONTATO_COL_PAIS, contato.getPais());

        db.update(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                valores,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(contato.getId())}
        );
    }

    public void excluirContato(int id) {
        db.delete(
                BancoDeDados_Agenda.TABELA_CONTATOS,
                BancoDeDados_Agenda.CONTATO_COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );
    }
}
