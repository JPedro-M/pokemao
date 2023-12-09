package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.util.ArrayList;
/*
 * Aqui o usuário escolherá qual pokemao ele quer em troca do seu que foi enviado;
 * 
 * 
 */
public class EscolherTroca extends JFrame {
    PokemaoTreinadorRepository pokemaoTreinadorRepository = new PokemaoTreinadorRepository();
    ArrayList<PokemaoTreinador> pokemaos = null;

    public EscolherTroca(TreinadorRepository treinadorRepository, PokemaoTreinador pokemaoOrigem) {
        pokemaos = pokemaoTreinadorRepository.listarDisponiveisParaTroca(treinadorRepository.getTreinadorLogado());

        setTitle("Troca");
        setBounds(200, 75, 800, 600);       

        setLayout(null);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField escolha = new JTextField();
        escolha.setBounds(350, 250, 100, 20);
        add(escolha);

        JButton confirmar = new JButton("Escolher");
        confirmar.setBounds(350, 300, 100, 50);
        add(confirmar);

        JPanel panel = new CircleTextPanel();
        panel.setBounds(100, 1, 600, 550);
        add(panel);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        add(voltar);

        setVisible(true);

        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });

        confirmar.addActionListener(e -> {
            int index = Integer.parseInt(escolha.getText()) - 1;
            if (index >= 0 && index < pokemaos.size()) {
                PokemaoTreinador pokemaoDestino = pokemaos.get(index);
                try {
                    pokemaoTreinadorRepository.trocar(pokemaoOrigem, pokemaoDestino);
                    JOptionPane.showMessageDialog(null, "Troca realizada com sucesso!");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao realizar troca!");
                }
                new PokemaoLobby(treinadorRepository);
                this.dispose();
            }
        });
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
                Font newFont = new Font("Calibri", Font.PLAIN, 14);
                g.setFont(newFont);
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(pokemaos.get(i).getNome());
                int textHeight = fm.getHeight();
                g.drawString(pokemaos.get(i).getNome(), x - textWidth / 2, y + textHeight / 2);
                // mostrar o valor de i logo acima do nome
                // mostrar o nome do treinador logo abaixo do nome do pokemao

                g.drawString(pokemaos.get(i).getTreinador().getNome(), x - textWidth / 2,
                    y + textHeight / 2 + textHeight);
                newFont = g.getFont().deriveFont(Font.BOLD, 18f);
                g.setFont(newFont);
                g.drawString(String.valueOf(i + 1), x - textWidth / 2, y + textHeight / 2 - 5 - textHeight);
                newFont = new Font("Segoe UI Emoji", Font.PLAIN, 30);
                g.setFont(newFont);
                g.drawString(pokemaos.get(i).getPokemao().getEmoji(), x - textWidth / 2 + 10, y + textHeight / 2 - 7 - textHeight);
            }
        }
    }
}