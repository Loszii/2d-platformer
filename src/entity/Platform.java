package entity;
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Entity{

    private int startY;
    private int borderSize = 1;

    public Platform(int width, int height, int xPos, int yPos, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, xAcc, yAcc);
        startY = yPos;
    }

    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

    }

    public int getStartY() {
        return startY;
    }

    public void draw(Graphics g) {
        g.setColor(new Color (255, 255, 255));
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        g.setColor(new Color (0, 0, 0));
        g.fillRect(getX() + borderSize, getY() + borderSize, getWidth() - 2 * borderSize, getHeight() - 2 * borderSize);
    }

}
    
