import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;

public class Arkanoid extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final double BALL_VELOCITY = 0.7;
    public static final double BLOCK_WIDTH = 60.0;
    public static final double BLOCK_HEIGHT = 20.0;
    public static final int COUNT_BLOCKS_X = 11;
    public static final int COUNT_BLOCKS_Y = 4;
    public static final int PLAYER_LIVES = 5;
    public static final double FT_SLICE = 1.0;
    private boolean tryAgain = false;
    private boolean running = false;

    private Paddle paddle = new Paddle(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT - 50);
    private Ball ball = new Ball(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    private List<Brick> bricks = new ArrayList<>();
    private ScoreBoard scoreboard = new ScoreBoard();

    private double lastFt;
    private double currentSlice;

    boolean isIntersecting(GameObject mA, GameObject mB) {
        return !(mA.right() >= mB.left()) || !(mA.left() <= mB.right())
                || !(mA.bottom() >= mB.top()) || !(mA.top() <= mB.bottom());
    }

    void testCollision(Paddle mPaddle, Ball mBall) {
        if (isIntersecting(mPaddle, mBall))
            return;
        mBall.velocityY = -BALL_VELOCITY;
        if (mBall.x < mPaddle.x)
            mBall.velocityX = -BALL_VELOCITY;
        else
            mBall.velocityX = BALL_VELOCITY;
    }

    void testCollision(Brick mBrick, Ball mBall, ScoreBoard scoreboard) {
        if (isIntersecting(mBrick, mBall))
            return;

        mBrick.destroyed = true;

        scoreboard.increaseScore();

        double overlapLeft = mBall.right() - mBrick.left();
        double overlapRight = mBrick.right() - mBall.left();
        double overlapTop = mBall.bottom() - mBrick.top();
        double overlapBottom = mBrick.bottom() - mBall.top();

        boolean ballFromLeft = overlapLeft < overlapRight;
        boolean ballFromTop = overlapTop < overlapBottom;

        double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
        double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

        if (minOverlapX < minOverlapY) {
            mBall.velocityX = ballFromLeft ? -BALL_VELOCITY : BALL_VELOCITY;
        } else {
            mBall.velocityY = ballFromTop ? -BALL_VELOCITY : BALL_VELOCITY;
        }
    }

    void initializeBricks(List<Brick> bricks) {
        bricks.clear();

        for (int iX = 0; iX < COUNT_BLOCKS_X; ++iX) {
            for (int iY = 0; iY < COUNT_BLOCKS_Y; ++iY) {
                bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                        (iY + 2) * (BLOCK_HEIGHT + 3) + 20));
            }
        }
    }

    public Arkanoid() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setVisible(true);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);

        this.createBufferStrategy(2);

        initializeBricks(bricks);

    }

    void run() {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = bf.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        running = true;

        while (running) {

            long time1 = System.currentTimeMillis();

            if (!scoreboard.gameOver && !scoreboard.win) {
                tryAgain = false;
                update();
                drawScene(ball, bricks, scoreboard);

                // to simulate low FPS
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                if (tryAgain) {
                    tryAgain = false;
                    initializeBricks(bricks);
                    scoreboard.lives = PLAYER_LIVES;
                    scoreboard.score = 0;
                    scoreboard.win = false;
                    scoreboard.gameOver = false;
                    scoreboard.updateScoreboard();
                    ball.x = SCREEN_WIDTH / 2.0;
                    ball.y = SCREEN_HEIGHT / 2.0;
                    paddle.x = SCREEN_WIDTH / 2.0;
                }
            }

            long time2 = System.currentTimeMillis();
            double elapsedTime = time2 - time1;

            lastFt = elapsedTime;

            double seconds = elapsedTime / 1000.0;
            if (seconds > 0.0) {
                double fps = 1.0 / seconds;
                this.setTitle("FPS: " + fps);
            }

        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }

    private void update() {

        currentSlice += lastFt;

        for (; currentSlice >= FT_SLICE; currentSlice -= FT_SLICE) {

            ball.update(scoreboard, paddle);
            paddle.update();
            testCollision(paddle, ball);

            Iterator<Brick> it = bricks.iterator();
            while (it.hasNext()) {
                Brick brick = it.next();
                testCollision(brick, ball, scoreboard);
                if (brick.destroyed) {
                    it.remove();
                }
            }

        }
    }

    private void drawScene(Ball ball, List<Brick> bricks, ScoreBoard scoreboard) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {

            g = bf.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());

            ball.draw(g);
            paddle.draw(g);
            for (Brick brick : bricks) {
                brick.draw(g);
            }
            scoreboard.draw(g);

        } finally {
            Objects.requireNonNull(g).dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            running = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            tryAgain = true;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                paddle.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                paddle.moveRight();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                paddle.stopMove();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    public static void main(String[] args) {
        new Arkanoid().run();
    }

}