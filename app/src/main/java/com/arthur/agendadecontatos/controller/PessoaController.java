package com.arthur.agendadecontatos.controller;

import com.arthur.agendadecontatos.model.Pessoas;

import java.util.ArrayList;
import java.util.List;

public class PessoaController {
    private List<Pessoas> pessoas = new ArrayList<>();

    public void adicionarPessoa(Pessoas pessoa) {
        pessoas.add(pessoa);
    }

    public List<Pessoas> listarPessoas() {
        return pessoas;
    }

    public void removerPessoa(Pessoas pessoa) {
        pessoas.remove(pessoa);
    }

    public Pessoas buscarPessoaPorNome(String nome) {
        for (Pessoas pessoa : pessoas) {
            if (pessoa.getNome().equalsIgnoreCase(nome)) {
                return pessoa;
            }
        }
        return null;
    }
}
