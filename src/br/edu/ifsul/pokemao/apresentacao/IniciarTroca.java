package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class IniciarTroca extends JFrame {
    public IniciarTroca(TreinadorRepository treinadorRepository) {
        // escolher pokemao entre lista de pokemaos do treinador logado
        setTitle("Troca");
        setBounds(100, 100, 800, 600);       

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository().listarDoTreinador(treinadorRepository.getTreinadorLogado());

        JLabel label = new JLabel("Escolha um pokemão para trocar");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(10, 10, 400, 50);
        add(label);

        // lista simples de pokemaos em linha, onde cada um é um botão
        // ao clicar no botão, abre a tela de escolher pokemao para troca
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        add(panel);

        for (PokemaoTreinador pokemao : pokemaos) {
            JButton button = new JButton(pokemao.getNome());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new EscolherTroca(treinadorRepository, pokemao);
                    dispose();
                }
            });
            panel.add(button);
        }

        setVisible(true);
    }
}
