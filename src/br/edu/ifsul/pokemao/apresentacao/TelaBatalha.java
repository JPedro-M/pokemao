package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TelaBatalha extends JFrame {
    Batalha batalha;

    /**
     * A Batalha ocorre aqui. O usuário comandará seu pokemao em batalha contra
     * outro pokemao que pertencerá ou a um outro usuário ou será um pokemao gerado
     * aleatoriamente.
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public TelaBatalha(TreinadorRepository treinadorRepository, PokemaoTreinador inicial) {
        // configurações da janela
        this.setTitle("Batalha");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // escolha ou geração do pokemao oponente
        PokemaoTreinador oponente = new PokemaoTreinadorRepository().escolherPokemaoParaBatalha(inicial);

        // criação da batalha
        batalha = new Batalha(inicial, oponente);

        // elementos da janela
        JPanel panelPokemaoInicial = new JPanel();

        JLabel hpPokeT = new JLabel("HP: " + oponente.getHp());
        hpPokeT.setBounds(105, 75, 200, 50);

        JLabel pokeT = new JLabel();
        pokeT.setBounds(105, 130, 200, 200);

        JLabel pokeTN = new JLabel("pokemão");
        pokeTN.setBounds(105, 240, 200, 50);

        panelPokemaoInicial.add(hpPokeT);
        panelPokemaoInicial.add(pokeT);
        panelPokemaoInicial.add(pokeTN);

        JPanel panelPokemaoOponente = new JPanel();

        JLabel hpPokeI = new JLabel("HP: " + oponente.getHp());
        hpPokeI.setBounds(450, 25, 200, 50);

        JLabel pokeI = new JLabel();
        pokeI.setBounds(450, 85, 200, 200);

        JLabel pokeIN = new JLabel("inimigo");
        pokeIN.setBounds(450, 195, 200, 50);

        panelPokemaoOponente.add(hpPokeI);
        panelPokemaoOponente.add(pokeI);
        panelPokemaoOponente.add(pokeIN);

        JButton atk = new JButton("Atacar");
        atk.setBounds(30, 300, 150, 45);

        JButton def = new JButton("Defender");
        def.setBounds(190, 300, 150, 45);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(500, 400, 100, 40);

        // adicionando elementos à janela
        this.add(atk);
        this.add(def);
        this.add(panelPokemaoInicial);
        this.add(panelPokemaoOponente);
        this.add(voltar);

        // ações dos botões
        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });

        atk.addActionListener(e -> {
            oponente.setHp(oponente.getDefesa() - inicial.getAtaque());
            checkBattleResult(inicial, oponente);
        });

        def.addActionListener(e -> {
            inicial.setHp((inicial.getDefesa() * 2) - oponente.getAtaque());
            checkBattleResult(inicial, oponente);
        });

        // tornando a janela visível
        this.setVisible(true);
    }

    /**
     * Verifica o resultado da batalha entre dois pokemãos.
     * <p>
     * Se o oponente tiver pontos de vida (HP) igual ou menor que zero, exibe uma
     * mensagem de vitória, define o HP do oponente como zero, marca a batalha como
     * vencida e adiciona a batalha ao repositório.
     * Caso contrário, se o pokemao inicial tiver HP igual ou menor que zero, exibe
     * uma mensagem de derrota.
     *
     * @param inicial  o PokemaoTreinador inicial
     * @param oponente o PokemaoTreinador oponente
     */
    private void checkBattleResult(PokemaoTreinador inicial, PokemaoTreinador oponente) {
        if (oponente.getHp() <= 0) {
            JOptionPane.showMessageDialog(null, "Ganhou!", "Parabéns!", JOptionPane.WARNING_MESSAGE);
            oponente.setHp(0);
            batalha.setVencedor(true);
            new AcontecimentoRepository().adicionarBatalha(batalha);
        } else if (inicial.getHp() <= 0) {
            JOptionPane.showMessageDialog(null, "Perdeu!", "Cure seu Pokemão", JOptionPane.WARNING_MESSAGE);
        }
    }
}
