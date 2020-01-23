import java.awt.*;

public class ScoreBoard {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int COUNT_BLOCKS_X = 11;
    public static final int COUNT_BLOCKS_Y = 4;
    public static final int PLAYER_LIVES = 5;
    private static final String FONT = "Arial";

    int score = 0;
    int lives = PLAYER_LIVES;
    boolean win = false;
    boolean gameOver = false;
    String text;

    Font font;

    ScoreBoard() {
        font = new Font(FONT, Font.PLAIN, 12);
        text = "Welcome to Arkanoid Java version";
    }

    void increaseScore() {
        score++;
        if (score == (COUNT_BLOCKS_X * COUNT_BLOCKS_Y)) {
            win = true;
            text = "You have won! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    void die() {
        lives--;
        if (lives == 0) {
            gameOver = true;
            text = "You have lost! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    void updateScoreboard() {
        text = "Score: " + score + "  Lives: " + lives;
    }

    void draw(Graphics g) {
        if (win || gameOver) {
            font = font.deriveFont(50f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int titleHeight = fontMetrics.getHeight();
            int lineNumber = 1;
            for (String line : text.split("\n")) {
                int titleLen = fontMetrics.stringWidth(line);
                g.drawString(line, (SCREEN_WIDTH / 2) - (titleLen / 2),
                        (SCREEN_HEIGHT / 4) + (titleHeight * lineNumber));
                lineNumber++;

            }
        } else {
            font = font.deriveFont(34f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int titleLen = fontMetrics.stringWidth(text);
            int titleHeight = fontMetrics.getHeight();
            g.drawString(text, (SCREEN_WIDTH / 2) - (titleLen / 2),
                    titleHeight + 5);

        }
    }

}