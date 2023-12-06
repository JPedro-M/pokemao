package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;

import javax.swing.*;

import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoLobby extends JFrame {
    PokemaoLobby(TreinadorRepository treinadorRepository){
        this.setTitle("Lobby");
        this.setBounds(200, 75, 600, 500);     

        JLabel msgTela = new JLabel("Bem vindo, " + treinadorRepository.getTreinadorLogado().getNome() + "!");
            msgTela.setBounds(0, 10, this.getWidth(), 50);
            msgTela.setHorizontalAlignment(JLabel.CENTER);
            Font currentFont = msgTela.getFont();
            Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
            msgTela.setFont(newFont);

        JButton batalha = new JButton("Batalhar");
            batalha.setBounds(125, 75, 150, 75 );

        JButton troca = new JButton("Trocar");
            troca.setBounds(350, 75, 150, 75);

        JButton pegar = new JButton("Pegar");
            pegar.setBounds(125, 175, 150, 75);

        JButton dex = new JButton("Enciclopédia");
            dex.setBounds(350, 175, 150, 75);

        JButton meuspokemao = new JButton("Meus Pokémãos");
            meuspokemao.setBounds(225, 275, 150, 75);

            JButton sair = new JButton("Sair");
            sair.setBounds(10, this.getHeight() - 75, 90, 30);
        this.add(msgTela); this.add(sair);
        this.add(batalha); this.add(troca); this.add(pegar); this.add(dex); this.add(meuspokemao);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        troca.addActionListener(e -> {
            new IniciarTroca(treinadorRepository);
            this.dispose();
        });
        
        pegar.addActionListener(e -> {
            new Natureza(treinadorRepository);
            this.dispose();
        });

        meuspokemao.addActionListener(e -> {
            new MeusPokemaos(treinadorRepository);
            this.dispose();
        });

        batalha.addActionListener(e -> {
            new IniciarBatalha(treinadorRepository);
            this.dispose();
        });

        dex.addActionListener(e -> {
            new Pokemaodex(treinadorRepository);
            this.dispose();
        });       
        
        sair.addActionListener(e -> {
            new PokemaoLogin();
            treinadorRepository.setTreinadorLogado(null);
            this.dispose();
        });

    }
}
