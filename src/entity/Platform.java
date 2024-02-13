package entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Platform extends Entity{

    private int startY;
    //private BufferedImage platImg;
    private BufferedImage carrotImg;
    private Carrot carrot;

    public Platform(int width, int height, int xPos, int yPos, double xAcc, double yAcc, Carrot carrot) {
        super(width, height, xPos, yPos, xAcc, yAcc);
        startY = yPos;
        this.carrot = carrot;
        if (carrot != null) {
            carrotImg = importImg("/res/item/carrot.png");
        }
        //platImg = importImg("/res/platform/" + Integer.toString(width) + "-grass.png");
    }

    public int getStartY() {
        return startY;
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public void setCarrot(Carrot carrot) {
        this.carrot = carrot;
    }

    //moves platforms side to side
    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

    }

    public void applyYAcc() {
        setY(getY() + (int) getYAcc());
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        if (carrot != null) {
            g.drawImage(carrotImg, (getX() + carrot.getX()), (getY() - carrot.getWidth()), null);
        }
    }

}
    
