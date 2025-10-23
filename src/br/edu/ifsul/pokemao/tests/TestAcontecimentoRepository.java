package br.edu.ifsul.pokemao.tests;

import static br.edu.ifsul.pokemao.tests.Asserts.*;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;
import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.model.Treinador;

public class TestAcontecimentoRepository {
    public static void run() throws Exception {
        // Reset base minimal data
        TestData.writePokemaosCatalogo(List.of(
            TestData.map("id","1","emoji","🐭","nome","Mouseling","ataque","10","defesa","5","raridade","1","foto","","descricao","")
        ));
        TestData.writeTreinadores(List.of(
            TestData.map("id","1","usuario","t1","senha","s1","nome","Treinador 1","nascimento", LocalDateTime.parse("2000-01-01T00:00:00").toString()),
            TestData.map("id","2","usuario","t2","senha","s2","nome","Treinador 2","nascimento", LocalDateTime.parse("2000-01-01T00:00:00").toString())
        ));
        TestData.writePokemaosTreinadores(List.of());
        TestData.writeTrocas(List.of());
        TestData.writeBatalhas(List.of());

        PokemaoCatalogoRepository catRepo = new PokemaoCatalogoRepository();
        TreinadorRepository tRepo = new TreinadorRepository();
        PokemaoTreinadorRepository pRepo = new PokemaoTreinadorRepository();
        AcontecimentoRepository aRepo = new AcontecimentoRepository();

        PokemaoCatalogo cat = catRepo.buscarPorId(1);
        Treinador t1 = tRepo.buscarPorID(1);
        Treinador t2 = tRepo.buscarPorID(2);

        PokemaoTreinador p1 = new PokemaoTreinador(cat, t1);
        PokemaoTreinador p2 = new PokemaoTreinador(cat, t2);
        pRepo.cadastrar(p1); pRepo.cadastrar(p2);

        // Troca
        Troca tr = new Troca(p1, p2);
        tr.setData(LocalDateTime.parse("2025-01-01T10:00:00"));
        tr.setTreinadorInicial(t1); tr.setTreinadorEscolhido(t2);
        aRepo.adicionarTroca(tr);
        assertTrue(aRepo.listarTrocas().size() >= 1, "listarTrocas >= 1");
        assertTrue(aRepo.listarTrocasPorTreinador(1).size() >= 1, "trocas por t1 >= 1");

        // Batalha
        Batalha b = new Batalha(p1, p2);
        b.setData(LocalDateTime.parse("2025-01-01T12:00:00"));
        b.setVencedor(true); // p1 vencedor
        b.setTreinadorInicial(t1); b.setTreinadorEscolhido(t2);
        aRepo.adicionarBatalha(b);
        assertTrue(aRepo.listarBatalhas().size() >= 1, "listarBatalhas >= 1");
        assertTrue(aRepo.listarBatalhasPorTreinador(2).size() >= 1, "batalhas por t2 >= 1");
    }
}
