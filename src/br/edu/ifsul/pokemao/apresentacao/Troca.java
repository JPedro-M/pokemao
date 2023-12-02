package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

public class Troca extends JFrame {
    Troca(){
        this.setTitle("Troca");
        this.setSize(600, 500);

        JLabel pokeTT = new JLabel("Gostosão");
            pokeTT.setBounds(50, 100, 200, 50);

        JLabel pokeTTImg = new JLabel("B-)");
            pokeTTImg.setBounds(50, 200, 200, 200);

        JButton trocador = new JButton("Trocar");
            trocador.setBounds(225, 200, 100, 50);

        JLabel pokeNT = new JLabel("Sério Luís");
            pokeNT.setBounds(450, 100, 200, 50);

        JLabel pokeNTImg = new JLabel("[:-|]");
            pokeNTImg.setBounds(450, 200, 200, 200);

        this.add(pokeTT); this.add(pokeTTImg); this.add(trocador); this.add(pokeNT); this.add(pokeNTImg);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);


    }
}
