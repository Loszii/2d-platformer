package entity;
import main.GamePanel;
import main.Game;

public class Player extends Entity{
    public boolean isGrounded = true;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel) {
        super(width, height, xPos, yPos, gamePanel);
    }

    public void movePlayer() {
        yAcceleration();
        if (gamePanel.wPressed && isGrounded) {
            yAcc = -20;
        }
        if (gamePanel.aPressed) {
            changeX(-10);
        }
        if (gamePanel.sPressed) {
            changeY(10);
        }
        if (gamePanel.dPressed) {
            changeX(10);
        }
    }

    private void yAcceleration() {
        if (!isGrounded || yAcc != gamePanel.defaultGrav) {
            yPos += yAcc;
        }
        if (yAcc != gamePanel.defaultGrav) {
            yAcc += 1; //slowly lose upwards momentum
        }
    }

    @Override
    public void checkBounds() { //edge of screen bounds
        if (yPos < 0) {
            yPos = 0;
        }
        if (xPos < 0) {
            xPos = 0;
        }
        if (yPos > Game.height - height) {
            yPos = Game.height - height;
            isGrounded = true;
        } else {
            isGrounded = false;
        }
        if (xPos > Game.width - width) {
            xPos = Game.width - width;
        }
    }

}
