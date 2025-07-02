package com.arthur.agendadecontatos.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "agenda.db";
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_CONTATOS = "contatos";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_SOBRENOME = "sobrenome";
    public static final String COLUNA_TELEFONE = "telefone";
    public static final String COLUNA_PAIS = "pais";

    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA_CONTATOS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME + " TEXT NOT NULL, " +
                COLUNA_SOBRENOME + " TEXT NOT NULL, " +
                COLUNA_TELEFONE + " TEXT NOT NULL, " +
                COLUNA_PAIS + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTATOS);
        onCreate(db);
    }
}
