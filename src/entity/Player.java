package entity;
import main.GamePanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private int walkFrame = 0;
    private boolean facingRight = true;
    private boolean isGrounded = true;
    private double xAccOfGround = 0;
    private double maxXAcc = 12.5;
    private BufferedImage airRightImg;
    private BufferedImage airLeftImg;
    private BufferedImage idleRightImg;
    private BufferedImage idleLeftImg;
    private BufferedImage playerWalkR1;
    private BufferedImage playerWalkR2;
    private BufferedImage playerWalkR3;
    private BufferedImage playerWalkR4;
    private BufferedImage playerWalkL1;
    private BufferedImage playerWalkL2;
    private BufferedImage playerWalkL3;
    private BufferedImage playerWalkL4;

    public Player(int width, int height, int xPos, int yPos, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, xAcc, yAcc);
        airRightImg = importImg("/res/player/player_air_right.png");
        airLeftImg = importImg("/res/player/player_air_left.png");
        idleRightImg = importImg("/res/player/player_idle_right.png");
        idleLeftImg = importImg("/res/player/player_idle_left.png");
        playerWalkR1 = importImg("/res/player/player_walk1_right.png");
        playerWalkR2 = importImg("/res/player/player_walk2_right.png");
        playerWalkR3 = importImg("/res/player/player_walk3_right.png");
        playerWalkR4 = importImg("/res/player/player_walk4_right.png");
        playerWalkL1 = importImg("/res/player/player_walk1_left.png");
        playerWalkL2 = importImg("/res/player/player_walk2_left.png");
        playerWalkL3 = importImg("/res/player/player_walk3_left.png");
        playerWalkL4 = importImg("/res/player/player_walk4_left.png");
    }

    public boolean getGrounded() {
        return isGrounded;
    }

    public double getXAccOfGround() {
        return xAccOfGround;
    }

    public int getWalkFrame() {
        return walkFrame;
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

    public void setWalkFrame(int frame) {
        walkFrame = frame;
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
            if (getGrounded() && getXAcc() == getXAccOfGround()){
                g.drawImage(idleRightImg, getX(), getY(), null);
            } else if (!getGrounded()){
                g.drawImage(airRightImg, getX(), getY(), null);
            } else if (walkFrame == 0) {
                g.drawImage(playerWalkR1, getX(), getY(), null);
            } else if (walkFrame == 1) {
                g.drawImage(playerWalkR2, getX(), getY(), null);
            } else if (walkFrame == 2) {
                g.drawImage(playerWalkR3, getX(), getY(), null);
            } else if (walkFrame == 3) {
                g.drawImage(playerWalkR4, getX(), getY(), null);
            }
        } else if (!facingRight) {
            if (getGrounded() && getXAcc() == getXAccOfGround()){
                g.drawImage(idleLeftImg, getX(), getY(), null);
            } else if (!getGrounded()){
                g.drawImage(airLeftImg, getX(), getY(), null);
            } else if (walkFrame == 0) {
                g.drawImage(playerWalkL1, getX(), getY(), null);
            } else if (walkFrame == 1) {
                g.drawImage(playerWalkL2, getX(), getY(), null);
            } else if (walkFrame == 2) {
                g.drawImage(playerWalkL3, getX(), getY(), null);
            } else if (walkFrame == 3) {
                g.drawImage(playerWalkL4, getX(), getY(), null);
            }
        }
    }
}
