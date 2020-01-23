import java.awt.*;


public class Paddle extends Rectangle {
    public static final int SCREEN_WIDTH = 800;
    public static final double PADDLE_WIDTH = 150.0;
    public static final double PADDLE_HEIGHT = 30.0;
    public static final double PADDLE_VELOCITY = 0.6;
    public static final double FT_STEP = 2.0;


    double velocity = 0.0;

    public Paddle(double x, double y) {
        this.x = x;
        this.y = y;
        this.sizeX = PADDLE_WIDTH;
        this.sizeY = PADDLE_HEIGHT;
    }

    void update() {
        x += velocity * FT_STEP;
    }

    void stopMove() {
        velocity = 0.0;
    }

    void moveLeft() {
        if (left() > 0.0) {
            velocity = -PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    void moveRight() {
        if (right() < SCREEN_WIDTH) {
            velocity = PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) (left()), (int) (top()), (int) sizeX, (int) sizeY);
    }

}
