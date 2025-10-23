package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.utils.JsonDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private final String FILE = "pokemaos.json";

    public PokemaoCatalogoRepository() {
    }

    public ArrayList<PokemaoCatalogo> listar() {
        ArrayList<PokemaoCatalogo> lista = new ArrayList<>();
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                PokemaoCatalogo p = new PokemaoCatalogo(
                        Long.parseLong(obj.get("id")),
                        obj.get("emoji"),
                        obj.get("nome"),
                        Integer.parseInt(obj.getOrDefault("ataque", "0")),
                        Integer.parseInt(obj.getOrDefault("defesa", "0")),
                        Integer.parseInt(obj.getOrDefault("raridade", "1")),
                        obj.getOrDefault("foto", ""),
                        obj.getOrDefault("descricao", ""));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public PokemaoCatalogo buscarPorId(long id_busca){
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == id_busca) {
                    return new PokemaoCatalogo(
                            Long.parseLong(obj.get("id")),
                            obj.get("emoji"),
                            obj.get("nome"),
                            Integer.parseInt(obj.getOrDefault("ataque", "0")),
                            Integer.parseInt(obj.getOrDefault("defesa", "0")),
                            Integer.parseInt(obj.getOrDefault("raridade", "1")),
                            obj.getOrDefault("foto", ""),
                            obj.getOrDefault("descricao", "")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cadastrar(PokemaoCatalogo pokemaoCatalogo) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            long nextId = 1;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid >= nextId) nextId = oid + 1;
            }
            pokemaoCatalogo.setId(nextId);
            Map<String, String> obj = new java.util.HashMap<>();
            obj.put("id", String.valueOf(nextId));
            obj.put("emoji", pokemaoCatalogo.getEmoji());
            obj.put("nome", pokemaoCatalogo.getNome());
            obj.put("ataque", String.valueOf(pokemaoCatalogo.getAtaque()));
            obj.put("defesa", String.valueOf(pokemaoCatalogo.getDefesa()));
            obj.put("raridade", String.valueOf(pokemaoCatalogo.getRaridade()));
            obj.put("foto", pokemaoCatalogo.getFoto());
            obj.put("descricao", pokemaoCatalogo.getDescricao());
            list.add(obj);
            JsonDB.write(FILE, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(PokemaoCatalogo pokemaoCatalogo) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == pokemaoCatalogo.getId()) {
                    obj.put("emoji", pokemaoCatalogo.getEmoji());
                    obj.put("nome", pokemaoCatalogo.getNome());
                    obj.put("ataque", String.valueOf(pokemaoCatalogo.getAtaque()));
                    obj.put("defesa", String.valueOf(pokemaoCatalogo.getDefesa()));
                    obj.put("raridade", String.valueOf(pokemaoCatalogo.getRaridade()));
                    obj.put("descricao", pokemaoCatalogo.getDescricao());
                    obj.put("foto", pokemaoCatalogo.getFoto());
                    found = true;
                    break;
                }
            }
            if (found) JsonDB.write(FILE, list);
            return found;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean removed = list.removeIf(obj -> Long.parseLong(obj.get("id")) == id);
            if (removed) JsonDB.write(FILE, list);
            return removed;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<PokemaoCatalogo> listarPorRaridade(int x) {
        ArrayList<PokemaoCatalogo> lista = new ArrayList<>();
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                if (Integer.parseInt(obj.getOrDefault("raridade", "1")) == x) {
                    PokemaoCatalogo p = new PokemaoCatalogo(
                            Long.parseLong(obj.get("id")),
                            obj.get("emoji"),
                            obj.get("nome"),
                            Integer.parseInt(obj.getOrDefault("ataque", "0")),
                            Integer.parseInt(obj.getOrDefault("defesa", "0")),
                            Integer.parseInt(obj.getOrDefault("raridade", "1")),
                            obj.getOrDefault("foto", ""),
                            obj.getOrDefault("descricao", ""));
                    lista.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}