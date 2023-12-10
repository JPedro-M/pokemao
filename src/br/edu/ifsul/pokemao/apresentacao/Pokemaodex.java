package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoCatalogo;
import br.edu.ifsul.pokemao.persistencia.PokemaoCatalogoRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class Pokemaodex extends JFrame {
    /**
     * Aqui os treinadores/ usuários podem ver a lista completa de todos os
     * pokemaos existentes no catálogo do sistema (pokemãodex).
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public Pokemaodex(TreinadorRepository treinadorRepository) {
        // coletando os pokemaos do sistema
        ArrayList<PokemaoCatalogo> pokemaos = new PokemaoCatalogoRepository().listar();

        // configurações da janela
        this.setTitle("Pokémãodex");
        this.setBounds(200, 50, 600, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // elementos da janela
        JPanel panel = new ListaCatalogo(treinadorRepository, pokemaos, "pokedex");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);

        JLabel label = new JLabel("Pokémãodex");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Esses são todos os pokemãos que existem.");
        label2.setBounds(120, 40, 400, 30);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);

        // adicionando elementos à janela
        add(scrollPane);
        add(label);
        add(label2);
        add(voltar);

        // ações dos botões
        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }

}
