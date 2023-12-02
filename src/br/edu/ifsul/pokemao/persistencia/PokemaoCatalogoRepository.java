package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.Pokemao;
import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;
import br.edu.ifsul.pokemao.utils.ListaMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PokemaoCatalogoRepository {
    // métodos para listar, cadastrar, editar, excluir
    // conexao com o banco de dados
    
    // o método de exclusão também deve excluir os pokémaos de treinadores

    private ConexaoMySQL conexao;

    public PokemaoCatalogoRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }



    public ArrayList<Pokemao> listar() {
        ArrayList<Pokemao> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM pokemao_catalogo";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoCatalogo(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

    public Pokemao buscarPorId(long l) {
        Pokemao pokemaoCatalogo = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM pokemao_catalogo WHERE id=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, l);
            ResultSet rs = statement.executeQuery();
            try {
                if (rs.next()) {
                    pokemaoCatalogo = 
                        new Pokemao(
                            rs.getLong("id"),
                            rs.getString("emoji"),
                            rs.getString("nome"),
                            rs.getInt("ataque"),
                            rs.getInt("defesa"),
                            rs.getInt("hp"),
                            rs.getInt("raridade")
                        );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return pokemaoCatalogo;
    }

    public boolean cadastrar(Pokemao pokemaoCatalogo) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "INSERT INTO pokemao_catalogo(emoji, nome, ataque, defesa, hp, raridade) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, pokemaoCatalogo.getEmoji());
            statement.setString(2, pokemaoCatalogo.getNome());
            statement.setInt(3, pokemaoCatalogo.getAtaque());
            statement.setInt(4, pokemaoCatalogo.getDefesa());
            statement.setInt(5, pokemaoCatalogo.getHp());
            statement.setInt(6, pokemaoCatalogo.getRaridade());
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

    public boolean editar(Pokemao pokemaoCatalogo) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "UPDATE pokemao_catalogo SET emoji=?, nome=?, ataque=?, defesa=?, hp=?, raridade=? WHERE id=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, pokemaoCatalogo.getEmoji());
            statement.setString(2, pokemaoCatalogo.getNome());
            statement.setInt(3, pokemaoCatalogo.getAtaque());
            statement.setInt(4, pokemaoCatalogo.getDefesa());
            statement.setInt(5, pokemaoCatalogo.getHp());
            statement.setInt(6, pokemaoCatalogo.getRaridade());
            statement.setLong(7, pokemaoCatalogo.getId());
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

    public boolean deletar(int id) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "DELETE FROM pokemao_catalogo WHERE id=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setInt(1, id);
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

    public ArrayList<Pokemao> listarPorRaridade(int x) {
        ArrayList<Pokemao> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlSelect = "SELECT * FROM pokemao_catalogo WHERE raridade = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlSelect);
            statement.setInt(1, x);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoCatalogo(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }
}