package entity;
import main.GamePanel;
import main.Game;
import java.awt.Graphics;

public class Entity {
    protected int width;
    protected int height;
    protected int xPos;
    protected int yPos;
    protected GamePanel gamePanel;
    protected double xAcc;
    protected double yAcc;

    public Entity(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gamePanel = gamePanel;
        this.xAcc = xAcc;
        this.yAcc = yAcc;
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
    public double getYAcc() {
        return yAcc;
    }
    public double getXAcc() {
        return xAcc;
    }
    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void draw(Graphics g) {
        g.fillRect(xPos, yPos, width, height);
    }

    //takes in position values and checks if they are in bounds
    public boolean inBounds(int xVal, int yVal) {
        if (xVal < 0) {
            return false;
        } else if (xVal > Game.width - width) {
            return false;
        }
        return true;
    }

}
