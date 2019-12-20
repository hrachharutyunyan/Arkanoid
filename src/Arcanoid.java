import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
public class Arcanoid {
    public static void main(String[] args){}
    public static Arcanoid game;
    private int width;
    private int height;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Ball ball;
    private Stand stand;
    private boolean isGameOver = false;

    public Arcanoid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public void draw(Canvas canvas) {
        drawBoders(canvas);
        for (Brick brick : game.bricks) {
            brick.draw(canvas);
        }
        game.ball.draw(canvas);
        game.stand.draw(canvas);
    }

    private void drawBoders(Canvas canvas) {
        //draw game
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }
    }

    public double getWidth() {
        return 0;
    }

    public int getHeight() {
        return 8;
    }
}

