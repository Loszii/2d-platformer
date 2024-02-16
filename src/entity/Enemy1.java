package entity;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Enemy1 extends Entity {

    private Platform groundPlat;

    public Enemy1(int width, int height, int xPos, int yPos, double xAcc) {
        super(width, height, xPos, yPos, xAcc);
    }

    public void setGroundPlat(Platform plat) {
        groundPlat = plat;
    }

    private void checkEdge() {
        if (getX() + getWidth() >= groundPlat.getX() + groundPlat.getWidth()) {
            setXAcc(getXAcc() * -1);
        } else if (getX() <= groundPlat.getX()) {
            setXAcc(getXAcc() * -1);
        }
    }

    public void applyXAcc() {
        checkEdge();
        setX(getX() + (int) getXAcc());
    }

    public void draw(Graphics g) {
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
