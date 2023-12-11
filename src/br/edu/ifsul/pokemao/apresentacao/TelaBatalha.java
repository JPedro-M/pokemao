package br.edu.ifsul.pokemao.apresentacao;

import java.awt.Font;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.Batalha;
import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.AcontecimentoRepository;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class TelaBatalha extends JFrame {
    Batalha batalha;
    PokemaoTreinador inicial;
    PokemaoTreinador oponente;

    TreinadorRepository repositorio;

    JLabel statusInicial;
    JLabel statusOponente;
    JLabel header;

    JButton atk;
    JButton pocao;

    /**
     * A Batalha ocorre aqui. O usuário comandará seu pokemao em batalha contra
     * outro pokemao que pertencerá ou a um outro usuário ou será um pokemao gerado
     * aleatoriamente.
     *
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     */
    public TelaBatalha(TreinadorRepository treinadorRepository, PokemaoTreinador escolha) {
        // configurações da janela
        this.setTitle("Batalha");
        this.setBounds(200, 75, 700, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // escolha ou geração do pokemao oponente
        this.inicial = escolha;
        this.oponente = new PokemaoTreinadorRepository().escolherPokemaoParaBatalha(inicial);
        oponente.fullHp();

        this.repositorio = treinadorRepository;

        // criação da batalha
        batalha = new Batalha(inicial, oponente);

        // painel do pokemao inicial
        JPanel panelPokemaoInicial = new JPanel();
        panelPokemaoInicial.setBounds(50, 150, 250, 250);
        panelPokemaoInicial.setLayout(null);
        panelPokemaoInicial.setBackground(inicial.getPokemao().getRaridadeColor());
        panelPokemaoInicial.setVisible(false);

        JLabel nomeInicial = new JLabel(inicial.getNome());
        Font newFont = nomeInicial.getFont().deriveFont(Font.BOLD, 20f);
        nomeInicial.setFont(newFont);
        nomeInicial.setBounds(10, 10, 200, 30);

        statusInicial = new JLabel("HP: " + inicial.getHp() + " | ATK: " + inicial.getAtaque() + " | DEF: "
                + inicial.getDefesa());
        statusInicial.setBounds(10, 100, 200, 30);

        JLabel emojiInicial = new JLabel(inicial.getPokemao().getEmoji());
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 50);
        emojiInicial.setFont(font);
        emojiInicial.setBounds(10, -50, 200, 200);

        JLabel treinadorInicial = new JLabel("Treinador: " + treinadorRepository.getTreinadorLogado().getNome());
        treinadorInicial.setBounds(10, 140, 200, 30);

        JLabel descInicial = new JLabel("Meu pokemão");
        descInicial.setBounds(10, 170, 200, 30);

        panelPokemaoInicial.add(nomeInicial);
        panelPokemaoInicial.add(statusInicial);
        panelPokemaoInicial.add(emojiInicial);
        panelPokemaoInicial.add(treinadorInicial);
        panelPokemaoInicial.add(descInicial);

        panelPokemaoInicial.setVisible(true);

        // painel do pokemao oponente
        JPanel panelPokemaoOponente = new JPanel();
        panelPokemaoOponente.setBounds(350, 105, 250, 250);
        panelPokemaoOponente.setLayout(null);
        panelPokemaoOponente.setBackground(oponente.getPokemao().getRaridadeColor());
        panelPokemaoOponente.setVisible(false);

        JLabel nomeOponente = new JLabel(oponente.getNome());
        nomeOponente.setFont(newFont);
        nomeOponente.setBounds(10, 10, 200, 30);

        statusOponente = new JLabel("HP: " + oponente.getHp() + " | ATK: " + oponente.getAtaque() + " | DEF: "
                + oponente.getDefesa());
        statusOponente.setBounds(10, 100, 200, 30);

        JLabel emojiOponente = new JLabel(oponente.getPokemao().getEmoji());
        emojiOponente.setFont(font);
        emojiOponente.setBounds(10, -50, 200, 200);

        JLabel treinadorOponente = new JLabel("Treinador: " + oponente.getTreinador().getNome());
        System.out.println(oponente.getTreinador().getNome());
        treinadorOponente.setBounds(10, 140, 200, 30);

        JLabel descOponente = new JLabel("Oponente");
        descOponente.setBounds(10, 170, 200, 30);

        panelPokemaoOponente.add(nomeOponente);
        panelPokemaoOponente.add(statusOponente);
        panelPokemaoOponente.add(emojiOponente);
        panelPokemaoOponente.add(treinadorOponente);
        panelPokemaoOponente.add(descOponente);

        panelPokemaoOponente.setVisible(true);

        // elementos da janela
        header = new JLabel("BATALHA");
        header.setBounds(0, 30, this.getWidth(), 50);
        header.setHorizontalAlignment(JLabel.CENTER);
        Font headerFont = header.getFont().deriveFont(Font.BOLD, 18f);
        header.setFont(headerFont);

        atk = new JButton("Atacar");
        atk.setBounds(70, this.getHeight() - 100, 150, 45);

        pocao = new JButton("Poção de Cura");
        pocao.setBounds(250, this.getHeight() - 100, 150, 45);

        JButton voltar = new JButton("<-- Desistir");
        voltar.setBounds(10, 10, 100, 30);

        // adicionando elementos à janela
        this.add(header);
        this.add(atk);
        this.add(pocao);
        this.add(panelPokemaoInicial);
        this.add(panelPokemaoOponente);
        this.add(voltar);

        // ações dos botões
        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            this.dispose();
        });

        atk.addActionListener(e -> {
            // ataque do pokemao inicial
            realizarAtaque(true);
        });

        pocao.addActionListener(e -> {
            // defesa do pokemao inicial
            tomarPocao(true);
        });

        // tornando a janela visível
        this.setVisible(true);
    }

    /**
     * Classe responsável por realizar o ataque de um pokemao contra outro.
     * <p>
     * O dano causado pelo atacante é calculado subtraindo a defesa do atacado do
     * ataque do atacante. Se o dano for menor que a defesa do atacado, o dano é
     * recalculado até que seja maior que a defesa do atacado. O dano é então
     * somado a um valor aleatório entre 0 e a velocidade de ataque.
     * 
     * @param atacante
     * @param atacado
     */
    private void realizarAtaque(Boolean inicialAtaca) {
        PokemaoTreinador atacante;
        PokemaoTreinador atacado;
        if (inicialAtaca) {
            atacante = inicial;
            atacado = oponente;
        } else {
            atacante = oponente;
            atacado = inicial;
        }

        // definindo o valor do dano
        int dano = atacante.getAtaque() - atacado.getDefesa();
        int x = 1;
        while (dano < 0) {
            int danoanterior = dano + 0;
            dano = atacante.getAtaque() - (atacado.getDefesa() / x);
            if (dano == danoanterior) {
                dano = 2;
                break;
            }
            x++;
        }
        dano += new Random().nextInt(atacante.getVelocidadeAtaque());
        atacado.setHp(atacado.getHp() - dano);

        // atualizando o status do pokemao atacado
        if (inicialAtaca) {
            statusOponente.setText("HP: " + atacado.getHp() + " | ATK: " + atacado.getAtaque() + " | DEF: "
                    + atacado.getDefesa());
        } else {
            statusInicial.setText("HP: " + atacado.getHp() + " | ATK: " + atacado.getAtaque() + " | DEF: "
                    + atacado.getDefesa());
        }

        // definindo o texto do header
        String textoheader = "";
        if (atacante.getNome().equals(atacado.getNome())) {
            System.out.println("atacante e atacado são iguais");
            textoheader = atacante.getNome() + " de " + atacante.getTreinador().getNome() + " atacou "
                    + atacado.getNome() + " de " + atacado.getTreinador().getNome();
        } else {
            textoheader = atacante.getNome() + " atacou " + atacado.getNome();
        }
        header.setText(textoheader + " e causou " + dano + " de dano");
        System.out.println(header.getText());

        // desabilitando os botões de ataque e poção
        atk.setEnabled(true);
        pocao.setEnabled(true);

        // verificando o resultado da batalha e executando o ataque do oponente
        checkBattleResult();
        if (inicialAtaca) {
            ataqueDoOponente();
        }
    }

    private void tomarPocao(Boolean curarInicial) {
        PokemaoTreinador pokemao;
        if (curarInicial) {
            pokemao = inicial;
        } else {
            pokemao = oponente;
        }

        // curando o pokemao
        if (pokemao.getHp() > 85) {
            pokemao.setHp(100);
        } else {
            pokemao.setHp(pokemao.getHp() + 15);
        }

        // atualizando o status do pokemao
        if (curarInicial) {
            statusInicial.setText("HP: " + pokemao.getHp() + " | ATK: " + pokemao.getAtaque() + " | DEF: "
                    + pokemao.getDefesa());
        } else {
            statusOponente.setText("HP: " + pokemao.getHp() + " | ATK: " + pokemao.getAtaque() + " | DEF: "
                    + pokemao.getDefesa());
        }

        // definindo o texto do header
        header.setText(pokemao.getNome() + " tomou uma poção de cura e recuperou 15 de HP");
        System.out.println(header.getText());

        // desabilitando os botões de ataque e poção
        atk.setEnabled(true);
        pocao.setEnabled(true);

        // verificando o resultado da batalha e executando o ataque do oponente
        checkBattleResult();
        if (curarInicial) {
            ataqueDoOponente();
        }
    }

    /**
     * Código "bot" responsável por realizar o ataque do oponente.
     * <p>
     * O oponente tem 70% de chance de atacar e 30% de chance de usar uma poção de
     * cura. Se o HP do oponente for menor do que 10, ele vai preferir se curar.
     */
    private void ataqueDoOponente() {
        // executar o ataque do oponente após 2 segundos
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chance = (int) (Math.random() * 100);
                if (chance < 70 && oponente.getHp() > 10) {
                    realizarAtaque(false);
                } else {
                    tomarPocao(false);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();

        // habilitando os botões de ataque e poção
        atk.setEnabled(false);
        pocao.setEnabled(false);
    }

    /**
     * Verifica o resultado da batalha entre dois pokemãos.
     * <p>
     * Se o oponente tiver pontos de vida (HP) igual ou menor que zero, exibe uma
     * mensagem de vitória, define o HP do oponente como zero, marca a batalha como
     * vencida e adiciona a batalha ao repositório.
     * Caso contrário, se o pokemao inicial tiver HP igual ou menor que zero, exibe
     * uma mensagem de derrota.
     *
     */
    private void checkBattleResult() {
        if (oponente.getHp() <= 0) {
            // vitória do pokemao inicial
            atk.setEnabled(true);
            pocao.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Seu pokemão " + inicial.getNome() + " venceu a batalha! (+50XP)",
                    "VITÓRIA",
                    JOptionPane.INFORMATION_MESSAGE);
            header.setText("Aguarde...");
            oponente.setHp(0);
            batalha.setVencedor(true);
            new AcontecimentoRepository().adicionarBatalha(batalha);

            inicial.addXp(50);
            new PokemaoTreinadorRepository().editar(inicial);
            new PokemaoTreinadorRepository().editar(oponente);

            new PokemaoLobby(repositorio);
            this.dispose();
        } else if (inicial.getHp() <= 0) {
            // vitória do pokemao oponente
            atk.setEnabled(true);
            pocao.setEnabled(true);
            JOptionPane.showMessageDialog(null, "O pokemão do adversário levou a melhor.", "NÃO FOI DESSA VEZ",
                    JOptionPane.INFORMATION_MESSAGE);
            header.setText("Aguarde...");
            inicial.setHp(0);
            batalha.setVencedor(false);
            new AcontecimentoRepository().adicionarBatalha(batalha);

            oponente.addXp(50);
            new PokemaoTreinadorRepository().editar(inicial);
            new PokemaoTreinadorRepository().editar(oponente);

            new PokemaoLobby(repositorio);
            this.dispose();
        }
    }
}
