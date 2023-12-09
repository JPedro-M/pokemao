package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;

import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

import java.awt.*;
import java.awt.event.*;
/*
 * Os usuários acessam essa área para poder capturarem novos pokemaos
 * para fazer isso eles devem "balançar" a bola azul na tela e com o tempo
 * encontrará um pokemao.
 * 
 */
public class Natureza extends JPanel {
    private boolean dragging = false;
    private Point objectPos = new Point(100, 200);
    private Point mouseOffset = new Point();
    private Rectangle dropZone = new Rectangle(300, 50, 250, 500);

    public Natureza(TreinadorRepository treinadorRepository) {
        JFrame frame = new JFrame("Natureza");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setBounds(200, 75, 600, 600);
        frame.setResizable(false);

        JLabel label = new JLabel("Arraste o treinador até a grama para pegar um pokemão!");
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(20f);
        label.setFont(newFont);
        label.setBounds(10, 10, 400, 50);
        add(label);

        JButton voltar = new JButton("<-- Voltar");
        voltar.setBounds(frame.getHeight()-40, 10, 90, 30);
        add(voltar);

        voltar.addActionListener(e -> {
            new PokemaoLobby(treinadorRepository);
            frame.dispose();
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    objectPos.x = e.getX() - mouseOffset.x;
                    objectPos.y = e.getY() - mouseOffset.y;
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Rectangle objectBounds = new Rectangle(objectPos.x, objectPos.y, 50, 50);
                if (objectBounds.contains(e.getPoint())) {
                    dragging = true;
                    mouseOffset.x = e.getX() - objectPos.x;
                    mouseOffset.y = e.getY() - objectPos.y;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    Rectangle objectBounds = new Rectangle(objectPos.x, objectPos.y, 50, 50);
                    if (dropZone.intersects(objectBounds)) {
                        naGrama(treinadorRepository);
                    }
                    dragging = false;
                }
            }
        });
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(dropZone.x, dropZone.y, dropZone.width, dropZone.height);
        g.setColor(Color.BLUE);
        g.fillOval(objectPos.x, objectPos.y, 50, 50);
    }

    private void naGrama(TreinadorRepository treinadorRepository) {
        // chance de 50% de encontrar um pokemao ou, se não, um aviso de que não encontrou nada
        int chance = (int) (Math.random() * 100);
        if (chance < 50) {
            new PokemaoNatureza(treinadorRepository);
        } else {
            JOptionPane.showMessageDialog(null, "Não encontrou nada!");
        }
    }
}