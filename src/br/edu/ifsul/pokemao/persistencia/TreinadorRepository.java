package br.edu.ifsul.pokemao.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;

public class TreinadorRepository {
    // métodos para login, cadastro, logout
    // conexao com o banco de dados

    Treinador treinadorLogado;

    private ConexaoMySQL conexao;

    public TreinadorRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public Treinador getTreinadorLogado() {
        return treinadorLogado;
    }

    public void setTreinadorLogado(Treinador treinadorLogado) {
        this.treinadorLogado = treinadorLogado;
    }

    public int getLenTreinadores() {
        int len = 0;
        try {
            this.conexao.abrirConexao("getLenTreinadores");
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
            this.conexao.abrirConexao("buscarPorUser, treinadorRepository");
            String sqlInsert = "SELECT * FROM treinador WHERE usuario = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                treinador = new Treinador(
                    rs.getString("usuario"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getTimestamp("nascimento").toLocalDateTime()
                );
                treinador.setId(rs.getLong("id_treinador"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return treinador;
    }

    public Treinador buscarPorID(long id){
        Treinador treinador = null;
        try {
            this.conexao.abrirConexao("buscarPorID, treinadorRepository");
            treinador = buscarPorID(id, this.conexao);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return treinador;
    }

    public Treinador buscarPorID(long id, ConexaoMySQL conexao) {
        Treinador treinador = null;
        try {
            String sqlInsert = "SELECT * FROM treinador WHERE id_treinador = ?";
            PreparedStatement statement = conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                treinador = new Treinador(
                    rs.getString("usuario"),
                    rs.getString("senha"),
                    rs.getString("nome"),
                    rs.getTimestamp("nascimento").toLocalDateTime()
                );
                treinador.setId(rs.getLong("id_treinador"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        long id = -1;
        try {
            this.conexao.abrirConexao("cadastrar, treinadorRepository");
            String sqlInsert = "INSERT INTO treinador(id_treinador, usuario, senha, nome, nascimento) VALUES(null, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, treinador.getUser());
            statement.setString(2, treinador.getSenha());
            statement.setString(3, treinador.getNome());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(treinador.getNascimento()));
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
            this.conexao.abrirConexao("atualizar, treinadorRepository");
            String sqlInsert = "UPDATE treinador SET usuario=?, senha=?, nome=?, nascimento=? WHERE id_treinador=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, treinador.getUser());
            statement.setString(2, treinador.getSenha());
            statement.setString(3, treinador.getNome());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(treinador.getNascimento()));
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
            this.conexao.abrirConexao("remover, treinadorRepository");
            String sqlInsert = "DELETE FROM treinador WHERE id_treinador=?";
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
