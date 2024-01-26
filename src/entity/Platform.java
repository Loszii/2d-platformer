package entity;
import main.GamePanel;

public class Platform extends Entity{

    public Platform(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, gamePanel, xAcc, yAcc);
    }

    public void applyXAcc() {

        if (!inBounds(xPos, yPos)) {
            xAcc *= -1;
        }
        setX(xPos + (int) xAcc);

    }

    //integrate collission detection in here aswell for moving side to side

}
    
