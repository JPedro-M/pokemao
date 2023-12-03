package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.Pokemao;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.utils.BDConfigs;
import br.edu.ifsul.pokemao.utils.ConexaoMySQL;
import br.edu.ifsul.pokemao.utils.ListaMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PokemaoTreinadorRepository {
    // métodos para listar, listardotreinador, cadastrar, libertar (excluir),
    // trocar, batalhar, curar, encontrarnanatureza, marcarcomodisponivel
    // conexao com o banco de dados

    private ConexaoMySQL conexao;

    public PokemaoTreinadorRepository() {
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA, BDConfigs.NOME_BD);
    }

    public int getLenPokemaoTreinador() {
        int len = 0;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT COUNT(*) FROM pokemao_treinador";
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

    public ArrayList<PokemaoTreinador> listar() {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM pokemao_treinador";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoTreinador(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;
    }

    public ArrayList<PokemaoTreinador> listarDoTreinador(Treinador treinador) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM pokemao_treinador WHERE id_treinador=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, treinador.getId());
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoTreinador(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;

    }

    public PokemaoTreinador buscarPorId(long l) {
        PokemaoTreinador pokemaoTreinador = null;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "SELECT * FROM pokemao_treinador WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, l);
            ResultSet rs = statement.executeQuery();
            try {
                if (rs.next()) {
                    pokemaoTreinador = new PokemaoTreinador(
                            rs.getLong("id_pokemao"),
                            new PokemaoCatalogoRepository().buscarPorId(rs.getLong("id_pokemao_catalogo")),
                            new TreinadorRepository().buscarPorId(rs.getLong("id_treinador")),
                            rs.getInt("velocidade_ataque"),
                            rs.getInt("ataque"),
                            rs.getInt("defesa"),
                            rs.getInt("hp"),
                            rs.getBoolean("disponivel_para_troca"),
                            rs.getDouble("xp"),
                            rs.getTimestamp("data_captura").toLocalDateTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rs.close();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return pokemaoTreinador;
    }

    public boolean cadastrar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "INSERT INTO pokemao_treinador VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, pokemaoTreinador.getPokemao().getId());
            statement.setLong(2, pokemaoTreinador.getTreinador().getId());
            statement.setInt(3, pokemaoTreinador.getVelocidadeAtaque());
            statement.setInt(4, pokemaoTreinador.getAtaque());
            statement.setInt(5, pokemaoTreinador.getDefesa());
            statement.setInt(6, pokemaoTreinador.getHp());
            statement.setBoolean(7, pokemaoTreinador.isDisponivelParaTroca());
            statement.setDouble(8, pokemaoTreinador.getXp());
            statement.setTimestamp(9, java.sql.Timestamp.valueOf(pokemaoTreinador.getDataCaptura()));
            int linhasAfetadas = statement.executeUpdate();
            resultado = linhasAfetadas > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return resultado;
    }

    public boolean libertar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "DELETE FROM pokemao_treinador WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, pokemaoTreinador.getId());
            int linhasAfetadas = statement.executeUpdate();
            resultado = linhasAfetadas > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return resultado;
    }

    // uma troca é composta por duas transferências
    protected boolean transferir(PokemaoTreinador pokemaoTreinador1, Treinador treinador2) {
        return transferir(pokemaoTreinador1, treinador2, false);
    }

    protected boolean transferir(PokemaoTreinador pokemaoTreinador1, Treinador treinador2,
            boolean mudarDisponivelParaTroca) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "UPDATE pokemao_treinador SET id_treinador=?, disponivel_para_troca=? WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, treinador2.getId());
            if (mudarDisponivelParaTroca) {
                statement.setBoolean(2, false);
            }
            statement.setLong(3, pokemaoTreinador1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return resultado;
    }

    public boolean trocar(PokemaoTreinador pokemaoTreinador1, PokemaoTreinador pokemaoTreinador2) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            this.conexao.getConexao().setAutoCommit(false);
            resultado = transferir(pokemaoTreinador1, pokemaoTreinador2.getTreinador());
            resultado = transferir(pokemaoTreinador2, pokemaoTreinador1.getTreinador());
            this.conexao.getConexao().commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.conexao.getConexao().rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                this.conexao.getConexao().setAutoCommit(true);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.conexao.fecharConexao();
        }
        return resultado;
    }

    public boolean curar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "UPDATE pokemao_treinador SET hp=? WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setInt(1, pokemaoTreinador.getPokemao().getHp());
            statement.setLong(2, pokemaoTreinador.getId());
            int linhasAfetadas = statement.executeUpdate();
            resultado = linhasAfetadas > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return resultado;
    }

    public Pokemao gerarWildPokemao() {
        this.conexao.abrirConexao();

        int random = (int) (Math.random() * 10) + 1;
        PokemaoTreinador novo = null;
        if (random <= 7) {
            ArrayList<Pokemao> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(1);
            int random2 = (int) (Math.random() * pokemaos.size());
            Pokemao pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        } else if (random <= 9) {
            ArrayList<Pokemao> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(2);
            int random2 = (int) (Math.random() * pokemaos.size());
            Pokemao pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        } else {
            ArrayList<Pokemao> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(3);
            int random2 = (int) (Math.random() * pokemaos.size());
            Pokemao pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        }

        this.conexao.fecharConexao();
        return novo.getPokemao();

        // 70% de chance de ser um pokemao de raridade normal, 20% de chance de ser um
        // pokemao de raridade raro, 10% de chance de ser um pokemao de raridade lendário
    }

    public boolean editar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao();
            String sqlInsert = "UPDATE pokemao_treinador SET id_pokemao_catalogo=?, id_treinador=?, velocidade_ataque=?,"+
                            "ataque=?, defesa=?, hp=?, disponivel_para_troca=?, xp=?, data_captura=? WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, pokemaoTreinador.getPokemao().getId());
            statement.setLong(2, pokemaoTreinador.getTreinador().getId());
            statement.setInt(3, pokemaoTreinador.getVelocidadeAtaque());
            statement.setInt(4, pokemaoTreinador.getAtaque());
            statement.setInt(5, pokemaoTreinador.getDefesa());
            statement.setInt(6, pokemaoTreinador.getHp());
            statement.setBoolean(7, pokemaoTreinador.isDisponivelParaTroca());
            statement.setDouble(8, pokemaoTreinador.getXp());
            statement.setTimestamp(9, java.sql.Timestamp.valueOf(pokemaoTreinador.getDataCaptura()));
            statement.setLong(10, pokemaoTreinador.getId());
            int linhasAfetadas = statement.executeUpdate();
            resultado = linhasAfetadas > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return resultado;
    }
}
    