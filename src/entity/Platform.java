package entity;
import main.GamePanel;

public class Platform extends Entity{

    private int startY;

    public Platform(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, gamePanel, xAcc, yAcc);
        startY = yPos;
    }

    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

    }

    public int getStartY() {
        return startY;
    }

}
    
