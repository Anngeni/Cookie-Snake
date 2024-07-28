import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake {

    ArrayList<SnakeLine> lines;
    int snakeSteps = 25;
    Timer timer;
    private JFrame frame;
    JPanel panel;
    BufferedImage cookie;
    int cookieX;
    int cookieY;
    int cookieCounter;

    public Snake() {

        cookieCounter = 0;
        lines = new ArrayList<>();
        lines.add(new SnakeLine(true, true, 37.5, 262.5, 137.5, 262.5));

        config();

        try {

            cookie = ImageIO.read(new File("/Users/annge/OneDrive/Documents/SNAKE/chocchip.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        randomiseCookie();

    }

    public void config() {

        frame = new JFrame("snake");
        frame.setSize(500, 500);
        frame.setResizable(false);

        frame.addKeyListener(new SnakeKeyAdapter(this));

        panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                drawGrid(g2d);
                drawCookie(g2d);

                updateLines(); //shouldnt be in repaint
                
                spentTail();

                for (SnakeLine line : lines) {

                    Color customSnakeColor = new Color(15, 100, 46);
                    g2d.setColor(customSnakeColor);
                    float lineWidth = 15f; // Width of the line
                    g2d.setStroke(new BasicStroke(lineWidth));

                    g2d.draw(line);

                }

            }
        };

        panel.setBackground(new Color(185, 218, 109));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // nearly there with this
    public void spentTail() {

        SnakeLine tailLine = lines.get(0);

        if (tailLine.calculateLength() <= 0) {

            lines.remove(0);

        }
    }

    public void drawCookie(Graphics2D g) {

        g.drawImage(this.cookie, this.cookieX, this.cookieY, 25, 25, this.panel);

    }

    public void randomiseCookie() {

        Random random = new Random();

        cookieX = Math.round(random.nextInt(451) / 25) * 25;

        cookieY = Math.round(random.nextInt(451) / 25) * 25;

    }

    public boolean checkBoundary() {

        SnakeLine head = lines.get(lines.size() - 1);

        if (head.getX2() + 12.5 > 500 || head.getX2() - 12.5 < 0) {

            return false;
        }

        if (head.getY2() + 12.5 > 500 || head.getY2() + 12.5 < 0) {
            return false;
        }

        return true;
    }

    public boolean checkCrossingTail() {

        SnakeLine head = lines.get(lines.size() - 1);
        double headX = head.getX2();
        
       
        double headY = head.getY2();
        

        for (int i = 0; i < lines.size()-1; i++) {

            SnakeLine line = lines.get(i);
            

            if (line.ptSegDist(headX, headY) == 0) { 
                
                if (!(head.getX1() == head.getX2() && head.getY1() == head.getY2())) {
  
                    
                    return false;
                }
            }
          

        }
        return true;
    }

    public void drawGrid(Graphics2D g) {

        g.setColor(new Color(119, 202, 47));
        g.setStroke(new BasicStroke(2.5f));

        for (int i = 0; i <= 500; i += 25) {

            g.drawLine(i, 0, i, 500);

        }

        for (int i = 0; i <= 500; i += 25) {

            g.drawLine(0, i, 500, i);

        }

    }

    public void updateLines() {

        SnakeLine tailLine = lines.get(0);
        SnakeLine headLine = lines.get(lines.size() - 1);

        double X1 = tailLine.getX1();
        double Y1 = tailLine.getY1();

        double X2 = headLine.getX2();
        double Y2 = headLine.getY2();

        // moving east
        if (tailLine.isMovingLaterally && tailLine.isDirectionEast) {

            X1 += snakeSteps;

        }

        // west
        else if (tailLine.isMovingLaterally && !tailLine.isDirectionEast) {
            X1 -= snakeSteps;
        }

        // up
        else if (!tailLine.isMovingLaterally && tailLine.isDirectionEast) {

            Y1 -= snakeSteps;

        }

        // down
        else if (!tailLine.isMovingLaterally && !tailLine.isDirectionEast) {

            Y1 += snakeSteps;

        }

        // headline

        if (headLine.isMovingLaterally && headLine.isDirectionEast) {

            X2 += snakeSteps;

        }

        else if (headLine.isMovingLaterally && !headLine.isDirectionEast) {

            X2 -= snakeSteps;
        }

        // up
        else if (!headLine.isMovingLaterally && headLine.isDirectionEast) {

            Y2 -= snakeSteps;

        }

        // down
        else if (!headLine.isMovingLaterally && !headLine.isDirectionEast) {

            Y2 += snakeSteps;

        }

        tailLine.setLine(X1, Y1, tailLine.getX2(), tailLine.getY2());

        headLine.setLine(headLine.getX1(), headLine.getY1(), X2, Y2);

        lines.set(0, tailLine);
        lines.set((lines.size() - 1), headLine);

    }

    public void startGame() {

        frame.setVisible(true);

        timer = new Timer(80, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBoundary() && checkCrossingTail()) {
                    frame.repaint();
                    if (checkCookie()) {
                        System.out.println("got cookie!");
                        cookieCounter++;
                        increaseTail();
                        randomiseCookie();

                    }
                } else {

                    timer.stop();
                    // game over
                    System.out.println("game over");
                    System.out.println("you got " + cookieCounter + " cookies!");
                }
            }

        });

        timer.start();
    }

    public boolean checkCookie() {

        SnakeLine head = lines.get(lines.size() - 1);

        Rectangle2D cookieRectangle = new Rectangle2D.Double(cookieX, cookieY, 25, 25);

        if (cookieRectangle.contains(head.getX2(), head.getY2())) {

            return true;
        }

        return false;
    }

    public void increaseTail() {

        SnakeLine tailLine = lines.get(0);
        double X1 = tailLine.getX1();
        double Y1 = tailLine.getY1();

        // moving east
        if (tailLine.isMovingLaterally && tailLine.isDirectionEast) {

            X1 -= snakeSteps;

        }

        // west
        else if (tailLine.isMovingLaterally && !tailLine.isDirectionEast) {
            X1 += snakeSteps;
        }

        // up
        else if (!tailLine.isMovingLaterally && tailLine.isDirectionEast) {

            Y1 += snakeSteps;

        }

        // down
        else if (!tailLine.isMovingLaterally && !tailLine.isDirectionEast) {

            Y1 -= snakeSteps;

        }

        tailLine.setLine(X1, Y1, tailLine.getX2(), tailLine.getY2());
        lines.set(0, tailLine);

    }
}