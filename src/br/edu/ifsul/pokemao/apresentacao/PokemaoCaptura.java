package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoCaptura extends JFrame {
    /**
     * Esta é a tela de captura, que mostra um pokemao aleatório para que o
     * treinador possa capturá-lo.
     * <p>
     * Primeiro aparece o emoji do pokemao aleatório e uma opção de abanar para
     * tentar fazer a captura.
     * <p>
     * O pokemao tem uma chance de fugir, não responder ao abanar, ou aceitar ser
     * capturado. As chances variam de acordo com a raridade do pokemao.
     * <p>
     * Se o pokemao fugir, o treinador perde a chance de capturá-lo.
     * Se o pokemao não responder, o treinador pode tentar novamente.
     * Se o pokemao aceitar, ele vai para a bolsa do treinador.
     * <p>
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public PokemaoCaptura(TreinadorRepository treinadorRepository) {
        // configurações da janela
        this.setTitle("Captura");
        this.setBounds(200, 75, 600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // método para gerar um pokemao aleatório
        PokemaoTreinador pokemaoSelvagem = new PokemaoTreinadorRepository().gerarPokemaoSelvagem();

        // elementos da janela
        JLabel emoji = new JLabel(pokemaoSelvagem.getPokemao().getEmoji());
        emoji.setBounds(0, 65, this.getWidth(), 100);
        emoji.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 50);
        emoji.setFont(font);

        JLabel header = new JLabel("Você encontrou um " + pokemaoSelvagem.getNome() + " selvagem!");
        header.setBounds(0, 30, this.getWidth(), 50);
        header.setHorizontalAlignment(JLabel.CENTER);
        Font currentFont = header.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 18f);
        header.setFont(newFont);

        JLabel callToAction = new JLabel("Abane para tentar capturá-lo!");
        callToAction.setBounds(0, 150, this.getWidth(), 50);
        callToAction.setHorizontalAlignment(JLabel.CENTER);

        JButton abanar = new JButton("Abanar");
        abanar.setBounds(this.getWidth() / 2 - 75, 200, 150, 50);
        abanar.setVisible(true);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        voltar.setVisible(true);

        JLabel desc = new JLabel("<html>" + pokemaoSelvagem.getPokemao().getDescricao() + "</html>");
        desc.setBounds(this.getWidth() / 2 - 150, 300, 300, 200);
        desc.setHorizontalAlignment(JLabel.CENTER);
        desc.setVisible(false);

        JButton ok = new JButton("OK");
        ok.setBounds(10, 500, 100, 50);
        ok.setVisible(false);

        // painel com as especificações do pokemao
        JPanel specs = new JPanel();
        specs.setBounds(this.getWidth() / 2 - 150, 200, 300, 150);
        specs.setLayout(null);
        specs.setBackground(pokemaoSelvagem.getPokemao().getRaridadeColor());
        specs.setVisible(false);

        JLabel raridadeLabel = new JLabel("Raridade: " + pokemaoSelvagem.getPokemao().getRaridadeString());
        raridadeLabel.setBounds(0, 10, 300, 50);
        raridadeLabel.setHorizontalAlignment(JLabel.CENTER);
        specs.add(raridadeLabel);

        JLabel ataqueLabel = new JLabel("Ataque: " + pokemaoSelvagem.getAtaque());
        ataqueLabel.setBounds(0, 40, 300, 50);
        ataqueLabel.setHorizontalAlignment(JLabel.CENTER);
        specs.add(ataqueLabel);

        JLabel defesaLabel = new JLabel("Defesa: " + pokemaoSelvagem.getDefesa());
        defesaLabel.setBounds(0, 70, 300, 50);
        defesaLabel.setHorizontalAlignment(JLabel.CENTER);
        specs.add(defesaLabel);

        JLabel velocidadeAtaqueLabel = new JLabel("Velocidade de ataque: " + pokemaoSelvagem.getVelocidadeAtaque());
        velocidadeAtaqueLabel.setBounds(0, 100, 300, 50);
        velocidadeAtaqueLabel.setHorizontalAlignment(JLabel.CENTER);
        specs.add(velocidadeAtaqueLabel);

        // adicionando elementos à janela
        this.add(emoji);
        this.add(header);
        this.add(callToAction);
        this.add(abanar);
        this.add(voltar);
        this.add(specs);
        this.add(desc);
        this.add(ok);

        // ações dos botões
        // ao clicar em abanar, o treinador tem uma chance de capturar o pokemao
        abanar.addActionListener(e -> {
            int chance = (int) (Math.random() * 100);
            header.setText("Carregando...");

            // se a raridade do pokemao for comum, a chance de captura é 50%
            // se a raridade do pokemao for raro, a chance de captura é 25%
            // se a raridade do pokemao for lendário, a chance de captura é 10%
            if ((chance < 50 && pokemaoSelvagem.getPokemao().getRaridade() == 1)
                    || (chance < 25 && pokemaoSelvagem.getPokemao().getRaridade() == 2)
                    || (chance < 10 && pokemaoSelvagem.getPokemao().getRaridade() == 3)) {
                // alterar tela para mostrar que o pokemao foi capturado
                header.setText("CAPTURADO!");
                callToAction.setText("O " + pokemaoSelvagem.getNome() + " foi para a sua bolsa!");
                abanar.setVisible(false);
                specs.setVisible(true);
                desc.setVisible(true);
                ok.setVisible(true);
                voltar.setVisible(false);
                pokemaoSelvagem.setTreinador(treinadorRepository.getTreinadorLogado());
                new PokemaoTreinadorRepository().cadastrar(pokemaoSelvagem);
            } else if ((chance < 60 && pokemaoSelvagem.getPokemao().getRaridade() == 1)
                    || (chance < 50 && pokemaoSelvagem.getPokemao().getRaridade() == 2)
                    || (chance < 65 && pokemaoSelvagem.getPokemao().getRaridade() == 3)) {
                // alterar tela para mostrar que o pokemao fugiu
                header.setText("O " + pokemaoSelvagem.getNome() + " fugiu!");
                callToAction.setText("Mais tarde ele volta.");
                abanar.setVisible(false);
                voltar.setVisible(false);
                ok.setVisible(true);
            } else {
                // alterar tela para mostrar que o pokemao não respondeu
                header.setText("O " + pokemaoSelvagem.getNome() + " te ignorou kkkk");
                callToAction.setText("Tente novamente!");
            }
        });

        ok.addActionListener(e -> {
            this.dispose();
        });

        voltar.addActionListener(e -> {
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }
}
