package entity;
import main.Game;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Entity {
    int width;
    int height;
    int x;
    int y;
    double xVel;
    double yVel;

    public Entity(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Entity(int width, int height, int x, int y, double xVel) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.xVel = xVel;
    }

    //setters
    public void setX(int value) {
        x = value;
    }
    public void setY(int value) {
        y = value;
    }
    public void setYVel(double value) {
        yVel = value;
    }
    public void setXVel(double value) {
        xVel = value;
    }

    //getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getYVel() {
        return yVel;
    }
    public double getXVel() {
        return xVel;
    }

    //takes in position values and checks if they are in bounds
    public boolean inBounds() {
        if (x < 0) {
            return false;
        } else if (x > Game.WIDTH - width) {
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
