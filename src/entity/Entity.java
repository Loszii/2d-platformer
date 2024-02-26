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
    double xAcc;
    double yAcc;

    public Entity(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Entity(int width, int height, int x, int y, double xAcc) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.xAcc = xAcc;
    }

    //setters
    public void setX(int value) {
        x = value;
    }
    public void setY(int value) {
        y = value;
    }
    public void setYAcc(double value) {
        yAcc = value;
    }
    public void setXAcc(double value) {
        xAcc = value;
    }

    //getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getYAcc() {
        return yAcc;
    }
    public double getXAcc() {
        return xAcc;
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
