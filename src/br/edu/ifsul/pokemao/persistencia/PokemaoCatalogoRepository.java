package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;
import br.edu.ifsul.pokemao.utils.ListaMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe para gerenciar os pokemaos do catálogo do sistema.
 * <p>
 * Esta classe fornece métodos para buscar pokemaos no banco de dados,
 * cadastrar, editar e excluir pokemaos do banco de dados.
 * 
 * @see PokemaoCatalogo 
 */
public class PokemaoCatalogoRepository {
    // métodos para listar, cadastrar, editar, excluir
    // conexao com o banco de dados
    
    // o método de exclusão também deve excluir os pokémaos de treinadores

    private ConexaoMySQL conexao;

    public PokemaoCatalogoRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public ArrayList<PokemaoCatalogo> listar() {
        ArrayList<PokemaoCatalogo> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao("listar, pokemaoCatalogoRepository");
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

    public PokemaoCatalogo buscarPorId(long id_busca){
        PokemaoCatalogo pokemaoCatalogo = null;
        try {
            this.conexao.abrirConexao("buscarPorId, pokemaoCatalogoRepository");
            pokemaoCatalogo = buscarPorId(id_busca, this.conexao);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return pokemaoCatalogo;
    }

    public PokemaoCatalogo buscarPorId(long id_busca, ConexaoMySQL conexao) {
        PokemaoCatalogo pokemaoCatalogo = null;
        try {
            String sqlInsert = "SELECT * FROM pokemao_catalogo WHERE id_pokemao_catalogo=?";
            PreparedStatement statement = conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id_busca);
            ResultSet rs = statement.executeQuery();
            try {
                if (rs.next()) {
                    pokemaoCatalogo = 
                        new PokemaoCatalogo(
                            rs.getLong("id_pokemao_catalogo"),
                            rs.getString("emoji"),
                            rs.getString("nome"),
                            rs.getInt("ataque"),
                            rs.getInt("defesa"),
                            rs.getInt("raridade"),
                            rs.getString("foto"),
                            rs.getString("descricao")
                        );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pokemaoCatalogo;
    }

    public boolean cadastrar(PokemaoCatalogo pokemaoCatalogo) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao("cadastrar, pokemaoCatalogoRepository");
            String sqlInsert = "INSERT INTO pokemao_catalogo(id_pokemao_catalogo, emoji, nome, ataque, defesa, hp,"+
                                "raridade, descricao) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, pokemaoCatalogo.getId());
            statement.setString(2, pokemaoCatalogo.getEmoji());
            statement.setString(3, pokemaoCatalogo.getNome());
            statement.setInt(4, pokemaoCatalogo.getAtaque());
            statement.setInt(5, pokemaoCatalogo.getDefesa());
            statement.setInt(7, pokemaoCatalogo.getRaridade());
            statement.setString(8, pokemaoCatalogo.getDescricao());
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

    public boolean editar(PokemaoCatalogo pokemaoCatalogo) {
        boolean retorno = false;
        try {
            this.conexao.abrirConexao("editar, pokemaoCatalogoRepository");
            String sqlInsert = "UPDATE pokemao_catalogo SET emoji=?, nome=?, ataque=?, defesa=?, hp=?,"+
                            "raridade=?, descricao=? WHERE id_pokemao_catalogo=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setString(1, pokemaoCatalogo.getEmoji());
            statement.setString(2, pokemaoCatalogo.getNome());
            statement.setInt(3, pokemaoCatalogo.getAtaque());
            statement.setInt(4, pokemaoCatalogo.getDefesa());
            statement.setInt(6, pokemaoCatalogo.getRaridade());
            statement.setString(7, pokemaoCatalogo.getDescricao());
            statement.setLong(8, pokemaoCatalogo.getId());
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
            this.conexao.abrirConexao("deletar, pokemaoCatalogoRepository");
            String sqlInsert = "DELETE FROM pokemao_catalogo WHERE id_pokemao_catalogo=?";
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

    public ArrayList<PokemaoCatalogo> listarPorRaridade(int x) {
        ArrayList<PokemaoCatalogo> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao("listarPorRaridade, pokemaoCatalogoRepository");
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