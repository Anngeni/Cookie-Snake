import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import javax.swing.Timer;

public class snakePractice {

    static Line2D.Double line = new Line2D.Double(0, 250, 100, 250);

    public static void main(String[] args) {

        JFrame frame = new JFrame("snake");

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                line = moveLine(line); //will become update lines

                g2d.draw(line);

            }
        };

        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        Timer timer = new Timer(200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();

            }

        });
        timer.start();

    }

    public static Line2D.Double moveLine(Line2D.Double line) {

        double x1 = line.getX1();
        double x2 = line.getX2();

        double newX1 = x1 + 25;
        double newX2 = x2 + 25;

        Line2D.Double newLine = new Line2D.Double(newX1, 250, newX2, 250);

        return newLine;

    }

}