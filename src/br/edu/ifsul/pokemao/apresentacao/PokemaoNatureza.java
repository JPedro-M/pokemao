package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoNatureza extends JFrame {
    // mostrar um pokemao aleatório para que o treinador possa capturá-lo
    // essa é a tela de captura

    // primeiro, aparece o emoji do pokemao aleatório
    // há uma opção de abanar para o pokemao
    // o pokemao tem uma chance de fugir, não responder ao abanar, ou aceitar ser capturado
    // as chances variam de acordo com a raridade do pokemao
    // se o pokemao fugir, o treinador perde a chance de capturá-lo
    // se o pokemao não responder, o treinador pode tentar novamente
    // se o pokemao aceitar, ele vai para a bolsa do treinador

    public PokemaoNatureza(TreinadorRepository treinadorRepository) {
        this.setTitle("Captura");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // método para gerar um pokemao aleatório
        PokemaoTreinador pokemaoSelvagem = new PokemaoTreinadorRepository().gerarPokemaoSelvagem();

        JLabel emoji = new JLabel(pokemaoSelvagem.getPokemao().getEmoji());
        emoji.setBounds(10, 30, 100, 100);
        // escolher uma fonte que suporte emojis 3D
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 50);
        emoji.setFont(font);
        add(emoji);

        JLabel header = new JLabel("Você encontrou um " + pokemaoSelvagem.getNome() + " selvagem!");
        header.setBounds(10, 10, 400, 50);
        add(header);

        JLabel callToAction = new JLabel("Abane para tentar capturá-lo!");
        callToAction.setBounds(10, 90, 400, 50);
        add(callToAction);

        JButton abanar = new JButton("Abanar");
        abanar.setBounds(10, 150, 100, 50);
        add(abanar);
        abanar.setVisible(true);

        JButton voltar = new JButton("Voltar");
            voltar.setBounds(500, 400, 100, 40);


        
        this.add(voltar);

        voltar.addActionListener(e ->{
            new PokemaoLobby(treinadorRepository);
            this.dispose();

        }); 

        JLabel specs = new JLabel("Raridade: " + pokemaoSelvagem.getPokemao().getRaridadeString() + "\n"+
                "Ataque: " + pokemaoSelvagem.getAtaque() + "\n"+
                "Defesa: " + pokemaoSelvagem.getDefesa() + "\n"+
                "Velocidade de ataque: " + pokemaoSelvagem.getVelocidadeAtaque() + "\n");
        specs.setBounds(10, 200, 400, 200);
        add(specs);
        specs.setVisible(false);

        JLabel desc = new JLabel(pokemaoSelvagem.getPokemao().getDescricao());
        desc.setBounds(10, 300, 400, 200);
        add(desc);
        desc.setVisible(false);

        JButton ok = new JButton("OK");
        ok.setBounds(10, 500, 100, 50);
        add(ok);
        ok.setVisible(false);

        abanar.addActionListener(e -> {
            // gerar número aleatório entre 0 e 100
            int chance = (int) (Math.random() * 100);
            header.setText("Carregando...");
            
            // se a raridade do pokemao for comum, a chance de captura é 50%
            // se a raridade do pokemao for raro, a chance de captura é 25%
            // se a raridade do pokemao for lendário, a chance de captura é 10%
            if ((chance < 50 && pokemaoSelvagem.getPokemao().getRaridade() ==1) || (chance < 25 && pokemaoSelvagem.getPokemao().getRaridade() ==2) || (chance < 10 && pokemaoSelvagem.getPokemao().getRaridade() ==3)) {
                // alterar tela para mostrar que o pokemao foi capturado
                header.setText("CAPTURADO!");
                callToAction.setText("O " + pokemaoSelvagem.getNome() + " foi para a sua bolsa!");
                abanar.setVisible(false);
                specs.setVisible(true);
                desc.setVisible(true);
                ok.setVisible(true);
                pokemaoSelvagem.setTreinador(treinadorRepository.getTreinadorLogado());
                new PokemaoTreinadorRepository().cadastrar(pokemaoSelvagem);
            } else if ((chance < 60 && pokemaoSelvagem.getPokemao().getRaridade() ==1) || (chance < 50 && pokemaoSelvagem.getPokemao().getRaridade() ==2) || (chance < 65 && pokemaoSelvagem.getPokemao().getRaridade() ==3)) {
                // alterar tela para mostrar que o pokemao fugiu
                header.setText("O " + pokemaoSelvagem.getNome() + " fugiu!");
                abanar.setVisible(false);
                
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

        this.setVisible(true);
    }
}

    
