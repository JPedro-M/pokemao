package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TelaBatalha extends JFrame {

    PokemaoTreinador vencedor = null;
    Batalha batalha;

    public TelaBatalha(TreinadorRepository treinadorRepository, PokemaoTreinador inicial){
        this.setTitle("Batalha");
        this.setSize(600, 500);

        PokemaoTreinador oponente = new PokemaoTreinadorRepository().escolherPokemaoParaBatalha(inicial);

        batalha = new Batalha(inicial, oponente);

        JLabel hpPokeT = new JLabel("HP: "+oponente.getHp());
        hpPokeT.setBounds(105, 75, 200, 50);

        JLabel pokeT = new JLabel();
        pokeT.setBounds( 105, 130, 200, 200);

        JLabel pokeTN = new JLabel("pokemão");
        pokeTN.setBounds(105, 240, 200, 50);

        JButton atk = new JButton("Atacar");
        atk.setBounds(30 , 300 , 150, 45);

        JButton def =  new JButton("Defender");
        def.setBounds(190, 300, 150, 45);

        JLabel hpPokeI = new JLabel("HP: "+oponente.getHp());
        hpPokeI.setBounds(450, 25, 200, 50);

        JLabel pokeI = new JLabel();
        pokeI.setBounds(450, 85, 200, 200);

        JLabel pokeIN = new JLabel("inimigo");
        pokeIN.setBounds(450,195, 200, 50);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(500, 400, 100, 40);

        voltar.addActionListener(e ->{
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });  
        
        atk.addActionListener(e ->{
            oponente.setHp(oponente.getDefesa()-inicial.getAtaque());  
            checkBattleResult(inicial, oponente);
        });

        def.addActionListener(e ->{
            inicial.setHp((inicial.getDefesa()*2)-oponente.getAtaque());
            checkBattleResult(inicial, oponente);
        });

        this.add(hpPokeT); this.add(pokeT); this.add(pokeTN); this.add(atk);
        this.add(def); this.add(hpPokeI); this.add(pokeI); this.add(pokeIN);
        this.add(voltar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
    }

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
