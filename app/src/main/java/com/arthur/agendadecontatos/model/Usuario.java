package com.arthur.agendadecontatos.model;

public class Usuario {
    private int id;
    private String imageView;
    private String nomeCompleto;
    private String pais;
    private String email;
    private String telefone;
    private String senha;

    public Usuario() {
    }

    public Usuario(int id, String imageView, String nomeCompleto, String senha, String telefone, String email, String pais) {
        this.id = id;
        this.imageView = imageView;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.pais = pais;
    }

    public Usuario(String imageView, String nomeCompleto, String senha, String telefone, String email, String pais) {
        this.imageView = imageView;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.pais = pais;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
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
