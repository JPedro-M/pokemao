package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Troca;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class HistTrocas extends JFrame {
    public HistTrocas(TreinadorRepository treinadorRepository) {
        // configurações da janela
        this.setTitle("Histórico de Trocas");
        this.setBounds(200, 50, 600, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // coletando as trocas do sistema
        ArrayList<Troca> trocas = new AcontecimentoRepository().listarTrocas();

        // elementos da janela
        ListaTrocas listaTrocas = new ListaTrocas(treinadorRepository, trocas);
        JScrollPane scrollPane = new JScrollPane(listaTrocas);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);

        JLabel label = new JLabel("Histórico de Trocas");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Essas foram as últimas trocas realizadas em todo o sistema.");
        label2.setBounds(120, 40, 400, 30);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);

        // adicionando elementos à janela
        this.add(scrollPane);
        this.add(label);
        this.add(label2);
        this.add(voltar);

        // ações dos botões
        voltar.addActionListener(e -> {
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }

    private class ListaTrocas extends JPanel {
        public ListaTrocas(TreinadorRepository treinadorRepository, ArrayList<Troca> array) {
            // configurações do painel
            this.setLayout(null);
            int totalHeight = array.size() * 90;
            this.setPreferredSize(new Dimension(500, totalHeight));

            // elementos do painel
            int y = 0;
            for (Troca troca : array) {
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panel.setBounds(0, y, 500, 80);
                // background: verde se o treinador logado participou da troca, cinza se não
                if (troca.getTreinadorInicial().getId() == treinadorRepository.getTreinadorLogado().getId()
                        || troca.getTreinadorEscolhido().getId() == treinadorRepository.getTreinadorLogado().getId()) {
                    panel.setBackground(new Color(0, 255, 0, 30));
                } else {
                    panel.setBackground(new Color(0, 0, 0, 30));
                }

                this.add(panel);

                y += 90;

                JLabel label = new JLabel(troca.getTreinadorInicial().getNome() + " trocou "
                        + troca.getPokemaoOfertado().getNome() + " por " + troca.getPokemaoResposta().getNome()
                        + " com " + troca.getTreinadorEscolhido().getNome());
                label.setBounds(0, 10, 500, 40);
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.add(label);
                JLabel label2 = new JLabel("em " + troca.getData().toString());
                label2.setBounds(0, 30, 500, 40);
                label2.setHorizontalAlignment(JLabel.CENTER);
                panel.add(label2);

                // tornando o painel visível
                this.setVisible(true);
            }

            // tornando a janela visível
            this.setVisible(true);
        }
    }
}
