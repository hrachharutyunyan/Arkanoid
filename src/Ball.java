import java.awt.*;

public class Ball extends GameObject {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final double BALL_RADIUS = 10.0;
    public static final double BALL_VELOCITY = 0.6;
    public static final double FT_STEP = 1.0;

    double x, y;
    double radius = BALL_RADIUS;
    double velocityX = BALL_VELOCITY;
    double velocityY = BALL_VELOCITY;

    Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) left(), (int) top(), (int) radius * 2,
                (int) radius * 2);
    }

    void update(ScoreBoard scoreBoard, Paddle paddle) {
        x += velocityX * FT_STEP;
        y += velocityY * FT_STEP;

        if (left() < 0)
            velocityX = BALL_VELOCITY;
        else if (right() > SCREEN_WIDTH)
            velocityX = -BALL_VELOCITY;
        if (top() < 0) {
            velocityY = BALL_VELOCITY;
        } else if (bottom() > SCREEN_HEIGHT) {
            velocityY = -BALL_VELOCITY;
            x = paddle.x;
            y = paddle.y - 50;
            scoreBoard.die();
        }

    }

    double left() {
        return x - radius;
    }

    double right() {
        return x + radius;
    }

    double top() {
        return y - radius;
    }

    double bottom() {
        return y + radius;
    }

}