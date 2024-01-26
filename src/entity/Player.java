package entity;
import main.GamePanel;

public class Player extends Entity{

    private boolean topOpen = true;
    private boolean rightOpen = true;
    private boolean leftOpen = true;
    private boolean botOpen = false;
    private boolean isGrounded = true;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, gamePanel, xAcc, yAcc);
    }

    public boolean getGrounded() {
        return isGrounded;
    }

    public void setGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

    public void applyXAcc() {
        setX(xPos + (int) xAcc);
        if (xAcc != 0) {
            if (xAcc > 0) {
                xAcc -= 0.1;
            } else {
                xAcc += 0.1;
            }
        }
    }

    public void applyYAcc() {
        setY(yPos + (int) yAcc);
        if (yAcc != GamePanel.gravity) {
            yAcc += 0.5;
        }
    }

}
