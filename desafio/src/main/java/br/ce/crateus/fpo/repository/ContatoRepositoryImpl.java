package br.ce.crateus.fpo.repository;

import br.ce.crateus.fpo.database.DatabaseConnection;
import br.ce.crateus.fpo.model.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ContatoRepositoryImpl implements ContatoRepository{
    @Override
    public void salvar(Contato contato) {
        String sql = "INSERT INTO contato (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar contato", e);
        }
    }

    @Override
    public void atualizar(Contato contato) {

    }

    @Override
    public void remover(Long id) {

    }

    @Override
    public List<Contato> listarTodos() {
        return List.of();
    }
}
