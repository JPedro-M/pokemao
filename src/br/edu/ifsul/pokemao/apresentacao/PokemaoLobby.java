package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoLobby extends JFrame {
    PokemaoLobby(TreinadorRepository treinadorRepository){
        this.setTitle("Lobby");
        this.setSize(600, 500);

        JLabel msgTela = new JLabel("Bem vindo, " + treinadorRepository.getTreinadorLogado().getNome() + "!");
            msgTela.setBounds(180, 10, 300, 50);

        JButton batalha = new JButton("Batalhar");
            batalha.setBounds(125, 75, 150, 75 );

        JButton troca = new JButton("Trocar");
            troca.setBounds(350, 75, 150, 75);

        JButton pegar = new JButton("Pegar");
            pegar.setBounds(125, 175, 150, 75);

        JButton dex = new JButton("Enciclopédia");
            dex.setBounds(350, 175, 150, 75);

        JButton cura = new JButton("Curar");
            cura.setBounds(225, 275, 150, 75);

        this.add(msgTela);
        this.add(batalha); this.add(troca); this.add(pegar); this.add(dex); this.add(cura);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        pegar.addActionListener(e -> {
            new Natureza();
            this.dispose();
        });

    }
}
