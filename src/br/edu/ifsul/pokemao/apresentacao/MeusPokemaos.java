package br.edu.ifsul.pokemao.apresentacao;

import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class MeusPokemaos extends JFrame {
    public MeusPokemaos(TreinadorRepository treinadorRepository) {
        this.setTitle("Meus Pokemãos");
        setBounds(200, 75, 600, 500);       

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        ArrayList<PokemaoTreinador> pokemaos = new PokemaoTreinadorRepository().listarDoTreinador(treinadorRepository.getTreinadorLogado());

        JPanel panel = new ListaPokemaos(treinadorRepository, pokemaos, "telatreinador");
        panel.setBounds(10, 50, 400, 400);
        add(panel);

    }
}
