package entity;
import main.GamePanel;

public class Player extends Entity{

    private boolean topOpen = true;
    private boolean rightOpen = true;
    private boolean leftOpen = true;
    private boolean botOpen = false;
    private boolean isGrounded = true;
    private int yAcc = 0;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel) {
        super(width, height, xPos, yPos, gamePanel);
    }

    public int getYAcc() {
        return yAcc;
    }
    public void setYAcc(int value) {
        yAcc = value;
    }

    public boolean getGrounded() {
        return isGrounded;
    }

    public void applyYAcc(Platform[] platforms) {
        setY(yPos + yAcc, platforms);
        if (yAcc != GamePanel.gravity) {
            yAcc += 1;
        }
    }

    public void setX(int value, Platform[] platforms) {
        if (inBounds(value, yPos)) {
            if (value > xPos) {
                rightOpen = true;
                for (int i = 0; i < platforms.length; i++) {
                    if (isRightBlocked(value, platforms[i]) && (rightOpen == true)) {
                        rightOpen = false;
                        xPos = platforms[i].getX() - width;
                    }
                }
                if (rightOpen) {
                    setX(value);
                }


            } else {
                leftOpen = true;
                for (int i = 0; i < platforms.length; i++) {
                    if (isLeftBlocked(value, platforms[i]) && (leftOpen == true)) {
                        leftOpen = false;
                        xPos = platforms[i].getX() + platforms[i].getWidth();
                    }
                }
                if (leftOpen) {
                    setX(value);
                }
            }
        }
    }
    public void setY(int value, Platform[] platforms) {
        isGrounded = false;
        if (inBounds(xPos, value)) {
            if (value > yPos) {
                botOpen = true;
                for (int i = 0; i < platforms.length; i++) {
                    if (isBotBlocked(value, platforms[i]) && (botOpen == true)) {
                        botOpen = false;
                        isGrounded = true;
                        yPos = platforms[i].getY() - height;
                    }
                }
                if (botOpen) {
                    setY(value);
                }
            } else {
                topOpen = true;
                for (int i = 0; i < platforms.length; i++) {
                    if (isTopBlocked(value, platforms[i]) && (topOpen == true)) {
                        topOpen = false;
                        yPos = platforms[i].getY() + platforms[i].getHeight();
                        yAcc = 0;
                    }
                }
                if (topOpen) {
                    setY(value); //original y func with one parameter
                }
            }
        } else if (yPos > 500){ //if out of bounds and y value is near bottom, set grounded
            isGrounded = true;
        }
    }

    //collision detections
    //these functions take in a new coord position and a platform and check player can move to val despite given platform
    private boolean isTopBlocked(int yVal, Platform platform) {
        if (yPos < (platform.getY() + platform.height) || xPos > (platform.getX() + platform.getWidth()) || (xPos + width) < platform.getX()) {
            return false;
        } else if (yVal < (platform.getY() + platform.getHeight())) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isRightBlocked(int xVal, Platform platform) {
        if ((xPos + width) > platform.getX() || yPos > (platform.getY() + platform.getHeight()) || (yPos + height) < platform.getY()){
            return false;
        } else if ((xVal + width) > platform.getX()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isLeftBlocked(int xVal, Platform platform) {
        if (xPos < (platform.getX() + platform.getWidth()) || yPos > (platform.getY() + platform.getHeight()) || (yPos + height) < platform.getY()){
            return false;
        } else if (xVal < (platform.getX() + platform.getWidth())) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isBotBlocked(int yVal, Platform platform) {
        if ((yPos + height) > platform.getY() || xPos > (platform.getX() + platform.getWidth()) || (xPos + width) < platform.getX()) {
            return false;
        } else if ((yVal + height) > platform.getY()) {
            return true;
        } else {
            return false;
        }
    }

}
