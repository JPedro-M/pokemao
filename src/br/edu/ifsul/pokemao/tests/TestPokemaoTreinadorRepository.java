package br.edu.ifsul.pokemao.tests;

import static br.edu.ifsul.pokemao.tests.Asserts.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TestPokemaoTreinadorRepository {
    public static void run() throws Exception {
        // Seed required catalog and trainers
        TestData.writePokemaosCatalogo(List.of(
            TestData.map("id","1","emoji","🐭","nome","Mouseling","ataque","10","defesa","5","raridade","1","foto","","descricao","")
        ));
        TestData.writeTreinadores(List.of(
            TestData.map("id","1","usuario","t1","senha","s1","nome","Treinador 1","nascimento", LocalDateTime.parse("2000-01-01T00:00:00").toString()),
            TestData.map("id","2","usuario","t2","senha","s2","nome","Treinador 2","nascimento", LocalDateTime.parse("2000-01-01T00:00:00").toString())
        ));
        TestData.writePokemaosTreinadores(List.of());

        PokemaoCatalogoRepository catRepo = new PokemaoCatalogoRepository();
        TreinadorRepository tRepo = new TreinadorRepository();
        PokemaoTreinadorRepository repo = new PokemaoTreinadorRepository();

        PokemaoCatalogo cat = catRepo.buscarPorId(1);
        Treinador t1 = tRepo.buscarPorID(1);
        Treinador t2 = tRepo.buscarPorID(2);

        PokemaoTreinador p1 = new PokemaoTreinador(cat, t1);
        assertTrue(repo.cadastrar(p1), "cadastrar p1");
        PokemaoTreinador fetched = repo.buscarPorId(p1.getId());
        assertEquals(p1.getId(), fetched.getId(), "buscarPorId matches");

        // editar
        p1.setNome("Mouseling+");
        p1.setHp(80);
        assertTrue(repo.editar(p1), "editar returns true");
        assertEquals("Mouseling+", repo.buscarPorId(p1.getId()).getNome(), "nome editado");

        // listarDoTreinador
        assertEquals(1, repo.listarDoTreinador(t1).size(), "listarDoTreinador t1 size");

        // disponivel para troca
        p1.setDisponivelParaTroca(true);
        assertTrue(repo.editar(p1), "editar disponivel");
        assertEquals(1, repo.listarDisponiveisParaTroca(null).size(), "um disponivel");

        // curar
        assertTrue(repo.curar(p1), "curar true");
        assertEquals(100, repo.buscarPorId(p1.getId()).getHp(), "hp 100");

        // libertar
        assertTrue(repo.libertar(p1), "libertar true");
        assertEquals(null, repo.buscarPorId(p1.getId()).getTreinador(), "sem treinador");

        // transferir (protected) via reflection para t2
        Method m = PokemaoTreinadorRepository.class.getDeclaredMethod("transferir", PokemaoTreinador.class, Treinador.class);
        m.setAccessible(true);
        boolean trOk = (boolean) m.invoke(repo, p1, t2);
        assertTrue(trOk, "transferir via reflection ok");
        assertEquals(2L, repo.buscarPorId(p1.getId()).getTreinador().getId(), "treinador agora t2");

        // escolherPokemaoParaBatalha: adicionar outro pokemao pro t1 como oponente
        PokemaoTreinador p2 = new PokemaoTreinador(cat, t1);
        assertTrue(repo.cadastrar(p2), "cadastrar p2");
        PokemaoTreinador oponente = repo.escolherPokemaoParaBatalha(p2);
        assertTrue(oponente != null, "oponente nao nulo");
    }
}
