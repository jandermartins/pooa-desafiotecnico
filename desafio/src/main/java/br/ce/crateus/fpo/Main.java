package br.ce.crateus.fpo;

import br.ce.crateus.fpo.database.DatabaseConnection;
import br.ce.crateus.fpo.repository.ContatoRepositoryImpl;
import br.ce.crateus.fpo.service.ContatoService;
import br.ce.crateus.fpo.ui.ContatoMenu;

public class Main {
    static void main() {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        ContatoRepositoryImpl repository = new ContatoRepositoryImpl();
        ContatoService service = new ContatoService(repository);

        ContatoMenu menu = new ContatoMenu(service);
        menu.exibirMenu();
    }
}
