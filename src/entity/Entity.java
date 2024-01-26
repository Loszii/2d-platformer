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

    public Entity(int width, int height, int xPos, int yPos, GamePanel gamePanel) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gamePanel = gamePanel;
    }

    public void setX(int value) {
        xPos = value;
    }
    public void setY(int value) {
        yPos = value;
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
        if (yVal < 0) {
            return false;
        } else if (yVal > Game.height - height) {
            return false;
        }
        if (xVal < 0) {
            return false;
        } else if (xVal > Game.width - width) {
            return false;
        }
        return true;
    }

}
