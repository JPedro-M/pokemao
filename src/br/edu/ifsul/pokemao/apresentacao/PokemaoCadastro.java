package br.edu.ifsul.pokemao.apresentacao;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class PokemaoCadastro extends JFrame {
    PokemaoCadastro() {
        this.setTitle("Cadastro");
        this.setBounds(200, 75, 600,500);       
        
        JLabel lUser = new JLabel("Usuário");
            lUser.setBounds(25, 10, 100, 40);
        JTextField tUser = new JTextField();
            tUser.setBounds(90, 15, 200, 30);

        JLabel lSenha = new JLabel("Senha");
            lSenha.setBounds(25, 60, 100, 40);
        JTextField tSenha = new JTextField();
            tSenha.setBounds(90, 65, 200, 30);

        JLabel lNome = new JLabel("Nome");
            lNome.setBounds(25, 110, 100, 40);
        JTextField tNome = new JTextField();
            tNome.setBounds(90, 115, 200, 30);

        JLabel lIdade = new JLabel("Idade em anos");
            lIdade.setBounds(25, 160, 150, 40);
        JTextField tIdade = new JTextField();
            tIdade.setBounds(120, 165, 170, 30);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setBounds(65, 230, 200, 50);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        add(voltar);

        voltar.addActionListener(e -> {
            new PokemaoLogin();
            this.dispose();
        });

        this.add(lNome); this.add(tNome);
        this.add(lIdade); this.add(tIdade);
        this.add(lUser); this.add(tUser);
        this.add(lSenha); this.add(tSenha);
        this.add(cadastrar); this.add(voltar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        cadastrar.addActionListener (new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tUser.getText().equals("") || tSenha.getText().equals("") || tNome.getText().equals("") || tIdade.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {
                    TreinadorRepository treinadorRepository = new TreinadorRepository();
                    // nascimento = dia de hoje - idade
                    LocalDateTime nascimento = LocalDateTime.now().minusYears(Integer.parseInt(tIdade.getText()));
                    Treinador treinador = new Treinador(tUser.getText(), tSenha.getText(), tNome.getText(), nascimento);
                    if (treinadorRepository.cadastrar(treinador) != -1) {
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar!");
                    }
                    dispose();
                    new PokemaoLogin();
                }
            }
        });
    }
}
