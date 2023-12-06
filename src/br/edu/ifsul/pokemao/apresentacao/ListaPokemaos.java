package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class ListaPokemaos extends JPanel {
    ListaPokemaos(TreinadorRepository treinadorRepository, ArrayList<PokemaoTreinador> array, String contexto) {
        this.setLayout(null);
        int totalHeight = array.size() * 60;
        this.setPreferredSize(new Dimension(500, totalHeight));
        int y = 0;

        for (PokemaoTreinador pokemao : array) {
            // no panel, mostrar o emoji do pokemao, o nome e o HP
            // usar um panel clicável em vez de botão

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setBounds(0, y, 400, 50);
            panel.setBackground(pokemao.getPokemao().getRaridadeColor());
            this.add(panel);

            y += 60;

            JLabel emoji = new JLabel(pokemao.getPokemao().getEmoji());
            emoji.setBounds(10, 5, 50, 50);
            Font font = new Font("Segoe UI Emoji", Font.PLAIN, 30);
            emoji.setFont(font);
            panel.add(emoji);

            JLabel nome = new JLabel(pokemao.getNome());
            nome.setBounds(60, 10, 200, 30);
            panel.add(nome);

            JLabel hp = new JLabel("HP: " + pokemao.getHp());
            hp.setBounds(250, 10, 100, 30);
            panel.add(hp);

            JLabel dispTroca = new JLabel();
            if (pokemao.isDisponivelParaTroca()) {
                dispTroca.setText("Disp.: Sim");
            } else {
                dispTroca.setText("Disp.: Não");
            }

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (contexto) {
                        case "troca":
                            new EscolherTroca(treinadorRepository, pokemao);
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            break;
                        case "batalha":
                            new Batalha(treinadorRepository, pokemao);
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            break;
                        case "telatreinador":
                            Object[] options = { "Curar", "Disponível para troca", "Libertar", "Alterar nome" };
                            int n = JOptionPane.showOptionDialog(null,
                                    "O que deseja fazer com " + pokemao.getNome() + "?",
                                    "Opções",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[3]);
                            switch (n) {
                                case 0:
                                    new PokemaoTreinadorRepository().curar(pokemao);
                                    JOptionPane.showMessageDialog(null, pokemao.getNome() + " foi curado!");
                                    break;
                                case 1:
                                    if (pokemao.isDisponivelParaTroca()) {
                                        pokemao.setDisponivelParaTroca(false);
                                        JOptionPane.showMessageDialog(null,
                                                pokemao.getNome() + " não está mais disponível para troca!");
                                    } else {
                                        pokemao.setDisponivelParaTroca(true);
                                        JOptionPane.showMessageDialog(null,
                                                pokemao.getNome() + " está disponível para troca!");
                                    }
                                    break;
                                case 2:
                                    new PokemaoTreinadorRepository().libertar(pokemao);
                                    JOptionPane.showMessageDialog(null, pokemao.getNome() + " foi libertado!");
                                    break;
                                case 3:
                                    String novoNome = JOptionPane.showInputDialog(null, "Novo nome:");
                                    if (novoNome != null) {
                                        pokemao.setNome(novoNome);
                                        new PokemaoTreinadorRepository().editar(pokemao);
                                        JOptionPane.showMessageDialog(null, "Nome alterado com sucesso!");
                                    }
                                    break;
                                default:
                                    break;
                            }
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            new MeusPokemaos(treinadorRepository);
                            break;
                        default:
                            break;
                    }
                }

            });

            this.add(panel);
        }

        this.setVisible(true);
    }
}
