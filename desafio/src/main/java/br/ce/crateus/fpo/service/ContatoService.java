package br.ce.crateus.fpo.service;

import br.ce.crateus.fpo.model.Contato;
import br.ce.crateus.fpo.repository.ContatoRepository;

public class ContatoService {
    private ContatoRepository repository;

    // Construtor que recebe a dependência
    public ContatoService(ContatoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Contato contato) {
        repository.salvar(contato);
    }
}
