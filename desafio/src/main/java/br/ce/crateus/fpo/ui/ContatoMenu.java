package br.ce.crateus.fpo.ui;

import br.ce.crateus.fpo.model.Contato;
import br.ce.crateus.fpo.service.ContatoService;

import java.util.List;
import java.util.Scanner;

public class ContatoMenu {
    private final ContatoService service;
    private final Scanner scanner;

    public ContatoMenu(ContatoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n========== AGENDA DE CONTATOS ==========");
            System.out.println("1. Cadastrar Contato");
            System.out.println("2. Listar Todos os Contatos");
            System.out.println("3. Buscar Contato");
            System.out.println("4. Atualizar Contato");
            System.out.println("5. Remover Contato");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> remover();
                case 6 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);
        scanner.close();
    }

    private void cadastrar() {
        System.out.println("\n--- CADASTRO DE CONTATO ---");
        Contato contato = new Contato();

        System.out.print("Nome completo: ");
        contato.setNome(scanner.nextLine());

        System.out.print("E-mail: ");
        contato.setEmail(scanner.nextLine());

        System.out.print("Telefone: ");
        contato.setTelefone(scanner.nextLine());

        try {
            service.cadastrar(contato);
            System.out.println("✅ Contato cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void listarTodos() {
        System.out.println("\n--- LISTA DE CONTATOS ---");
        List<Contato> contatos = service.listarTodos();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            exibirTabelaContatos(contatos);
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR CONTATO ---");
        System.out.println("1. Buscar por nome (parcial)");
        System.out.println("2. Buscar por e-mail (exato)");
        System.out.print("Opção: ");
        int op = lerInteiro();

        if (op == 1) {
            System.out.print("Digite o nome ou parte dele: ");
            String nome = scanner.nextLine();
            List<Contato> resultados = service.buscarPorNome(nome);
            if (resultados.isEmpty()) {
                System.out.println("Nenhum contato encontrado.");
            } else {
                System.out.println("📋 Resultados da busca:");
                exibirTabelaContatos(resultados);
            }
        } else if (op == 2) {
            System.out.print("Digite o e-mail exato: ");
            String email = scanner.nextLine();
            Contato contato = service.buscarPorEmail(email);
            if (contato == null) {
                System.out.println("Contato não encontrado.");
            } else {
                exibirContatoDetalhado(contato);
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void atualizar() {
        System.out.println("\n--- ATUALIZAR CONTATO ---");
        System.out.print("Digite o ID do contato: ");
        Long id = lerLong();

        Contato existente = service.buscarPorId(id);
        if (existente == null) {
            System.out.println("❌ Contato não encontrado.");
            return;
        }

        System.out.println("Dados atuais:");
        exibirContatoDetalhado(existente);
        System.out.println("\nDeixe em branco para manter o valor atual.");

        Contato atualizado = new Contato();
        atualizado.setId(id);

        System.out.print("Novo nome (" + existente.getNome() + "): ");
        String nome = scanner.nextLine();
        atualizado.setNome(nome.isBlank() ? existente.getNome() : nome);

        System.out.print("Novo e-mail (" + existente.getEmail() + "): ");
        String email = scanner.nextLine();
        atualizado.setEmail(email.isBlank() ? existente.getEmail() : email);

        System.out.print("Novo telefone (" + existente.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        atualizado.setTelefone(telefone.isBlank() ? existente.getTelefone() : telefone);

        try {
            service.atualizar(atualizado);
            System.out.println("✅ Contato atualizado!");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n--- REMOVER CONTATO ---");
        System.out.print("Digite o ID do contato: ");
        Long id = lerLong();

        Contato contato = service.buscarPorId(id);
        if (contato == null) {
            System.out.println("❌ Contato não encontrado.");
            return;
        }

        exibirContatoDetalhado(contato);
        System.out.print("Confirma remoção? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                service.remover(id);
                System.out.println("✅ Contato removido.");
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // Métodos auxiliares de formatação (idênticos aos anteriores, sem referência a categoria)
    private void exibirTabelaContatos(List<Contato> contatos) {
        System.out.println("+----+--------------------------------------+--------------------------------------+----------------+---------------------+");
        System.out.format("| %-2s | %-36s | %-36s | %-14s | %-19s |\n", "ID", "NOME", "EMAIL", "TELEFONE", "DATA CADASTRO");
        System.out.println("+----+--------------------------------------+--------------------------------------+----------------+---------------------+");
        for (Contato c : contatos) {
            String dataStr = (c.getDataCadastro() != null) ? c.getDataCadastro().toString().substring(0, 19) : "N/A";
            System.out.format("| %-2d | %-36s | %-36s | %-14s | %-19s |\n",
                    c.getId(),
                    truncar(c.getNome(), 36),
                    truncar(c.getEmail(), 36),
                    truncar(c.getTelefone(), 14),
                    dataStr);
        }
        System.out.println("+----+--------------------------------------+--------------------------------------+----------------+---------------------+");
    }

    private void exibirContatoDetalhado(Contato c) {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ ID: " + c.getId());
        System.out.println("│ Nome: " + c.getNome());
        System.out.println("│ E-mail: " + c.getEmail());
        System.out.println("│ Telefone: " + c.getTelefone());
        System.out.println("│ Data de cadastro: " + c.getDataCadastro());
        System.out.println("└─────────────────────────────────────────┘");
    }

    private String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        if (texto.length() <= maxLen) return texto;
        return texto.substring(0, maxLen - 3) + "...";
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número: ");
            }
        }
    }

    private Long lerLong() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("ID inválido. Digite um número: ");
            }
        }
    }
}