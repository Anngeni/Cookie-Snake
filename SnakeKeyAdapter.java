
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeKeyAdapter extends KeyAdapter {

    private Snake snake;

    // try to prevent two keys pressed at the same time

    public SnakeKeyAdapter(Snake snake) {

        this.snake = snake;

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        ArrayList<SnakeLine> lines = snake.lines;
        SnakeLine head = lines.get(lines.size() - 1);

        double startX = head.getX2(); 
        double startY = head.getY2();

        boolean lateral = head.isMovingLaterally;

        if (keyCode == KeyEvent.VK_UP) {

            // headline

            if (lateral) {
                SnakeLine newLine = new SnakeLine(false, true, startX, startY, startX, startY);

                snake.lines.add(newLine); 

            }

        }

        else if (keyCode == KeyEvent.VK_DOWN) {

            if (lateral) {

                SnakeLine newLine = new SnakeLine(false, false, startX, startY, startX, startY);

                snake.lines.add(newLine);

            }

        }

        else if (keyCode == KeyEvent.VK_RIGHT) {

            if (!lateral) {

                SnakeLine newLine = new SnakeLine(true, true, startX, startY, startX, startY);

                snake.lines.add(newLine);

            }
        }

        else if (keyCode == KeyEvent.VK_LEFT) {

            if (!lateral) {

                SnakeLine newLine = new SnakeLine(true, false, startX, startY, startX, startY);

                snake.lines.add(newLine);

            }

        }

    }

}
