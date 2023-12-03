package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.util.ArrayList;

public class EscolherTroca extends JFrame {
    PokemaoTreinadorRepository pokemaoTreinadorRepository = new PokemaoTreinadorRepository();
    ArrayList<PokemaoTreinador> pokemaos = null;

    public EscolherTroca(TreinadorRepository treinadorRepository, PokemaoTreinador pokemaoOrigem) {
        pokemaos = pokemaoTreinadorRepository.listarDisponiveisParaTroca(null);

        setTitle("Troca");
        setBounds(100, 100, 800, 600);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CircleTextPanel());

        setVisible(true);

        
    }

    /**
     * Um painel personalizado JPanel que exibe um círculo de texto representando
     * pokemaos. Cada texto é posicionado ao longo da circunferência do círculo.
     */
    private class CircleTextPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(centerX, centerY) - 60;
            int size = pokemaos.size();

            for (int i = 0; i < size; i++) {
                double angle = 2 * Math.PI * i / size;
                int x = centerX + (int) (radius * Math.cos(angle));
                int y = centerY + (int) (radius * Math.sin(angle));
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(pokemaos.get(i).getNome());
                int textHeight = fm.getHeight();
                g.drawString(pokemaos.get(i).getNome(), x - textWidth / 2, y + textHeight / 2);
                // mostrar o valor de i logo acima do nome
                // mostrar o nome do treinador logo abaixo do nome do pokemao

                g.drawString(String.valueOf(i + 1), x - textWidth / 2, y + textHeight / 2 - textHeight);
                g.drawString(pokemaos.get(i).getTreinador().getNome(), x - textWidth / 2,
                        y + textHeight / 2 + textHeight);
            }
        }
    }
}