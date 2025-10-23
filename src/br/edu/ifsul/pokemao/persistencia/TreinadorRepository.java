package br.edu.ifsul.pokemao.persistencia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.utils.JsonDB;

/**
 * Classe para gerenciar os treinadores (usuários) do sistema.
 * <p>
 * Esta classe fornece métodos para realizar login, cadastro e logout de um
 * treinador, além de fornecer métodos para buscar treinadores no banco de dados
 * e atualizar e remover treinadores do banco de dados.
 * 
 * @see Treinador
 */
public class TreinadorRepository {

    /**
     * Representa o treinador atualmente logado no sistema.
     */
    Treinador treinadorLogado;

    private final String FILE = "treinadores.json";

    public TreinadorRepository() {
    }

    public Treinador getTreinadorLogado() {
        return treinadorLogado;
    }

    public void setTreinadorLogado(Treinador treinadorLogado) {
        this.treinadorLogado = treinadorLogado;
    }

    public int getLenTreinadores() {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Treinador buscarPorUser(String user) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                if (user.equals(obj.get("usuario"))) {
                    Treinador t = new Treinador(obj.get("usuario"), obj.get("senha"), obj.get("nome"),
                            LocalDateTime.parse(obj.get("nascimento")));
                    t.setId(Long.parseLong(obj.get("id")));
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Treinador buscarPorID(long id) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == id) {
                    Treinador t = new Treinador(obj.get("usuario"), obj.get("senha"), obj.get("nome"),
                            LocalDateTime.parse(obj.get("nascimento")));
                    t.setId(Long.parseLong(obj.get("id")));
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(String user, String senha) {
        Treinador treinador = buscarPorUser(user);
        if (treinador != null) {
            if (treinador.getSenha().equals(senha)) {
                this.treinadorLogado = treinador;
                return true;
            }
        }
        return false;
    }

    public long cadastrar(Treinador treinador) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            long nextId = 1;
            for (Map<String, String> obj : list) {
                long oid = Long.parseLong(obj.get("id"));
                if (oid >= nextId) nextId = oid + 1;
            }
            treinador.setId(nextId);
            Map<String, String> obj = new java.util.HashMap<>();
            obj.put("id", String.valueOf(nextId));
            obj.put("usuario", treinador.getUser());
            obj.put("senha", treinador.getSenha());
            obj.put("nome", treinador.getNome());
            obj.put("nascimento", treinador.getNascimento().toString());
            list.add(obj);
            JsonDB.write(FILE, list);
            return nextId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void logout() {
        this.treinadorLogado = null;
    }

    public boolean editar(Treinador treinador) {
        try {
            List<Map<String, String>> list = JsonDB.read(FILE);
            boolean found = false;
            for (Map<String, String> obj : list) {
                if (Long.parseLong(obj.get("id")) == treinador.getId()) {
                    obj.put("usuario", treinador.getUser());
                    obj.put("senha", treinador.getSenha());
                    obj.put("nome", treinador.getNome());
                    obj.put("nascimento", treinador.getNascimento().toString());
                    found = true;
                    break;
                }
            }
            if (found) {
                JsonDB.write(FILE, list);
            }
            return found;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remover(long id) {
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
}
