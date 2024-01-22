package entity;
import main.GamePanel;
import main.Game;

public class Player extends Entity{
    public boolean isGrounded = true;
    private boolean rightBlocked = false;
    private boolean leftBlocked = false;
    private boolean topBlocked = false;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel) {
        super(width, height, xPos, yPos, gamePanel);
    }

    public void movePlayer(Platform[] platforms) {
        leftBlocked = false;
        rightBlocked = false;
        topBlocked = false;
        checkGrounded();

        for (int i = 0; i < platforms.length; i++) {
            if (!leftBlocked) {
                if (xPos != platforms[i].xPos + platforms[i].width || yPos > platforms[i].yPos + platforms[i].height || yPos + height < platforms[i].yPos) {
                    leftBlocked = false; //if sides arent touching or the player is over/under platform then move
                } else {
                    leftBlocked = true;
                }
            }
            if (!rightBlocked) {
                if (xPos + width != platforms[i].xPos || yPos > platforms[i].yPos + platforms[i].height || yPos + height < platforms[i].yPos) {
                    rightBlocked = false;
                } else {
                    rightBlocked = true;
                }
            }
        }
        yAcceleration(platforms);

        if (!rightBlocked && gamePanel.dPressed) {
            changeX(5);
        } else if (!leftBlocked && gamePanel.aPressed) {
            changeX(-5);
        }

        if (gamePanel.wPressed && isGrounded) {
            yAcc = -20; //jump
        }
    }

    private void yAcceleration(Platform[] platforms) {
        for (int i = 0; i < platforms.length; i++) {
            if (yPos + height == platforms[i].yPos && xPos + width > platforms[i].xPos && xPos < platforms[i].xPos + platforms[i].width) {
                isGrounded = true;
            }
            if (yPos <= platforms[i].yPos + platforms[i].height && xPos + width > platforms[i].xPos && xPos < platforms[i].xPos + platforms[i].width && yPos > platforms[i].yPos) {
                topBlocked = true; //dont move up if platform
            }
        }
        if (yAcc < 0 && topBlocked) { //if moving up and top is blocked
            yAcc = 0;
        } else if (!isGrounded || yAcc != gamePanel.defaultGrav) {
            yPos += yAcc;
        }
        if (yAcc != gamePanel.defaultGrav) {
            yAcc += 1; //slowly lose upwards momentum
        }
    }

    private void checkGrounded() {
        if (yPos + height == Game.height) {
            isGrounded = true;
        } else {
            isGrounded = false;
        }
    }

}
