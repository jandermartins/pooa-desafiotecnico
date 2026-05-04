package br.ce.crateus.fpo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Configurações do banco de dados PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/agenda";
    private static final String USER = "postgres";        // substitua pelo seu usuário
    private static final String PASSWORD = "2502";   // substitua pela sua senha

    // Evita instanciar a classe (uso estático)
    public DatabaseConnection() {}

    /**
     * Retorna uma conexão com o banco de dados.
     * @return Connection object
     * @throws SQLException se ocorrer erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registra o driver explicitamente (opcional em versões recentes do JDBC 4+)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado. Verifique o JAR.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Testa a conexão (útil para diagnóstico).
     */
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão com PostgreSQL estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}