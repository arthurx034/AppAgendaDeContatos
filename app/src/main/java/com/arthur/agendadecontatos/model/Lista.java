package com.arthur.agendadecontatos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de modelo para armazenar listas de contatos em memória.
 */
public class Lista {
    private ArrayList<String> nomes;
    private ArrayList<String> sobrenomes;
    private ArrayList<String> paises;
    private ArrayList<String> telefones;

    public Lista() {
        nomes = new ArrayList<>();
        sobrenomes = new ArrayList<>();
        paises = new ArrayList<>();
        telefones = new ArrayList<>();
    }

    /**
     * Adiciona um novo contato à lista.
     */
    public void adicionarContato(String nome, String telefone, String sobrenome, String pais) {
        nomes.add(nome);
        sobrenomes.add(sobrenome);
        paises.add(pais);
        telefones.add(telefone);
    }

    /**
     * Remove o contato pelo índice.
     */
    public void removerContato(int indice) {
        if (indice >= 0 && indice < nomes.size()) {
            nomes.remove(indice);
            sobrenomes.remove(indice);
            paises.remove(indice);
            telefones.remove(indice);
        }
    }

    public List<String> getNomes() {
        return nomes;
    }

    public List<String> getSobrenomes() {
        return sobrenomes;
    }

    public List<String> getPaises() {
        return paises;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public int getQuantidadeContatos() {
        return nomes.size();
    }

    public void setNomes(ArrayList<String> nomes) {
        this.nomes = nomes;
    }

    public void setSobrenomes(ArrayList<String> sobrenomes) {
        this.sobrenomes = sobrenomes;
    }

    public void setPaises(ArrayList<String> paises) {
        this.paises = paises;
    }

    public void setTelefones(ArrayList<String> telefones) {
        this.telefones = telefones;
    }
}
