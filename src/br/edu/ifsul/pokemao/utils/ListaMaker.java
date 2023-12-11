package br.edu.ifsul.pokemao.utils;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

/**
 * Esta classe contém métodos estáticos para converter objetos ResultSet em
 * ArrayLists de objetos específicos.
 */
public class ListaMaker {

    /**
     * Converte um ResultSet em uma lista de objetos PokemaoCatalogo.
     * 
     * @param rs O ResultSet contendo os dados do PokemaoCatalogo.
     * @return Uma lista de objetos PokemaoCatalogo.
     */
    public static ArrayList<PokemaoCatalogo> ResultSettoListPokemaoCatalogo(ResultSet rs) {
        ArrayList<PokemaoCatalogo> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                PokemaoCatalogo pokemaoCatalogo = new PokemaoCatalogo(
                        rs.getLong("id_pokemao_catalogo"),
                        rs.getString("emoji"),
                        rs.getString("nome"),
                        rs.getInt("ataque"),
                        rs.getInt("defesa"),
                        rs.getInt("raridade"),
                        rs.getString("foto"),
                        rs.getString("descricao"));
                lista.add(pokemaoCatalogo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Converte um ResultSet em uma lista de objetos PokemaoTreinador.
     * <p>
     * A conexão é passada como parâmetro para evitar que a conexão seja aberta e
     * fechada várias vezes.
     * 
     * @param rs      O ResultSet contendo os dados do PokemaoTreinador.
     * @param conexao A conexão com o banco de dados.
     * @return Uma lista de objetos PokemaoTreinador.
     */
    public static ArrayList<PokemaoTreinador> ResultSettoListPokemaoTreinador(ResultSet rs, ConexaoMySQL conexao) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                PokemaoTreinador pokemaoTreinador = new PokemaoTreinador(
                        new PokemaoCatalogoRepository().buscarPorId(rs.getLong("id_pokemao_catalogo"), conexao),
                        new TreinadorRepository().buscarPorID(rs.getLong("id_treinador"), conexao),
                        rs.getInt("velocidade_ataque"),
                        rs.getInt("ataque"),
                        rs.getInt("defesa"),
                        rs.getInt("hp"),
                        rs.getBoolean("disponivel_para_troca"),
                        rs.getDouble("xp"),
                        rs.getTimestamp("data_captura").toLocalDateTime());
                pokemaoTreinador.setId(rs.getLong("id_pokemao"));
                pokemaoTreinador.setNome(rs.getString("nome_custom"));
                lista.add(pokemaoTreinador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Converte um ResultSet em uma lista de objetos Troca.
     * 
     * @param rs O ResultSet contendo os dados da Troca.
     * @return Uma lista de objetos Troca.
     */
    public static ArrayList<Troca> ResultSettoListTroca(ResultSet rs, ConexaoMySQL conexao) {
        ArrayList<Troca> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Troca troca = new Troca(
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_1"), conexao),
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_2"), conexao));
                troca.setId(rs.getLong("id_troca"));
                troca.setData(rs.getTimestamp("data_troca").toLocalDateTime());
                troca.setTreinadorInicial(new TreinadorRepository().buscarPorID(rs.getLong("id_usuario_1"), conexao));
                troca.setTreinadorEscolhido(new TreinadorRepository().buscarPorID(rs.getLong("id_usuario_2"), conexao));
                lista.add(troca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Troca> revertedList = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--) {
            revertedList.add(lista.get(i));
        }
        return revertedList;
    }

    /**
     * Converte um ResultSet em uma lista de objetos Batalha.
     * 
     * @param rs O ResultSet contendo os dados da Batalha.
     * @return Uma lista de objetos Batalha.
     */
    public static ArrayList<Batalha> ResultSettoListBatalha(ResultSet rs, ConexaoMySQL conexao) {
        ArrayList<Batalha> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Batalha batalha = new Batalha(
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_1"), conexao),
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_2"), conexao));
                batalha.setId(rs.getLong("id_batalha"));
                batalha.setData(rs.getTimestamp("data_batalha").toLocalDateTime());
                if (rs.getLong("id_pokemao_vencedor") == batalha.getPokemaoInicial().getId()) {
                    batalha.setVencedor(true);
                } else {
                    batalha.setVencedor(false);
                }
                batalha.setTreinadorInicial(new TreinadorRepository().buscarPorID(rs.getLong("id_usuario_1"), conexao));
                batalha.setTreinadorEscolhido(new TreinadorRepository().buscarPorID(rs.getLong("id_usuario_2"), conexao));
                lista.add(batalha);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Batalha> revertedList = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--) {
            revertedList.add(lista.get(i));
        }
        return revertedList;
    }
}
