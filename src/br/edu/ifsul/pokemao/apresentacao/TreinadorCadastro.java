package br.edu.ifsul.pokemao.apresentacao;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TreinadorCadastro extends JFrame {
    /**
     * Aqui os usuários sem cadastro podem se registrar na plataforma e começar sua
     * jornada no mundo pokemão como treinadores.
     * 
     * Após o cadastro, o usuário é redirecionado para a tela de login.
     */
    TreinadorCadastro() {
        // configurações da janela
        this.setTitle("Cadastro");
        this.setBounds(200, 75, 600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // elementos da janela
        JLabel lUser = new JLabel("Usuário");
        lUser.setBounds(25, 50, 100, 40);
        JTextField tUser = new JTextField();
        tUser.setBounds(90, 55, 200, 30);

        JLabel lSenha = new JLabel("Senha");
        lSenha.setBounds(25, 100, 100, 40);
        JPasswordField tSenha = new JPasswordField();
        tSenha.setBounds(90, 105, 200, 30);

        JLabel lNome = new JLabel("Nome");
        lNome.setBounds(25, 150, 100, 40);
        JTextField tNome = new JTextField();
        tNome.setBounds(90, 155, 200, 30);

        JLabel lIdade = new JLabel("Idade em anos");
        lIdade.setBounds(25, 200, 150, 40);
        JTextField tIdade = new JTextField();
        tIdade.setBounds(120, 205, 170, 30);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setBounds(65, 270, 200, 50);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(10, 10, 90, 30);
        
        // adicionando elementos à janela
        this.add(lNome);
        this.add(tNome);
        this.add(lIdade);
        this.add(tIdade);
        this.add(lUser);
        this.add(tUser);
        this.add(lSenha);
        this.add(tSenha);
        this.add(cadastrar);
        this.add(voltar);

        // ações dos botões
        cadastrar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tUser.getText().equals("") || new String(tSenha.getPassword()).equals("") || tNome.getText().equals("")
                    || tIdade.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {
                    TreinadorRepository treinadorRepository = new TreinadorRepository();
                    // nascimento = dia de hoje - idade
                    LocalDateTime nascimento = LocalDateTime.now().minusYears(Integer.parseInt(tIdade.getText()));
                    Treinador treinador = new Treinador(tUser.getText(), new String(tSenha.getPassword()), tNome.getText(), nascimento);
                    if (treinadorRepository.cadastrar(treinador) != -1) {
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar!");
                    }
                    dispose();
                    new TreinadorLogin();
                }

            }
        });
        
        voltar.addActionListener(e -> {
            new TreinadorLogin();
            this.dispose();
        });

        // tornando a janela visível
        this.setVisible(true);
    }
}
