package br.ce.crateus.fpo;

import br.ce.crateus.fpo.database.DatabaseConnection;
import br.ce.crateus.fpo.model.Contato;
import br.ce.crateus.fpo.repository.ContatoRepositoryImpl;
import br.ce.crateus.fpo.service.ContatoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    static void main() {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        ContatoRepositoryImpl repository = new ContatoRepositoryImpl();
        ContatoService service = new ContatoService(repository);


        Contato contato = new Contato( "Jander",
                "88988963057", "jandermartins@alu.ufc.br");

        service.cadastrar(contato);
    }
}
