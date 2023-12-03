package br.edu.ifsul.pokemao.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;

public class RaridadeRepository {
    // métodos para retornar nome e cor de uma raridade
    // a partir do id (1, 2, 3)
    // conexao com o banco de dados

    private ConexaoMySQL conexao;

    public RaridadeRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public String nomeRaridade (int id) {
        String nome = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT nome FROM raridade WHERE id_raridade = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return nome;
    }

    public String corRaridade (int id) {
        String cor = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT hex_cor FROM raridade WHERE id_raridade = ?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cor = rs.getString("hex_cor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return cor;
    }
    
}
