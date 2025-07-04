package com.arthur.agendadecontatos.model;

public class Usuario {
    private int id;
    private String nomeCompleto;
    private String pais;
    private String email;
    private String telefone;
    private String senha;

    public Usuario() {
    }

    public Usuario(int id, String nomeCompleto, String pais, String email, String telefone, String senha) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.pais = pais;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Usuario(String nomeCompleto, String pais, String email, String telefone, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.pais = pais;
        this.email = email;
        this.telefone = senha;
        this.senha = senha;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
