package com.arthur.agendadecontatos.model;

import java.util.ArrayList;
import java.util.List;

public class Lista {
    private static Lista instancia;
    private final ArrayList<Contato> contatos;

    private Lista() {
        contatos = new ArrayList<>();
    }

    public static Lista getInstance() {
        if (instancia == null) {
            instancia = new Lista();
        }
        return instancia;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void limpar() {
        contatos.clear();
    }
}
