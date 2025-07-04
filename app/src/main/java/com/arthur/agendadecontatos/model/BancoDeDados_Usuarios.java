package com.arthur.agendadecontatos.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados_Usuarios extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "usuarios.db";
    public static final int VERSAO_BANCO = 2;
    public static final String TABELA_USUARIOS = "usuarios";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOMECOMPLETO = "nomeCompleto";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_TELEFONE = "telefone";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_PAIS = "pais";

    public BancoDeDados_Usuarios(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA_USUARIOS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOMECOMPLETO + " TEXT NOT NULL, " +
                COLUNA_SENHA + " TEXT NOT NULL, " +
                COLUNA_TELEFONE + " TEXT NOT NULL, " +
                COLUNA_EMAIL + " TEXT NOT NULL, " +
                COLUNA_PAIS + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        onCreate(db);
    }
}
