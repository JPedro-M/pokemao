package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;
import br.edu.ifsul.pokemao.utils.ListaMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Timestamp;

public class AcontecimentoRepository {
    // métodos para adicionar acontecimentos: trocas e batalhas
    // métodos para listar acontecimentos de um treinador ou todos em um periodo de tempo
    private ConexaoMySQL conexao;

    public AcontecimentoRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public void adicionarTroca(Troca troca) {
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "INSERT INTO troca(id_troca, id_pokemao_treinador_1, id_pokemao_treinador_2,"+
                            "data_troca, id_usuario_1, id_usuario_2) VALUES(null, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);

            statement.setLong(1, troca.getPokemaoOfertado().getId());
            statement.setLong(2, troca.getPokemaoResposta().getId());
            statement.setTimestamp(3, Timestamp.valueOf(troca.getData()));
            statement.setLong(4, troca.getTreinadorInicial().getId());
            statement.setLong(5, troca.getTreinadorEscolhido().getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
    }

    public void adicionarBatalha(Batalha batalha) {
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "INSERT INTO batalha(id_batalha, id_pokemao_treinador_1, id_pokemao_treinador_2, " +
                    "data_batalha, id_pokemao_vencedor, id_usuario_1, id_usuario_2) VALUES(null, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);

            statement.setLong(1, batalha.getPokemaoInicial().getId());
            statement.setLong(2, batalha.getPokemaoEscolhido().getId());
            statement.setTimestamp(3, Timestamp.valueOf(batalha.getData()));
            if (batalha.isInicialVencedor()) {
                statement.setLong(4, batalha.getPokemaoInicial().getId());
            } else {
                statement.setLong(4, batalha.getPokemaoEscolhido().getId());
            }
            statement.setLong(5, batalha.getTreinadorInicial().getId());
            statement.setLong(6, batalha.getTreinadorEscolhido().getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
    }


    public ArrayList<Troca> listarTrocas() {
        ArrayList<Troca> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM troca";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListTroca(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

    public ArrayList<Batalha> listarBatalhas() {
        ArrayList<Batalha> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM batalha";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListBatalha(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

    public ArrayList<Troca> listarTrocasPorTreinador(long id) {
        ArrayList<Troca> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM troca WHERE id_usuario_1=? OR id_usuario_2=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListTroca(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

    public ArrayList<Batalha> listarBatalhasPorTreinador(long id) {
        ArrayList<Batalha> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM batalha WHERE id_usuario_1=? OR id_usuario_2=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListBatalha(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

}