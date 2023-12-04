package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.util.ArrayList;

public class IniciarTroca extends JFrame {
    public IniciarTroca(TreinadorRepository treinadorRepository) {
        // escolher pokemao entre lista de pokemaos do treinador logado
        setTitle("Troca");
        setBounds(200, 75, 800, 600);       

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository().listarDoTreinador(treinadorRepository.getTreinadorLogado());

        JLabel label = new JLabel("Escolha um pokemão para trocar");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(10, 10, 400, 50);
        add(label);

        // lista simples de pokemaos em linha, onde cada um é um botão
        // ao clicar no botão, abre a tela de escolher pokemao para troca

        JButton voltar = new JButton("Voltar");
            voltar.setBounds(500, 400, 100, 40);


        
        this.add(voltar);

        voltar.addActionListener(e ->{
            new PokemaoLobby(treinadorRepository);
            this.dispose();

        });  
        
        
        JPanel panel = new ListaPokemaos(treinadorRepository, pokemaos, "troca");
        panel.setBounds(10, 50, 400, 400);
        add(panel);

        setVisible(true);
    }
}
