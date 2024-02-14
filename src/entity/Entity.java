package entity;
import main.Game;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Entity {
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private double xAcc;
    private double yAcc;

    public Entity(int width, int height, int xPos, int yPos, double xAcc, double yAcc) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xAcc = xAcc;
        this.yAcc = yAcc;
    }

    public Entity(int width, int height, int xPos) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
    }

    //setters
    public void setX(int value) {
        xPos = value;
    }
    public void setY(int value) {
        yPos = value;
    }
    public void setYAcc(double value) {
        yAcc = value;
    }
    public void setXAcc(double value) {
        xAcc = value;
    }

    //getters
    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    public double getYAcc() {
        return yAcc;
    }
    public double getXAcc() {
        return xAcc;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    //takes in position values and checks if they are in bounds
    public boolean inBounds(int xVal, int yVal) {
        if (xVal < 0) {
            return false;
        } else if (xVal > Game.WIDTH - width) {
            return false;
        }
        return true;
    }

    //imports an image given String filepath
    public BufferedImage importImg(String filePath) {
        InputStream is = getClass().getResourceAsStream(filePath);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            return new BufferedImage(0, 0, 0); //if dont work just feed random img lol
        }
    }

}
