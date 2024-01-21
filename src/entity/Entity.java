package entity;
import main.GamePanel;
import main.Game;
import java.awt.Graphics;

public class Entity {
    public int width;
    public int height;
    public int xPos;
    public int yPos;
    public int xAcc = 0;
    public int yAcc = 5;
    public GamePanel gamePanel;

    public Entity(int width, int height, int xPos, int yPos, GamePanel gamePanel) {
        this.width = width;
        this.height = height;
        this.gamePanel = gamePanel;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void changeX(int value) {
        xPos += value;
    }

    public void changeY(int value) {
        yPos += value;
    }

    public void draw(Graphics g) {
        g.fillRect(xPos, yPos, width, height);
    }

    public void checkBounds() {
        if (yPos < 0) {
            yPos = 0;
        }
        if (xPos < 0) {
            xPos = 0;
        }
        if (yPos > Game.height - height) {
            yPos = Game.height - height;
        }
        if (xPos > Game.width - width) {
            xPos = Game.width - width;
        }
    }

}
