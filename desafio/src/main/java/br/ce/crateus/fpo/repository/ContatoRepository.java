package br.ce.crateus.fpo.repository;

import br.ce.crateus.fpo.model.Contato;

import java.util.List;

public interface ContatoRepository {
    void salvar(Contato contato);
    void atualizar(Contato contato);
    void remover(Long id);
    List<Contato> listarTodos();

    List<Contato> buscarPorNome(String nome);
}
