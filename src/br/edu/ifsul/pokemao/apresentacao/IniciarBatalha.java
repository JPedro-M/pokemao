package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.util.ArrayList;
/*
 * Tela de seleção do pokemao que será usado pelo usuário em batalha.
 * 
 * 
 */
public class IniciarBatalha extends JFrame {
    public IniciarBatalha(TreinadorRepository treinadorRepository) {
        // escolher pokemao entre lista de pokemaos do treinador logado
        setTitle("Batalha");
        setBounds(200, 75, 800, 600);       

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository().
            listarDoTreinador(treinadorRepository.getTreinadorLogado());

        JLabel label = new JLabel("Centro de batalhas");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        label.setFont(newFont);
        label.setBounds(120, 10, 400, 30);
        JLabel label2 = new JLabel("Selecione o pokemão que irá batalhar.");
        label2.setBounds(120, 40, 400, 30);
        add(label); add(label2);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        add(voltar);

        this.setVisible(true);

        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });
        
        JPanel panel = new ListaPokemaos(treinadorRepository, pokemaos, "batalha");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 80, this.getWidth(), this.getHeight() - 120);
        add(scrollPane);

        setVisible(true);
    }
}
