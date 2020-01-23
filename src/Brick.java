import java.awt.*;

public class Brick extends Rectangle {

    public static final double BLOCK_WIDTH = 60.0;
    public static final double BLOCK_HEIGHT = 20.0;
    boolean destroyed = false;

    Brick(double x, double y) {
        this.x = x;
        this.y = y;
        this.sizeX = BLOCK_WIDTH;
        this.sizeY = BLOCK_HEIGHT;
    }

    void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
    }
}