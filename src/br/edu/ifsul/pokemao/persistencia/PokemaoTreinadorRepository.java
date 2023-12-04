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
        this.conexao = new ConexaoMySQL(BDConfigs.IP, BDConfigs.PORTA, BDConfigs.USUARIO, BDConfigs.SENHA,
                BDConfigs.NOME_BD);
    }

    public ArrayList<PokemaoTreinador> listar() {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao("listar, pokemaoTreinadorRepository");
            String sqlInsert = "SELECT * FROM pokemao_treinador";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoTreinador(rs, this.conexao);
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
            this.conexao.abrirConexao("listarDoTreinador, pokemaoTreinadorRepository");
            String sqlInsert = "SELECT * FROM pokemao_treinador WHERE id_treinador=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, treinador.getId());
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoTreinador(rs, this.conexao);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.conexao.fecharConexao();
        }
        return lista;

    }

    public ArrayList<PokemaoTreinador> listarDisponiveisParaTroca(Treinador ignorarTreinador) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            this.conexao.abrirConexao("listarDisponiveisParaTroca, pokemaoTreinadorRepository");
            String sqlInsert = "SELECT * FROM pokemao_treinador";
            if (ignorarTreinador != null) {
                sqlInsert += " WHERE disponivel_para_troca = true AND id_treinador != ?";
            } else {
                sqlInsert += " WHERE disponivel_para_troca = true";
            }
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            if (ignorarTreinador != null) {
                statement.setLong(1, ignorarTreinador.getId());
            }
            ResultSet rs = statement.executeQuery();
            lista = ListaMaker.ResultSettoListPokemaoTreinador(rs, this.conexao);
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
            this.conexao.abrirConexao("buscarPorId, pokemaoTreinadorRepository");
            String sqlInsert = "SELECT * FROM pokemao_treinador WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, l);
            ResultSet rs = statement.executeQuery();
            try {
                if (rs.next()) {
                    pokemaoTreinador = new PokemaoTreinador(
                            new PokemaoCatalogoRepository().buscarPorId(rs.getLong("id_pokemao_catalogo")),
                            new TreinadorRepository().buscarPorID(rs.getLong("id_treinador")),
                            rs.getInt("velocidade_ataque"),
                            rs.getInt("ataque"),
                            rs.getInt("defesa"),
                            rs.getInt("hp"),
                            rs.getBoolean("disponivel_para_troca"),
                            rs.getDouble("xp"),
                            rs.getTimestamp("data_captura").toLocalDateTime());
                    pokemaoTreinador.setId(rs.getLong("id_pokemao"));
                    pokemaoTreinador.setNome(pokemaoTreinador.getPokemao().getNome());
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
            this.conexao.abrirConexao("cadastrar, pokemaoTreinadorRepository");
            String sqlInsert = "INSERT INTO pokemao_treinador VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setInt(1, pokemaoTreinador.getVelocidadeAtaque());
            statement.setInt(2, pokemaoTreinador.getAtaque());
            statement.setInt(3, pokemaoTreinador.getDefesa());
            statement.setBoolean(4, pokemaoTreinador.isDisponivelParaTroca());
            statement.setDouble(5, pokemaoTreinador.getXp());
            statement.setTimestamp(6, java.sql.Timestamp.valueOf(pokemaoTreinador.getDataCaptura()));
            statement.setLong(7, pokemaoTreinador.getPokemao().getId());
            statement.setLong(8, pokemaoTreinador.getTreinador().getId());
            statement.setString(9, pokemaoTreinador.getNome());
            statement.setInt(10, pokemaoTreinador.getHp());
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
            this.conexao.abrirConexao("libertar, pokemaoTreinadorRepository");
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
            this.conexao.abrirConexao("transferir, pokemaoTreinadorRepository");
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
            this.conexao.abrirConexao("trocar, pokemaoTreinadorRepository");
            String sqlInsert = "UPDATE pokemao_treinador SET id_treinador=?, disponivel_para_troca=? WHERE id_pokemao=?";
            PreparedStatement statement = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement.setLong(1, pokemaoTreinador2.getTreinador().getId());
            statement.setBoolean(2, false);
            statement.setLong(3, pokemaoTreinador1.getId());
            PreparedStatement statement2 = this.conexao.getConexao().prepareStatement(sqlInsert);
            statement2.setLong(1, pokemaoTreinador1.getTreinador().getId());
            statement2.setBoolean(2, false);
            statement2.setLong(3, pokemaoTreinador2.getId());
            int linhasAfetadas = statement.executeUpdate() + statement2.executeUpdate();
            resultado = linhasAfetadas > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean curar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao("curar, pokemaoTreinadorRepository");
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

    public PokemaoTreinador gerarPokemaoSelvagem() {
        this.conexao.abrirConexao("gerarPokemaoSelvagem, pokemaoTreinadorRepository");

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
        return novo;

        // 70% de chance de ser um pokemao de raridade normal, 20% de chance de ser um
        // pokemao de raridade raro, 10% de chance de ser um pokemao de raridade
        // lendário
    }

    public boolean editar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            this.conexao.abrirConexao("editar, pokemaoTreinadorRepository");
            String sqlInsert = "UPDATE pokemao_treinador SET id_pokemao_catalogo=?, id_treinador=?, velocidade_ataque=?,"
                    +
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
