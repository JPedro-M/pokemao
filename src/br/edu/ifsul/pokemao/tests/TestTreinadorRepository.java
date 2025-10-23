package br.edu.ifsul.pokemao.tests;

import static br.edu.ifsul.pokemao.tests.Asserts.*;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TestTreinadorRepository {
    public static void run() throws Exception {
        // Prepare clean dataset
        TestData.writeTreinadores(List.of());

        TreinadorRepository repo = new TreinadorRepository();
        assertEquals(0, repo.getLenTreinadores(), "len should be 0");

        Treinador t1 = new Treinador("alice","123","Alice", LocalDateTime.parse("2000-01-01T00:00:00"));
        long id1 = repo.cadastrar(t1);
        assertTrue(id1 > 0, "cadastrar should return id > 0");

        Treinador got = repo.buscarPorID(id1);
        assertEquals("alice", got.getUser(), "buscarPorID user");

        Treinador byUser = repo.buscarPorUser("alice");
        assertEquals(id1, byUser.getId(), "buscarPorUser id matches");

        assertTrue(repo.login("alice", "123"), "login success");
        assertEquals(id1, repo.getTreinadorLogado().getId(), "logged in id");
        repo.logout();
        assertEquals(null, repo.getTreinadorLogado(), "logout clears session");

        t1.setNome("Alice B.");
        assertTrue(repo.editar(t1), "editar returns true");
        assertEquals("Alice B.", repo.buscarPorID(id1).getNome(), "edited name");

        assertEquals(1, repo.getLenTreinadores(), "len should be 1");
        assertTrue(repo.remover(id1), "remover returns true");
        assertEquals(0, repo.getLenTreinadores(), "len should be 0 after remove");
    }
}
