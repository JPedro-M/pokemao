package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.utils.JsonDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe para gerenciar os acontecimentos do sistema.
 * <p>
 * Esta classe fornece métodos para adicionar acontecimentos de trocas e batalhas
 * ao banco de dados, além de fornecer métodos para listar acontecimentos de um
 * treinador ou todos do banco de dados.
 * 
 * @see Troca
 * @see Batalha
 * @see Acontecimento
 */
public class AcontecimentoRepository {
    private final String FILE_TROCAS = "trocas.json";
    private final String FILE_BATALHAS = "batalhas.json";

    public AcontecimentoRepository() {
    }

    public void adicionarTroca(Troca troca) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE_TROCAS);
            long nextId = 1;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid >= nextId) nextId = oid + 1;
            }
            Map<String, String> obj = new java.util.HashMap<>();
            obj.put("id", String.valueOf(nextId));
            obj.put("id_pokemao_treinador_1", String.valueOf(troca.getPokemaoOfertado().getId()));
            obj.put("id_pokemao_treinador_2", String.valueOf(troca.getPokemaoResposta().getId()));
            obj.put("data_troca", troca.getData().toString());
            obj.put("id_usuario_1", String.valueOf(troca.getTreinadorInicial().getId()));
            obj.put("id_usuario_2", String.valueOf(troca.getTreinadorEscolhido().getId()));
            list.add(obj);
            JsonDB.write(FILE_TROCAS, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarBatalha(Batalha batalha) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE_BATALHAS);
            long nextId = 1;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid >= nextId) nextId = oid + 1;
            }
            Map<String, String> obj = new java.util.HashMap<>();
            obj.put("id", String.valueOf(nextId));
            obj.put("id_pokemao_treinador_1", String.valueOf(batalha.getPokemaoInicial().getId()));
            obj.put("id_pokemao_treinador_2", String.valueOf(batalha.getPokemaoEscolhido().getId()));
            obj.put("data_batalha", batalha.getData().toString());
            obj.put("id_pokemao_vencedor", String.valueOf(batalha.getVencedor().getId()));
            obj.put("id_usuario_1", String.valueOf(batalha.getTreinadorInicial().getId()));
            obj.put("id_usuario_2", String.valueOf(batalha.getTreinadorEscolhido().getId()));
            list.add(obj);
            JsonDB.write(FILE_BATALHAS, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Troca> listarTrocas() {
        ArrayList<Troca> lista = new ArrayList<>();
        try {
            List<Map<String, String>> list = JsonDB.read(FILE_TROCAS);
            for (Map<String, String> obj : list) {
                try {
                    PokemaoTreinadorRepository ptr = new PokemaoTreinadorRepository();
                    TreinadorRepository tr = new TreinadorRepository();
                    var p1 = ptr.buscarPorId(Long.parseLong(obj.get("id_pokemao_treinador_1")));
                    var p2 = ptr.buscarPorId(Long.parseLong(obj.get("id_pokemao_treinador_2")));
                    Troca t = new Troca(p1, p2);
                    t.setId(Long.parseLong(obj.get("id")));
                    t.setData(LocalDateTime.parse(obj.get("data_troca")));
                    t.setTreinadorInicial(tr.buscarPorID(Long.parseLong(obj.get("id_usuario_1"))));
                    t.setTreinadorEscolhido(tr.buscarPorID(Long.parseLong(obj.get("id_usuario_2"))));
                    lista.add(t);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // reverse to keep same behavior as earlier (newest first)
        ArrayList<Troca> reverted = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--) reverted.add(lista.get(i));
        return reverted;
    }

    public ArrayList<Batalha> listarBatalhas() {
        ArrayList<Batalha> lista = new ArrayList<>();
        try {
            List<Map<String, String>> list = JsonDB.read(FILE_BATALHAS);
            for (Map<String, String> obj : list) {
                try {
                    PokemaoTreinadorRepository ptr = new PokemaoTreinadorRepository();
                    TreinadorRepository tr = new TreinadorRepository();
                    var p1 = ptr.buscarPorId(Long.parseLong(obj.get("id_pokemao_treinador_1")));
                    var p2 = ptr.buscarPorId(Long.parseLong(obj.get("id_pokemao_treinador_2")));
                    Batalha b = new Batalha(p1, p2);
                    b.setId(Long.parseLong(obj.get("id")));
                    b.setData(LocalDateTime.parse(obj.get("data_batalha")));
                    long vencedorId = Long.parseLong(obj.get("id_pokemao_vencedor"));
                    if (vencedorId == b.getPokemaoInicial().getId()) b.setVencedor(true);
                    else b.setVencedor(false);
                    b.setTreinadorInicial(tr.buscarPorID(Long.parseLong(obj.get("id_usuario_1"))));
                    b.setTreinadorEscolhido(tr.buscarPorID(Long.parseLong(obj.get("id_usuario_2"))));
                    lista.add(b);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Batalha> reverted = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--) reverted.add(lista.get(i));
        return reverted;
    }

    public ArrayList<Troca> listarTrocasPorTreinador(long id) {
        ArrayList<Troca> resultado = new ArrayList<>();
        try {
            for (Troca t : listarTrocas()) {
                if (t.getTreinadorInicial() != null && t.getTreinadorInicial().getId() == id) resultado.add(t);
                else if (t.getTreinadorEscolhido() != null && t.getTreinadorEscolhido().getId() == id) resultado.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<Batalha> listarBatalhasPorTreinador(long id) {
        ArrayList<Batalha> resultado = new ArrayList<>();
        try {
            for (Batalha b : listarBatalhas()) {
                if (b.getTreinadorInicial() != null && b.getTreinadorInicial().getId() == id) resultado.add(b);
                else if (b.getTreinadorEscolhido() != null && b.getTreinadorEscolhido().getId() == id) resultado.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

}