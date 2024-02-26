package entity;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Snake extends Entity {

    int slideFrame = 0;
    int animationCounter = 0;
    int animationSpeed; //lower faster
    Platform groundPlat;
    boolean facingRight = true;
    private BufferedImage snakeWalkR1;
    private BufferedImage snakeWalkR2;
    private BufferedImage snakeWalkL1;
    private BufferedImage snakeWalkL2;

    public Snake(int width, int height, int x, int y, double xAcc) {
        super(width, height, x, y, xAcc);
        animationSpeed = (int) (25 - (5 * xAcc));

        importFrames();
    }

    private void importFrames() {
        snakeWalkR1 = importImg("/res/snake/snake_walk1_right.png");
        snakeWalkR2 = importImg("/res/snake/snake_walk2_right.png");
        snakeWalkL1 = importImg("/res/snake/snake_walk1_left.png");
        snakeWalkL2 = importImg("/res/snake/snake_walk2_left.png");
    }

    public void applyXAcc() {
        checkEdge();
        x += (int) xAcc;
    }

    private void checkEdge() {
        if (x + width >= groundPlat.x + groundPlat.width) {
            xAcc *= -1;
            facingRight = !facingRight;
        } else if (x <= groundPlat.x) {
            xAcc *= -1;
            facingRight = !facingRight;
        }
    }

    private boolean checkAnimationCounter() {
        if (animationCounter > animationSpeed) {
            return true;
        } else {
            return false;
        }
    }

    public void changeWalkFrame() {
        if (checkAnimationCounter()) {
            if (slideFrame == 0) {
                slideFrame = 1;
                animationCounter = 0;
            } else {
                slideFrame = 0;
                animationCounter = 0;
            }
        }
        animationCounter += 1;
    }

    public void draw(Graphics g) {
        if (slideFrame == 0 && facingRight) {
            g.drawImage(snakeWalkR1, x, y, null);
        } else if (slideFrame == 1 && facingRight) {
            g.drawImage(snakeWalkR2, x, y, null);
        } else if (slideFrame == 0) {
            g.drawImage(snakeWalkL1, x, y, null);
        } else {
            g.drawImage(snakeWalkL2, x, y, null);
        }
    }

}
