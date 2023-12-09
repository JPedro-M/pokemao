package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Pokemao;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;
/*
 * Aqui os treinadores/ usuários podem ver a lista completa de todos os
 * pokemaos existentes.
 * 
 * 
 */
public class Pokemaodex extends JFrame {
    public Pokemaodex(TreinadorRepository treinadorRepository) {
        this.setTitle("Pokémãodex");
        this.setBounds(200, 50, 600, 700);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        ArrayList<Pokemao> pokemaos = new PokemaoCatalogoRepository().listar();

        JPanel panel = new ListaCatalogo(treinadorRepository, pokemaos, "pokedex");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);
        add(scrollPane);

        JLabel label = new JLabel("Pokémãodex");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Esses são todos os pokemãos que existem.");
        label2.setBounds(120, 40, 400, 30);
        add(label); add(label2);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        add(voltar);

        this.setVisible(true);

        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });
    }
    
}
