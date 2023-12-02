package br.edu.ifsul.pokemao.utils;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.Pokemao;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class ListaMaker {
    public static ArrayList<Pokemao> ResultSettoListPokemaoCatalogo(ResultSet rs) {
        ArrayList<Pokemao> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Pokemao pokemaoCatalogo = new Pokemao(
                        rs.getLong("id"),
                        rs.getString("emoji"),
                        rs.getString("nome"),
                        rs.getInt("ataque"),
                        rs.getInt("defesa"),
                        rs.getInt("hp"),
                        rs.getInt("raridade"));
                lista.add(pokemaoCatalogo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ArrayList<PokemaoTreinador> ResultSettoListPokemaoTreinador(ResultSet rs) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                PokemaoTreinador pokemaoTreinador = new PokemaoTreinador(
                        rs.getLong("id"),
                        new PokemaoCatalogoRepository().buscarPorId(rs.getLong("id_pokemao_catalogo")),
                        new TreinadorRepository().buscarPorId(rs.getLong("id_treinador")),
                        rs.getInt("velocidade_ataque"),
                        rs.getInt("ataque"),
                        rs.getInt("defesa"),
                        rs.getInt("hp"),
                        rs.getBoolean("disponivel_para_troca"),
                        rs.getDouble("xp"),
                        rs.getTimestamp("data_captura").toLocalDateTime());
                lista.add(pokemaoTreinador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ArrayList<Troca> ResultSettoListTroca(ResultSet rs) {
        ArrayList<Troca> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Troca troca = new Troca(
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_1")),
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_2")));
                troca.setId(rs.getLong("id_troca"));
                troca.setData(rs.getTimestamp("data").toLocalDateTime());
                troca.setTreinadorInicial(new TreinadorRepository().buscarPorId(rs.getLong("id_usuario_1")));
                troca.setTreinadorEscolhido(new TreinadorRepository().buscarPorId(rs.getLong("id_usuario_2")));
                lista.add(troca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static ArrayList<Batalha> ResultSettoListBatalha(ResultSet rs) {
        ArrayList<Batalha> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Batalha batalha = new Batalha(
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_1")),
                        new PokemaoTreinadorRepository().buscarPorId(rs.getLong("id_pokemao_treinador_2")));
                batalha.setId(rs.getLong("id_batalha"));
                batalha.setData(rs.getTimestamp("data").toLocalDateTime());
                if (rs.getLong("id_pokemao_vencedor") == batalha.getPokemaoInicial().getId()) {
                    batalha.setVencedor(true);
                } else {
                    batalha.setVencedor(false);
                }
                batalha.setTreinadorInicial(new TreinadorRepository().buscarPorId(rs.getLong("id_usuario_1")));
                batalha.setTreinadorEscolhido(new TreinadorRepository().buscarPorId(rs.getLong("id_usuario_2")));
                lista.add(batalha);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
