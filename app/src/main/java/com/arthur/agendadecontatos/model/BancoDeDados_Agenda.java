package com.arthur.agendadecontatos.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados_Agenda extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "agenda.db";
    public static final int VERSAO_BANCO = 1;

    public static final String TABELA_CONTATOS = "contatos";
    public static final String CONTATO_COL_ID = "id";
    public static final String CONTATO_COL_NOME = "nome";
    public static final String CONTATO_COL_SOBRENOME = "sobrenome";
    public static final String CONTATO_COL_TELEFONE = "telefone";
    public static final String CONTATO_COL_PAIS = "pais";
    public static final String CONTATO_COL_USUARIO = "usuarioId";

    public static final String TABELA_USUARIOS = "usuarios";
    public static final String USUARIO_COL_ID = "id";
    public static final String USUARIO_COL_IMAGEVIEW = "imageview";
    public static final String USUARIO_COL_NOME_COMPLETO = "nomeCompleto";
    public static final String USUARIO_COL_SENHA = "senha";
    public static final String USUARIO_COL_TELEFONE = "telefone";
    public static final String USUARIO_COL_EMAIL = "email";
    public static final String USUARIO_COL_PAIS = "pais";

    public BancoDeDados_Agenda(final Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String sqlUsuarios = "CREATE TABLE " + TABELA_USUARIOS + " (" +
                USUARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USUARIO_COL_IMAGEVIEW + " TEXT NOT NULL, " +
                USUARIO_COL_NOME_COMPLETO + " TEXT NOT NULL, " +
                USUARIO_COL_SENHA + " TEXT NOT NULL, " +
                USUARIO_COL_TELEFONE + " TEXT NOT NULL, " +
                USUARIO_COL_EMAIL + " TEXT NOT NULL, " +
                USUARIO_COL_PAIS + " TEXT NOT NULL" +
                ")";

        final String sqlContatos = "CREATE TABLE " + TABELA_CONTATOS + " (" +
                CONTATO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTATO_COL_NOME + " TEXT NOT NULL, " +
                CONTATO_COL_SOBRENOME + " TEXT NOT NULL, " +
                CONTATO_COL_TELEFONE + " TEXT NOT NULL, " +
                CONTATO_COL_PAIS + " TEXT NOT NULL, " +
                CONTATO_COL_USUARIO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + CONTATO_COL_USUARIO + ") REFERENCES " + TABELA_USUARIOS + "(" + USUARIO_COL_ID + ") ON DELETE CASCADE" +
                ")";

        db.execSQL(sqlUsuarios);
        db.execSQL(sqlContatos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTATOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTATOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        onCreate(db);
    }
}
