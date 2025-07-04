package com.arthur.agendadecontatos.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arthur.agendadecontatos.model.BancoDeDados_Contatos;
import com.arthur.agendadecontatos.model.BancoDeDados_Usuarios;
import com.arthur.agendadecontatos.model.Contato;
import com.arthur.agendadecontatos.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DBController_Usuarios {
    private static SQLiteDatabase db = null;

    public DBController_Usuarios(Context context) {
        BancoDeDados_Usuarios banco = new BancoDeDados_Usuarios(context);
        db = banco.getWritableDatabase();
    }

    public void CadastrarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put(BancoDeDados_Usuarios.COLUNA_NOMECOMPLETO, usuario.getNomeCompleto());
        valores.put(BancoDeDados_Usuarios.COLUNA_SENHA, usuario.getSenha());
        valores.put(BancoDeDados_Usuarios.COLUNA_TELEFONE, usuario.getTelefone());
        valores.put(BancoDeDados_Usuarios.COLUNA_EMAIL, usuario.getEmail());
        valores.put(BancoDeDados_Usuarios.COLUNA_PAIS, usuario.getPais());

        db.insert(BancoDeDados_Usuarios.TABELA_USUARIOS, null, valores);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        Cursor cursor = db.query(
                BancoDeDados_Usuarios.TABELA_USUARIOS,
                null,
                null,
                null,
                null,
                null,
                BancoDeDados_Usuarios.COLUNA_NOMECOMPLETO + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_ID)));
                usuario.setNomeCompleto(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_NOMECOMPLETO)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_SENHA)));
                usuario.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_TELEFONE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_EMAIL)));
                usuario.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_PAIS)));

                listaUsuarios.add(usuario);
            }
            cursor.close();
        }

        return listaUsuarios;
    }

    public static Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = null;

        Cursor cursor = db.query(
                BancoDeDados_Usuarios.TABELA_USUARIOS,
                null,
                BancoDeDados_Usuarios.COLUNA_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_ID)));
            usuario.setNomeCompleto(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_NOMECOMPLETO)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_SENHA)));
            usuario.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_TELEFONE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_EMAIL)));
            usuario.setPais(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados_Usuarios.COLUNA_PAIS)));

            cursor.close();
        }

        return usuario;
    }
}
