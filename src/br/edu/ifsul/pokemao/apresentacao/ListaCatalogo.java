package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class ListaCatalogo extends JPanel {
    /**
     * Classe responsável por exibir uma lista de PokemaoCatalogo na interface
     * gráfica.
     * <p>
     * A lista é exibida em um JPanel, que é adicionado a um JScrollPane.
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     * @param array               A lista de Pokemãos a ser exibida.
     * @param contexto            O contexto em que a lista será exibida.
     */
    public ListaCatalogo(TreinadorRepository treinadorRepository, ArrayList<PokemaoCatalogo> array, String contexto) {
        // configurações do painel
        this.setLayout(null);
        int totalHeight = array.size() * 60;
        this.setPreferredSize(new Dimension(500, totalHeight));
        int y = 0;

        // para cada pokemao na lista, criar um painel menor com as informações dele
        for (PokemaoCatalogo pokemao : array) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setBounds(0, y, 500, 100);
            panel.setBackground(pokemao.getRaridadeColor());
            this.add(panel);

            y += 110;

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

            JLabel desc = new JLabel("<html>" + pokemao.getDescricao() + "</html>");
            desc.setBounds(60, 30, 400, 30);
            panel.add(desc);

            // tornando o painel visível
            this.setVisible(true);
        }
    }
}