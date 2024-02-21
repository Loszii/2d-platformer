package entity;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Snake extends Entity {

    private int slideFrame = 0;
    private int animationCounter = 0;
    private int animationSpeed; //lower faster
    private Platform groundPlat;
    private BufferedImage snakeWalkR1;
    private BufferedImage snakeWalkR2;
    private BufferedImage snakeWalkL1;
    private BufferedImage snakeWalkL2;
    private boolean facingRight = true;

    public Snake(int width, int height, int xPos, int yPos, double xAcc) {
        super(width, height, xPos, yPos, xAcc);
        animationSpeed = (int) (25 - (5 * xAcc));

        snakeWalkR1 = importImg("/res/snake/snake_walk1_right.png");
        snakeWalkR2 = importImg("/res/snake/snake_walk2_right.png");
        snakeWalkL1 = importImg("/res/snake/snake_walk1_left.png");
        snakeWalkL2 = importImg("/res/snake/snake_walk2_left.png");
    }

    public void setGroundPlat(Platform plat) {
        groundPlat = plat;
    }

    public void applyXAcc() {
        checkEdge();
        setX(getX() + (int) getXAcc());
    }

    private void checkEdge() {
        if (getX() + getWidth() >= groundPlat.getX() + groundPlat.getWidth()) {
            setXAcc(getXAcc() * -1);
            facingRight = !facingRight;
        } else if (getX() <= groundPlat.getX()) {
            setXAcc(getXAcc() * -1);
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
            g.drawImage(snakeWalkR1, getX(), getY(), null);
        } else if (slideFrame == 1 && facingRight) {
            g.drawImage(snakeWalkR2, getX(), getY(), null);
        } else if (slideFrame == 0) {
            g.drawImage(snakeWalkL1, getX(), getY(), null);
        } else {
            g.drawImage(snakeWalkL2, getX(), getY(), null);
        }
    }

}
