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
        validarCampos(contato);
        validarEmailUnico(contato.getEmail());
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
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio");
        }
        return repository.buscarPorEmail(email);
    }

    public Contato buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Contato contato) {
        // Validações de negócio
        if (contato.getId() == null) {
            throw new IllegalArgumentException("ID do contato é obrigatório para atualização");
        }
        validarCampos(contato);  // reutiliza a validação de nome, email, telefone

        // Verifica se o e-mail já está em uso por OUTRO contato (evita duplicação)
        Contato existentePorEmail = repository.buscarPorEmail(contato.getEmail());
        if (existentePorEmail != null && !existentePorEmail.getId().equals(contato.getId())) {
            throw new IllegalArgumentException("E-mail já está em uso por outro contato.");
        }

        repository.atualizar(contato);
    }

    // Método auxiliar de validação (pode ser reaproveitado do cadastro)
    private void validarCampos(Contato c) {
        if (c.getNome() == null || c.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (c.getEmail() == null || !c.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        if (c.getTelefone() == null || c.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
    }

    private void validarEmailUnico(String email) {
        Contato existente = repository.buscarPorEmail(email);
        if (existente != null)
            throw new RuntimeException("E-mail já cadastrado");
    }

    public void remover(Long id) {
        repository.remover(id);
    }
}
