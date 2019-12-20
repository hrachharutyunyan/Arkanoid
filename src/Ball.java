public class Ball extends BaseObject
{

    private double speed;
    private double direction;
    private double dx;
    private double dy;

    private boolean isFrozen;

    public Ball(double x, double y, double speed, double direction)
    {
        super(x, y, 1);

        this.direction = direction;
        this.speed = speed;

        this.isFrozen = true;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public double getDirection()
    {
        return direction;
    }

    public double getDx()
    {
        return dx;
    }

    public double getDy()
    {
        return dy;
    }

    public void setDirection(double direction)
    {
        this.direction = direction;

        double angel = Math.toRadians(direction);
        dx = Math.cos(angel) * speed;
        dy = -Math.sin(angel) * speed;
    }
    @Override
    public void draw(Canvas canvas)
    {
        canvas.setPoint(x, y, 'O');
    }
    public void move()
    {
        if (isFrozen) return;

        x += dx;
        y += dy;

        checkRebound(1, (int) Arcanoid.game.getWidth(), 1, Arcanoid.game.getHeight() + 5);
    }
    public void checkRebound(int minx, int maxx, int miny, int maxy)
    {
        if (x < minx)
        {
            x = minx + (minx - x);
            dx = -dx;
        }

        if (x > maxx)
        {
            x = maxx - (x - maxx);
            dx = -dx;
        }

        if (y < miny)
        {
            y = miny + (miny - y);
            dy = -dy;
        }

        if (y > maxy)
        {
            y = maxy - (y - maxy);
            dy = -dy;
        }
    }
    public void start()
    {
        this.setDirection(direction);
        this.isFrozen = false;
    }
}
