package entity;
import main.GamePanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private boolean facingRight = true;
    private boolean isGrounded = true;
    private double xAccOfGround = 0;
    private double maxXAcc = 12.5;
    private BufferedImage airRightImg;
    private BufferedImage airLeftImg;
    private BufferedImage walkImgRight;
    private BufferedImage walkImgLeft;

    public Player(int width, int height, int xPos, int yPos, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, xAcc, yAcc);
        airRightImg = importImg("/res/player_air_right.png");
        airLeftImg = importImg("/res/player_air_left.png");
        walkImgRight = importImg("/res/player_walk_right.png");
        walkImgLeft = importImg("/res/player_walk_left.png");
    }

    public boolean getGrounded() {
        return isGrounded;
    }

    public double getXAccOfGround() {
        return xAccOfGround;
    }

    public void setFacingRight (boolean status) {
        facingRight = status;
    }

    public void setGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

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

    public void draw(Graphics g) {
        if (facingRight) {
            if (getGrounded()){
                g.drawImage(walkImgRight, getX(), getY(), null);
            } else {
                g.drawImage(airRightImg, getX(), getY(), null);
            }
        } else if (!facingRight) {
            if (getGrounded()){
                g.drawImage(walkImgLeft, getX(), getY(), null);
            } else {
                g.drawImage(airLeftImg, getX(), getY(), null);
            }
        }
    }
}
