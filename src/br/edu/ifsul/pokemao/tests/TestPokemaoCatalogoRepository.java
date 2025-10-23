package br.edu.ifsul.pokemao.tests;

import static br.edu.ifsul.pokemao.tests.Asserts.*;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;

public class TestPokemaoCatalogoRepository {
    public static void run() throws Exception {
        // Reset
        TestData.writePokemaosCatalogo(List.of());

        PokemaoCatalogoRepository repo = new PokemaoCatalogoRepository();
        assertEquals(0, repo.listar().size(), "empty list");

        PokemaoCatalogo p1 = new PokemaoCatalogo(0, "🐭", "Mouseling", 10, 5, 1, "", "");
        PokemaoCatalogo p2 = new PokemaoCatalogo(0, "🐢", "Shellby", 9, 12, 2, "", "");
        PokemaoCatalogo p3 = new PokemaoCatalogo(0, "🐉", "Dracoon", 22, 18, 3, "", "");
        assertTrue(repo.cadastrar(p1), "cadastrar p1");
        assertTrue(repo.cadastrar(p2), "cadastrar p2");
        assertTrue(repo.cadastrar(p3), "cadastrar p3");

        ArrayList<PokemaoCatalogo> lista = repo.listar();
        assertEquals(3, lista.size(), "three items");
        
        // Verificar ordenação por ID
        for (int i = 0; i < lista.size() - 1; i++) {
            assertTrue(lista.get(i).getId() <= lista.get(i + 1).getId(), 
                "lista ordenada por ID: " + lista.get(i).getId() + " <= " + lista.get(i + 1).getId());
        }

        long id2 = lista.stream().filter(p -> p.getNome().equals("Shellby")).findFirst().get().getId();
        PokemaoCatalogo got2 = repo.buscarPorId(id2);
        assertEquals("Shellby", got2.getNome(), "buscarPorId Shellby");

        p2.setDescricao("Tartaruga brava");
        p2.setId(id2);
        assertTrue(repo.editar(p2), "editar returns true");
        assertEquals("Tartaruga brava", repo.buscarPorId(id2).getDescricao(), "descricao editada");

        ArrayList<PokemaoCatalogo> rar2 = repo.listarPorRaridade(2);
        assertEquals(1, rar2.size(), "raridade 2 count");

        assertTrue(repo.deletar((int) id2), "delete returns true");
        assertEquals(2, repo.listar().size(), "after delete size");
    }
}
