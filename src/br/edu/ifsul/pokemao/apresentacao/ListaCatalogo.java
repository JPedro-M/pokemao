package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Pokemao;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class ListaCatalogo extends JPanel {
    public ListaCatalogo(TreinadorRepository treinadorRepository, ArrayList<Pokemao> array, String contexto) {
        this.setLayout(null);
        int totalHeight = array.size() * 60;
        this.setPreferredSize(new Dimension(500, totalHeight));
        int y = 0;

        for (Pokemao pokemao : array) {
            // no panel, mostrar o emoji do pokemao, o nome, o ataque e a defesa
            // usar um panel clicável em vez de botão
            
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setBounds(0, y, 500, 50);
            panel.setBackground(pokemao.getRaridadeColor());
            this.add(panel);

            y += 60;

            JLabel emoji = new JLabel(pokemao.getEmoji());
            emoji.setBounds(10, 5, 50, 50);
            Font font = new Font("Segoe UI Emoji", Font.PLAIN, 30);
            emoji.setFont(font);
            panel.add(emoji);

            JLabel nome = new JLabel(pokemao.getNome());
            nome.setBounds(60, 10, 200, 30);
            panel.add(nome);

            JLabel ataque = new JLabel("ATK: " + pokemao.getAtaque());
            ataque.setBounds(250, 10, 100, 30);
            panel.add(ataque);

            JLabel defesa = new JLabel("DEF: " + pokemao.getDefesa());
            defesa.setBounds(350, 10, 100, 30);
            panel.add(defesa);

            this.setVisible(true);
        }
    }
}