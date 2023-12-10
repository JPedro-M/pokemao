package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Treinador;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TreinadorEditar extends JFrame {
    /**
     * Tela para edição dos dados do treinador logado.
     * <p>
     * Também é possível encerrar a conta do treinador por aqui.
     * 
     * @param treinadorRepository Repositório de treinadores para acesso ao
     *                            treinador logado
     */
    TreinadorEditar(TreinadorRepository treinadorRepository) {
        // configurações da janela
        this.setTitle("Editar treinador");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // elementos da janela
        JLabel lUser = new JLabel("Usuário");
        lUser.setBounds(25, 50, 100, 40);
        JTextField tUser = new JTextField(treinadorRepository.getTreinadorLogado().getUser());
        tUser.setBounds(90, 55, 200, 30);

        JLabel lSenha = new JLabel("Senha");
        lSenha.setBounds(25, 100, 100, 40);
        JPasswordField tSenha = new JPasswordField();
        tSenha.setBounds(90, 105, 200, 30);

        JLabel lNome = new JLabel("Nome");
        lNome.setBounds(25, 150, 100, 40);
        JTextField tNome = new JTextField(treinadorRepository.getTreinadorLogado().getNome());
        tNome.setBounds(90, 155, 200, 30);

        JLabel lIdade = new JLabel("Idade em anos");
        lIdade.setBounds(25, 200, 150, 40);
        JTextField tIdade = new JTextField();
        tIdade.setBounds(120, 205, 170, 30);

        JButton editar = new JButton("Editar");
        editar.setBounds(65, 270, 200, 40);

        JButton encerrar = new JButton("Encerrar conta");
        encerrar.setBounds(65, 320, 200, 40);

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
        this.add(editar);
        this.add(encerrar);
        this.add(voltar);

        // ações dos botões
        editar.addActionListener(e -> {
            Treinador treinador = treinadorRepository.getTreinadorLogado();
            if (!tUser.getText().equals("")) {
                treinador.setUser(tUser.getText());
            }
            if (!new String(tSenha.getPassword()).equals("")) {
                treinador.setSenha(new String(tSenha.getPassword()));
            }
            if (!tNome.getText().equals("")) {
                treinador.setNome(tNome.getText());
            }
            if (!tIdade.getText().equals("")) {
                treinador.setNascimento(treinador.getNascimento().minusYears(Integer.parseInt(tIdade.getText())));
            }
            if (treinadorRepository.editar(treinador)) {
                JOptionPane.showMessageDialog(null, "Edição realizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao editar!");
            }
            this.dispose();
        });

        voltar.addActionListener(e -> {
            this.dispose();
        });

        encerrar.addActionListener(e -> {
            // pedir confirmação
            int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja encerrar sua conta?",
                    "Aviso", JOptionPane.YES_NO_OPTION);
            // remover treinador
            if (dialogResult == JOptionPane.YES_OPTION
                    && treinadorRepository.remover(treinadorRepository.getTreinadorLogado().getId())) {
                JOptionPane.showMessageDialog(null, "Conta encerrada com sucesso!");
                new TreinadorLogin();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conta!");
            }
        });

        // tornando a janela visível
        this.setVisible(true);
    }
}
