package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class MeusPokemaos extends JFrame {
    /**
     * Menu onde o usuário pode administrar os pokemaos associados a sua conta.
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public MeusPokemaos(TreinadorRepository treinadorRepository) {
        // coletando os pokemaos do treinador logado
        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository()
                .listarDoTreinador(treinadorRepository.getTreinadorLogado());

        // configurações da janela
        this.setTitle("Meus Pokemãos");
        setBounds(200, 75, 600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // elementos da janela
        JPanel panel = new ListaPokemaos(treinadorRepository, pokemaos, "telatreinador");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);

        JLabel label = new JLabel("Meus pokémãos");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Clique em um pokemão para ações.");
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
