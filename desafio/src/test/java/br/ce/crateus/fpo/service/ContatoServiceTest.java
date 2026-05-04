package br.ce.crateus.fpo.service;

import br.ce.crateus.fpo.model.Contato;
import br.ce.crateus.fpo.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe ContatoService.
 */
@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @InjectMocks
    private ContatoService service;        // serviço com o mock injetado

    private Contato contato;

    @BeforeEach
    void setUp() {
        contato = new Contato();
        contato.setNome("João Silva");
        contato.setEmail("joao@email.com");
        contato.setTelefone("(85) 99999-9999");
    }

    @Test
    void cadastrar_deveChamarSalvarDoRepository() {
        service.cadastrar(contato);
    }
}