package com.arthur.agendadecontatos.model;

public class Pessoas {
    private String nome;
    private String sobrenome;
    private String pais;
    private Integer telefone;

    public Pessoas(String nome, String sobrenome, String pais, Integer telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.pais = pais;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getPais() {
        return pais;
    }

    public Integer getTelefone() {
        return telefone;
    }
}
