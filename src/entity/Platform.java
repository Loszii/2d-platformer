package entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Platform extends Entity{

    private int startY;
    private BufferedImage platImg;

    public Platform(int width, int height, int xPos, int yPos, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, xAcc, yAcc);
        startY = yPos;
        platImg = importImg("/res/platform/" + Integer.toString(width) + "-grass.png");
    }

    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

    }

    public void applyYAcc() {
        setY(getY() + (int) getYAcc());
    }

    public int getStartY() {
        return startY;
    }

    public void draw(Graphics g) {
        g.drawImage(platImg, getX(), getY(), null);
    }

}
    
