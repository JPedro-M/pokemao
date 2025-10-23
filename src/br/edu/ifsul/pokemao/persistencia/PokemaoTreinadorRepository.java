package br.edu.ifsul.pokemao.persistencia;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.utils.JsonDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Repositório baseado em arquivos JSON para PokemaoTreinador.
 */
public class PokemaoTreinadorRepository {
    private final String FILE = "pokemaos_treinadores.json";

    public PokemaoTreinadorRepository() {}

    public ArrayList<PokemaoTreinador> listar() {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                PokemaoCatalogo pc = new PokemaoCatalogoRepository().buscarPorId(Long.parseLong(obj.get("id_pokemao_catalogo")));
                Treinador t = null;
                if (obj.get("id_treinador") != null && !obj.get("id_treinador").isEmpty()) {
                    t = new TreinadorRepository().buscarPorID(Long.parseLong(obj.get("id_treinador")));
                }
                PokemaoTreinador pt = new PokemaoTreinador(
                        pc,
                        t,
                        Integer.parseInt(obj.getOrDefault("velocidade_ataque", "0")),
                        Integer.parseInt(obj.getOrDefault("ataque", "0")),
                        Integer.parseInt(obj.getOrDefault("defesa", "0")),
                        Integer.parseInt(obj.getOrDefault("hp", "100")),
                        Boolean.parseBoolean(obj.getOrDefault("disponivel_para_troca", "false")),
                        Double.parseDouble(obj.getOrDefault("xp", "0")),
                        LocalDateTime.parse(obj.getOrDefault("data_captura", LocalDateTime.now().toString()))
                );
                pt.setId(Long.parseLong(obj.get("id")));
                pt.setNome(obj.get("nome_custom"));
                lista.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<PokemaoTreinador> listarDoTreinador(Treinador treinador) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            List<PokemaoTreinador> all = this.listar();
            for (PokemaoTreinador pt : all) {
                if (pt.getTreinador() != null && pt.getTreinador().getId() == treinador.getId()) lista.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<PokemaoTreinador> listarDisponiveisParaTroca(Treinador ignorarTreinador) {
        ArrayList<PokemaoTreinador> lista = new ArrayList<>();
        try {
            List<PokemaoTreinador> all = this.listar();
            for (PokemaoTreinador pt : all) {
                if (pt.isDisponivelParaTroca()) {
                    if (ignorarTreinador == null || pt.getTreinador() == null || pt.getTreinador().getId() != ignorarTreinador.getId()) {
                        lista.add(pt);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public PokemaoTreinador buscarPorId(long l) {
        try {
            List<PokemaoTreinador> all = this.listar();
            for (PokemaoTreinador pt : all) {
                if (pt.getId() == l) return pt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cadastrar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            long nextId = 1;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid >= nextId) nextId = oid + 1;
            }
            pokemaoTreinador.setId(nextId);
            Map<String, String> obj = new java.util.HashMap<>();
            obj.put("id", String.valueOf(nextId));
            obj.put("velocidade_ataque", String.valueOf(pokemaoTreinador.getVelocidadeAtaque()));
            obj.put("ataque", String.valueOf(pokemaoTreinador.getAtaque()));
            obj.put("defesa", String.valueOf(pokemaoTreinador.getDefesa()));
            obj.put("disponivel_para_troca", String.valueOf(pokemaoTreinador.isDisponivelParaTroca()));
            obj.put("xp", String.valueOf(pokemaoTreinador.getXp()));
            obj.put("data_captura", pokemaoTreinador.getDataCaptura().toString());
            obj.put("id_pokemao_catalogo", String.valueOf(pokemaoTreinador.getPokemao().getId()));
            obj.put("id_treinador", pokemaoTreinador.getTreinador() == null ? "" : String.valueOf(pokemaoTreinador.getTreinador().getId()));
            obj.put("nome_custom", pokemaoTreinador.getNome());
            obj.put("hp", String.valueOf(pokemaoTreinador.getHp()));
            list.add(obj);
            JsonDB.write(FILE, list);
            resultado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean libertar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == pokemaoTreinador.getId()) {
                    obj.put("id_treinador", "");
                    obj.put("disponivel_para_troca", "false");
                    found = true;
                    break;
                }
            }
            if (found) JsonDB.write(FILE, list);
            resultado = found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    protected boolean transferir(PokemaoTreinador pokemaoTreinador1, Treinador treinador2) {
        return transferir(pokemaoTreinador1, treinador2, false);
    }

    protected boolean transferir(PokemaoTreinador pokemaoTreinador1, Treinador treinador2,
            boolean mudarDisponivelParaTroca) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == pokemaoTreinador1.getId()) {
                    obj.put("id_treinador", String.valueOf(treinador2.getId()));
                    if (mudarDisponivelParaTroca) obj.put("disponivel_para_troca", "false");
                    found = true;
                    break;
                }
            }
            if (found) JsonDB.write(FILE, list);
            resultado = found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean trocar(PokemaoTreinador pokemaoTreinador1, PokemaoTreinador pokemaoTreinador2) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found1 = false, found2 = false;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid == pokemaoTreinador1.getId()) {
                    obj.put("id_treinador", String.valueOf(pokemaoTreinador2.getTreinador().getId()));
                    obj.put("disponivel_para_troca", "false");
                    found1 = true;
                }
                if (oid == pokemaoTreinador2.getId()) {
                    obj.put("id_treinador", String.valueOf(pokemaoTreinador1.getTreinador().getId()));
                    obj.put("disponivel_para_troca", "false");
                    found2 = true;
                }
            }
            if (found1 && found2) {
                JsonDB.write(FILE, list);
                // register troca
                new AcontecimentoRepository().adicionarTroca(new br.edu.ifsul.pokemao.model.Troca(pokemaoTreinador1, pokemaoTreinador2));
            }
            resultado = found1 && found2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean curar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == pokemaoTreinador.getId()) {
                    obj.put("hp", "100");
                    found = true; break;
                }
            }
            if (found) JsonDB.write(FILE, list);
            resultado = found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public PokemaoTreinador gerarPokemaoSelvagem() {
        int random = (int) (Math.random() * 10) + 1;
        PokemaoTreinador novo = null;
        if (random <= 7) {
            ArrayList<PokemaoCatalogo> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(1);
            int random2 = (int) (Math.random() * pokemaos.size());
            PokemaoCatalogo pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        } else if (random <= 9) {
            ArrayList<PokemaoCatalogo> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(2);
            int random2 = (int) (Math.random() * pokemaos.size());
            PokemaoCatalogo pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        } else {
            ArrayList<PokemaoCatalogo> pokemaos = new PokemaoCatalogoRepository().listarPorRaridade(3);
            int random2 = (int) (Math.random() * pokemaos.size());
            PokemaoCatalogo pokemao = pokemaos.get(random2);
            novo = new PokemaoTreinador(pokemao, null);
        }
        return novo;
    }

    public boolean editar(PokemaoTreinador pokemaoTreinador) {
        boolean resultado = false;
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == pokemaoTreinador.getId()) {
                    obj.put("id_pokemao_catalogo", String.valueOf(pokemaoTreinador.getPokemao().getId()));
                    obj.put("id_treinador", String.valueOf(pokemaoTreinador.getTreinador().getId()));
                    obj.put("velocidade_ataque", String.valueOf(pokemaoTreinador.getVelocidadeAtaque()));
                    obj.put("ataque", String.valueOf(pokemaoTreinador.getAtaque()));
                    obj.put("defesa", String.valueOf(pokemaoTreinador.getDefesa()));
                    obj.put("hp", String.valueOf(pokemaoTreinador.getHp()));
                    obj.put("disponivel_para_troca", String.valueOf(pokemaoTreinador.isDisponivelParaTroca()));
                    obj.put("xp", String.valueOf(pokemaoTreinador.getXp()));
                    obj.put("data_captura", pokemaoTreinador.getDataCaptura().toString());
                    obj.put("nome_custom", pokemaoTreinador.getNome());
                    found = true;
                    break;
                }
            }
            if (found) JsonDB.write(FILE, list);
            resultado = found;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public PokemaoTreinador escolherPokemaoParaBatalha(PokemaoTreinador inicial) {
        ArrayList<PokemaoTreinador> pokemaos = this.listar();
        ArrayList<PokemaoTreinador> possiveisOponentes = new ArrayList<>();
        for (PokemaoTreinador pokemao : pokemaos) {
            if (pokemao.getTreinador() != null && pokemao.getTreinador().getId() != inicial.getTreinador().getId()) {
                possiveisOponentes.add(pokemao);
            }
        }
        Collections.shuffle(possiveisOponentes);
        if (!possiveisOponentes.isEmpty()) {
            for (PokemaoTreinador oponente : possiveisOponentes) {
                if (oponente.getPokemao().getRaridade() == inicial.getPokemao().getRaridade()) {
                    System.out.println("Oponente encontrado: " + oponente.getPokemao().getNome());
                    return oponente;
                }
            }
            System.out.println("Oponente encontrado: " + possiveisOponentes.get(0).getPokemao().getNome());
            return possiveisOponentes.get(0);
        }
        PokemaoTreinador pokemaoSelvagem = gerarPokemaoSelvagem();
        new PokemaoTreinadorRepository().cadastrar(pokemaoSelvagem);
        return pokemaoSelvagem;
    }
}