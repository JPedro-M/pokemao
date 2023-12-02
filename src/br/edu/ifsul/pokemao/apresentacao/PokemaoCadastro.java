package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

public class PokemaoCadastro extends JFrame {
    PokemaoCadastro() {
        this.setTitle("Cadastro");
        this.setSize(600, 500);
        JLabel lUser = new JLabel("Usúario");
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

        JLabel lIdade = new JLabel("Idade");
            lIdade.setBounds(25, 160, 100, 40);
        JTextField tIdade = new JTextField();
            tIdade.setBounds(90, 165, 200, 30);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setBounds(65, 230, 200, 50);

        this.add(lNome); this.add(tNome);
        this.add(lIdade); this.add(tIdade);
        this.add(lUser); this.add(tUser);
        this.add(lSenha); this.add(tSenha);
        this.add(cadastrar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}
