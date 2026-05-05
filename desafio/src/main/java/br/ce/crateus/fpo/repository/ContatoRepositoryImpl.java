package br.ce.crateus.fpo.repository;

import br.ce.crateus.fpo.database.DatabaseConnection;
import br.ce.crateus.fpo.model.Contato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepositoryImpl implements ContatoRepository{
    @Override
    public void salvar(Contato contato) {
        String sql = "INSERT INTO contato (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getTelefone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar contato", e);
        }
    }

    @Override
    public void atualizar(Contato contato) {
        String sql = "UPDATE contato SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getTelefone());
            stmt.setLong(4, contato.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar contato", e);
        }
    }

    @Override
    public void remover(Long id) {
        String sql = "DELETE FROM contato WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Contato com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover contato", e);
        }
    }

    @Override
    public List<Contato> listarTodos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contato ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                Timestamp ts = rs.getTimestamp("dataCadastro");
                if (ts != null) c.setDataCadastro(ts.toLocalDateTime());
                contatos.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contatos", e);
        }
        return contatos;
    }

    @Override
    public List<Contato> buscarPorNome(String nome) {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contato WHERE nome ILIKE ? ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contatos.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contatos por nome", e);
        }
        return contatos;
    }

    @Override
    public Contato buscarPorEmail(String email) {
        String sql = "SELECT * FROM contato WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contato por e-mail", e);
        }
        return null;
    }

    @Override
    public Contato buscarPorId(Long id) {
        String sql = "SELECT * FROM contato WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contato por ID", e);
        }
        return null;
    }

    private Contato mapper(ResultSet rs) throws SQLException {
        Contato c = new Contato();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setEmail(rs.getString("email"));
        c.setTelefone(rs.getString("telefone"));
        Timestamp ts = rs.getTimestamp("dataCadastro");
        if (ts != null) {
            c.setDataCadastro(ts.toLocalDateTime());
        }
        return c;
    }
}
