package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

public class Batalha extends JFrame {

    Batalha(){
        this.setTitle("Batalha");
        this.setSize(600, 500);

        JLabel hpPokeT = new JLabel("HP: ");
            hpPokeT.setBounds(105, 75, 200, 50);

        JLabel pokeT = new JLabel();
            pokeT.setBounds( 105, 130, 200, 200);

        JLabel pokeTN = new JLabel("pokemão");
            pokeTN.setBounds(105, 240, 200, 50);

        JButton atk = new JButton("Atacar");
            atk.setBounds(30 , 300 , 150, 45);

        JButton def =  new JButton("Defender");
            def.setBounds(190, 300, 150, 45);

        JLabel hpPokeI = new JLabel("HP: ");
            hpPokeI.setBounds(450, 25, 200, 50);

        JLabel pokeI = new JLabel();
            pokeI.setBounds(450, 85, 200, 200);

        JLabel pokeIN = new JLabel("inimigo");
            pokeIN.setBounds(450,195, 200, 50);

        JButton voltar = new JButton();
            voltar.setBounds(500, 400, 100, 40);

        this.add(hpPokeT); this.add(pokeT); this.add(pokeTN); this.add(atk);
        this.add(def); this.add(hpPokeI); this.add(pokeI); this.add(pokeIN);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

    }
}
