package entity;
import main.GamePanel;

public class Player extends Entity{

    private boolean isGrounded = true;
    private double xAccOfGround = 0;
    private double maxXAcc = 12.5;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, gamePanel, xAcc, yAcc);
    }

    public boolean getGrounded() {
        return isGrounded;
    }

    public double getXAccOfGround() {
        return xAccOfGround;
    }

    public void setGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

    //make this work when back
    public void setXAccOfGround(double acc) {
        xAccOfGround = acc;
    }

    public void applyXAcc() {
        if (!isGrounded) {
            setXAccOfGround(0);
        }
        setX(getX() + (int) getXAcc()); //apply acceleration
        if (getXAcc() != xAccOfGround) {
            if (getXAcc() > xAccOfGround) {
                setXAcc(getXAcc() - 0.25);
            } else {
                setXAcc(getXAcc() + 0.25); //applying friction
            }
        }
    }

    public void applyYAcc() {
        setY(getY() + (int) getYAcc());
        if (getYAcc() != GamePanel.gravity) {
            setYAcc(getYAcc() + 0.5);
        }
    }

    //returns if players acc is within xAccOfGround +/- maxXAcc
    public boolean isWithinSpeed() {
        if (getXAcc() >= 0) {
            if (getXAcc() > getXAccOfGround() + maxXAcc) {
                return false;
            } else {
                return true;
            }
        } else {
            if (getXAcc() < getXAccOfGround() - maxXAcc) {
                return false;
            } else {
                return true;
            }
        }
    }

}
