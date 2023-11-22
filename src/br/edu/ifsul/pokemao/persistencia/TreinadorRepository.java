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
}
