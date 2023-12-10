package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;

import javax.swing.*;

import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoLobby extends JFrame {
    /**
     * Lobby. Ponto acessado após usuário realizar o login com sucesso.
     * <p>
     * Aqui o usuário pode escolher uma das ações a seguir:
     * - Batalhar com seus pokemãos;
     * - Trocar pokemãos;
     * - Acessar a enciclopédia dos pokemãos;
     * - Administrar seus pokemãos;
     * - Sair do seu login;
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado.
     */
    PokemaoLobby(TreinadorRepository treinadorRepository) {
        // configurações da janela
        this.setTitle("Lobby");
        this.setBounds(200, 75, 600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // elementos da janela
        JPanel header = new JPanel();
        header.setBounds(0, 0, this.getWidth(), 50);
        JLabel msgTela = new JLabel("Bem vindo, " + treinadorRepository.getTreinadorLogado().getNome() + "!");
        msgTela.setBounds(0, 10, this.getWidth(), 50);
        msgTela.setHorizontalAlignment(JLabel.CENTER);
        Font currentFont = msgTela.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        msgTela.setFont(newFont);
        header.add(msgTela);
        JButton edicao = new JButton("Editar perfil");
        edicao.setBounds(this.getWidth() - 140, 0, 100, 50);
        header.add(edicao);

        JButton batalha = new JButton("Batalhar");
        batalha.setBounds(125, 75, 150, 75);

        JButton troca = new JButton("Trocar");
        troca.setBounds(350, 75, 150, 75);

        JButton pegar = new JButton("Pegar");
        pegar.setBounds(125, 175, 150, 75);

        JButton dex = new JButton("Enciclopédia");
        dex.setBounds(350, 175, 150, 75);

        JButton meuspokemao = new JButton("Meus Pokémãos");
        meuspokemao.setBounds(225, 275, 150, 75);

        JButton sair = new JButton("Sair");
        sair.setBounds(10, this.getHeight() - 75, 90, 30);

        // adicionando elementos à janela
        this.add(header);
        this.add(sair);
        this.add(batalha);
        this.add(troca);
        this.add(pegar);
        this.add(dex);
        this.add(meuspokemao);

        // ações dos botões
        edicao.addActionListener(e -> {
            new TreinadorEditar(treinadorRepository);
        });

        troca.addActionListener(e -> {
            new IniciarTroca(treinadorRepository);
            this.dispose();
        });

        pegar.addActionListener(e -> {
            new Natureza(treinadorRepository);
            this.dispose();
        });

        meuspokemao.addActionListener(e -> {
            new MeusPokemaos(treinadorRepository);
            this.dispose();
        });

        batalha.addActionListener(e -> {
            new IniciarBatalha(treinadorRepository);
            this.dispose();
        });

        dex.addActionListener(e -> {
            new Pokemaodex(treinadorRepository);
            this.dispose();
        });

        sair.addActionListener(e -> {
            new TreinadorLogin();
            treinadorRepository.setTreinadorLogado(null);
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }
}
