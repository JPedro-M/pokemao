package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.util.ArrayList;

public class IniciarBatalha extends JFrame {
    /**
     * Tela de seleção do pokemao que será usado pelo usuário em batalha.
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public IniciarBatalha(TreinadorRepository treinadorRepository) {
        // coletando os pokemaos do treinador logado
        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository()
                .listarDoTreinador(treinadorRepository.getTreinadorLogado());

        // configurações da janela
        setTitle("Batalha");
        setBounds(200, 75, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // elementos da janela
        JLabel label = new JLabel("Centro de batalhas");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Selecione o pokemão que irá batalhar.");
        label2.setBounds(120, 40, 400, 30);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);

        // lista de pokemaos
        JPanel panel = new ListaPokemaos(treinadorRepository, pokemaos, "batalha");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);

        // adicionando elementos à janela
        add(label);
        add(label2);
        add(voltar);
        add(scrollPane);

        // ações dos botões
        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }
}
