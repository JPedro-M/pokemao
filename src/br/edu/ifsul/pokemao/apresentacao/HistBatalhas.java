package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class HistBatalhas extends JFrame {
    public HistBatalhas(TreinadorRepository treinadorRepository) {
        // configurações da janela
        this.setTitle("Histórico de Batalhas");
        this.setBounds(200, 50, 600, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // coletando as batalhas do sistema
        ArrayList<Batalha> batalhas = new AcontecimentoRepository().listarBatalhas();

        // elementos da janela
        ListaBatalhas listaBatalhas = new ListaBatalhas(treinadorRepository, batalhas);
        JScrollPane scrollPane = new JScrollPane(listaBatalhas);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);

        JLabel label = new JLabel("Histórico de Batalhas");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Essas foram as últimas batalhas realizadas em todo o sistema.");
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

    private class ListaBatalhas extends JPanel {
        public ListaBatalhas(TreinadorRepository treinadorRepository, ArrayList<Batalha> array) {
            // configurações do painel
            this.setLayout(null);
            int totalHeight = array.size() * 90;
            this.setPreferredSize(new Dimension(500, totalHeight));

            // elementos do painel
            int y = 0;
            for (Batalha batalha : array) {
                JPanel panel = new JPanel();
                panel.setBounds(0, y, 500, 80);
                panel.setLayout(null);
                panel.setBackground(new Color(0, 0, 0, 20));

                y += 90;

                JLabel label = new JLabel("Batalha entre " + batalha.getTreinadorInicial().getNome() + " e "
                        + batalha.getTreinadorEscolhido().getNome());
                label.setBounds(10, 10, 400, 30);

                JLabel labelHora = new JLabel(batalha.getData().toString());
                labelHora.setBounds(200, 10, 400, 30);

                // mostrar os pokemaos de cada treinador
                JLabel label2 = new JLabel(batalha.getPokemaoInicial().getNome());
                label2.setBounds(10, 40, 200, 30);
                label2.setHorizontalAlignment(JLabel.CENTER);
                JLabel label3 = new JLabel(batalha.getPokemaoEscolhido().getNome());
                label3.setBounds(290, 40, 200, 30);
                label3.setHorizontalAlignment(JLabel.CENTER);

                // exibir um label "vs" entre os pokemaos e com fonte grande
                JLabel label4 = new JLabel("VS");
                label4.setBounds(210, 40, 80, 30);
                label4.setHorizontalAlignment(JLabel.CENTER);
                Font currentFont = label4.getFont();
                Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
                label4.setFont(newFont);

                // definir o vencedor da batalha
                if (batalha.isInicialVencedor()) {
                    label2.setOpaque(true);
                    label2.setBackground(new Color(0, 255, 0, 30));
                    label3.setOpaque(true);
                    label3.setBackground(new Color(255, 0, 0, 30));
                } else {
                    label2.setOpaque(true);
                    label2.setBackground(new Color(255, 0, 0, 30));
                    label3.setOpaque(true);
                    label3.setBackground(new Color(0, 255, 0, 30));
                }

                panel.add(label);
                panel.add(labelHora);
                panel.add(label2);
                panel.add(label3);
                panel.add(label4);

                this.add(panel);

                // tornando o painel visível
                this.setVisible(true);
            }

            this.setVisible(true);
        }
    }
}
