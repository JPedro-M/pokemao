package br.edu.ifsul.pokemao.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.ifsul.pokemao.model.Treinador;

public class TreinadorRepository {
    // métodos para login, cadastro, logout
    // conexao com o banco de dados

    Treinador treinadorLogado;

    private ConexaoMySQL conexao;

    public TreinadorRepository() {
        this.conexao = new ConexaoMySQL("localhost", "3306", "root", "root", "pokemao");
    }

    public Treinador getTreinadorLogado() {
        return treinadorLogado;
    }

    public void setTreinadorLogado(Treinador treinadorLogado) {
        this.treinadorLogado = treinadorLogado;
    }

    public Treinador buscarPorId(long long1) {
        return null;
    }

    public int getLenTreinadores() {
        int len = 0;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT COUNT(*) FROM treinador";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            len = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return len;
    }

    public Treinador buscarPorUser(String user) {
        Treinador treinador = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM treinador WHERE user = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                treinador = new Treinador(
                    rs.getLong("id"),
                    rs.getString("user"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getInt("idade")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return treinador;
    }

    public Treinador buscarPorID(long id) {
        Treinador treinador = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM treinador WHERE id = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                treinador = new Treinador(
                    rs.getLong("id"),
                    rs.getString("user"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getInt("idade")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return treinador;
    }

    public boolean login(String user, String senha) {
        Treinador treinador = buscarPorUser(user);
        if (treinador != null) {
            if (treinador.getSenha().equals(senha)) {
                this.treinadorLogado = treinador;
                return true;
            }
        }
        return false;
    }

    public long cadastrar(Treinador treinador) {
        long id = 0;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "INSERT INTO treinador(user, senha, nome, idade) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, treinador.getUser());
            statement.setString(2, treinador.getSenha());
            statement.setString(3, treinador.getNome());
            statement.setInt(4, treinador.getIdade());
            int linhas = statement.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return id;
    }

    public void logout() {
        this.treinadorLogado = null;
    }

    public boolean atualizar(Treinador treinador) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "UPDATE treinador SET user=?, senha=?, nome=?, idade=? WHERE id=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, treinador.getUser());
            statement.setString(2, treinador.getSenha());
            statement.setString(3, treinador.getNome());
            statement.setInt(4, treinador.getIdade());
            statement.setLong(5, treinador.getId());
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            retorno = false;
        } finally {
            this.conexao.fecharConexao();
        }
        return retorno;
    }

    public boolean remover(long id) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "DELETE FROM treinador WHERE id=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            retorno = false;
        } finally {
            this.conexao.fecharConexao();
        }
        return retorno;
    }
}
