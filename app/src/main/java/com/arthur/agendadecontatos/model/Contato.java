package com.arthur.agendadecontatos.model;

public class Contato {
    private int id;
    private String nome;
    private String sobrenome;
    private String pais;
    private String telefone;

    public Contato() {
    }

    public Contato(int id, String nome, String sobrenome, String pais, String telefone) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.pais = pais;
        this.telefone = telefone;
    }

    public Contato(String nome, String sobrenome, String pais, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.pais = pais;
        this.telefone = telefone;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}
