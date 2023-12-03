package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class PokemaoLogin extends JFrame {
    PokemaoLogin() {
        TreinadorRepository treinadorRepository = new TreinadorRepository();

        this.setTitle("Login");
        this.setSize(600, 600);

        JLabel user = new JLabel("Usuário");
        user.setBounds(125, 125, 150, 25);
        JTextField nUser = new JTextField();
        nUser.setBounds(200, 125, 150, 25);

        JLabel senha = new JLabel("Senha");
        senha.setBounds(125, 150, 150, 25);
        JPasswordField senhaF = new JPasswordField();
        senhaF.setBounds(200, 150, 150, 25);

        JButton acesso = new JButton("Acessar");
        acesso.setBounds(175, 180, 100, 35);

        JLabel msgCad = new JLabel("Não possui uma conta?");
        msgCad.setBounds(150, 225, 300, 25);
        JButton cadastro = new JButton("Cadastre-se");
        cadastro.setBounds(155, 250, 150, 25);

        JLabel dNome = new JLabel();
        dNome.setBounds(120, 250, 100, 25);
        JLabel dSenha = new JLabel();
        dSenha.setBounds(210, 250, 100, 25);

        JLabel pokemao = new JLabel("POKEMÃO");
        pokemao.setBounds(185, 45, 200, 50);

        JLabel lErro = new JLabel("Usuário ou senha inválidos.");
        lErro.setForeground(Color.RED);
        lErro.setVisible(false);
        lErro.setBounds(175, 210, 200, 25);

        this.add(pokemao);
        this.add(user);
        this.add(nUser);
        this.add(senha);
        this.add(senhaF);
        this.add(acesso);
        this.add(dNome);
        this.add(dSenha);
        this.add(cadastro);
        this.add(msgCad);
        this.add(lErro);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        acesso.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = nUser.getText();
                String senha = new String(senhaF.getPassword());
                if (treinadorRepository.login(user, senha)) {
                    System.out.println("Login realizado com sucesso!");
                    new PokemaoLobby();
                    dispose();
                } else {
                    System.out.println("Usuário ou senha inválidos.");
                    lErro.setVisible(true);
                }
            }
        });
    }
}