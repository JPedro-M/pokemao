package br.edu.ifsul.pokemao.apresentacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Natureza extends JPanel {
    private boolean dragging = false;
    private Point objectPos = new Point(100, 100);
    private Point mouseOffset = new Point();
    private Rectangle dropZone = new Rectangle(200, 200, 50, 50);

    public Natureza() {
        JFrame frame = new JFrame("Draggable Object");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(300, 300);
        frame.setVisible(true);

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
                        JOptionPane.showMessageDialog(null, "Object dropped in zone!");
                    }
                    dragging = false;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(dropZone.x, dropZone.y, dropZone.width, dropZone.height);
        g.setColor(Color.BLUE);
        g.fillOval(objectPos.x, objectPos.y, 50, 50);
    }
}