package com.arthur.agendadecontatos.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaContatos {
    private ArrayList<String> nomes;
    private ArrayList<String> sobrenomes;
    private ArrayList<String> paises;
    private ArrayList<Integer> telefones;

    public ListaContatos() {
        nomes = new ArrayList<>();
        sobrenomes = new ArrayList<>();
        paises = new ArrayList<>();
        telefones = new ArrayList<>();
    }

    public void adicionarContato(String nome, int telefone, String sobrenome, String pais) {
        nomes.add(nome);
        sobrenomes.add(sobrenome);
        paises.add(pais);
        telefones.add(telefone);
    }

    public void removerContato(int indice) {
        nomes.remove(indice);
        sobrenomes.remove(indice);
        paises.remove(indice);
        telefones.remove(indice);
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

    public List<Integer> getTelefones() {
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

    public void setTelefones(ArrayList<Integer> telefones) {
        this.telefones = telefones;
    }
}
