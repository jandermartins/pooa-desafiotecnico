package br.ce.crateus.fpo.service;

import br.ce.crateus.fpo.model.Contato;
import br.ce.crateus.fpo.repository.ContatoRepository;

import java.util.List;

public class ContatoService {
    private final ContatoRepository repository;

    public ContatoService(ContatoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Contato contato) {
        repository.salvar(contato);
    }


    public List<Contato> listarTodos() {
        return repository.listarTodos();
    }

    public List<Contato> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca não pode ser vazio");
        }
        return repository.buscarPorNome(nome);
    }

    public Contato buscarPorEmail(String email) {
        return null;
    }

    public Contato buscarPorId(Long id) {
        return null;
    }

    public void atualizar(Contato atualizado) {
    }

    public void remover(Long id) {

    }
}
