package entity;
import main.GamePanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    //make all static, only one player
    private int walkFrame = 0;
    private int animationCounter = 0;
    private long whenAteCarrot;
    private double xAccOfGround = 0;
    private double maxXAcc = 12.5;
    private double jumpHeight = -15.0;
    private boolean facingRight = true;
    private boolean isGrounded = true;
    private boolean ateCarrot = false;
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
        //imgs
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

    public boolean getAteCarrot() {
        return ateCarrot;
    }

    public int getWalkFrame() {
        return walkFrame;
    }

    public double getXAccOfGround() {
        return xAccOfGround;
    }

    public double getJumpHeight() {
        return jumpHeight;
    }

    public long getWhenAteCarrot() {
        return whenAteCarrot;
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

    public void setJumpHeight(double height) {
        jumpHeight = height;
    }

    public void setAteCarrot(boolean status){
        ateCarrot = status;
    }

    public void setWhenAteCarrot(long time) {
        whenAteCarrot = time;
    }

    //moves player in x Dir
    public void applyXAcc() {
        if (!isGrounded) {
            setXAccOfGround(0);
        }
        if (getXAcc() != xAccOfGround) { //friction applied below
            //this removes flickering back and forth when reducing acceleration to norm
            if (getXAcc() - xAccOfGround >= 1) {
                if (isGrounded) {
                    setXAcc(getXAcc() - 0.75); //ground has more friction
                } else {
                    setXAcc(getXAcc() - 0.7);
                }
            } else if (getXAcc() - xAccOfGround <= -1) {
                if (isGrounded) {
                    setXAcc(getXAcc() + 0.75);
                } else {
                    setXAcc(getXAcc() + 0.7);
                }
            } else {
                setXAcc(xAccOfGround);
            }
        }

        setX(getX() + (int) getXAcc()); //apply acceleration
    }

    //moves player in y Dir
    public void applyYAcc() {
        if (!getGrounded()) {
            setY(getY() + (int) getYAcc());
            if ((getYAcc() != GamePanel.getGravity())) {
                setYAcc(getYAcc() + 0.5);
            }
        }
    }

    //returns if players acc is within xAccOfGround +/- maxXAcc
    public boolean isWithinSpeed() {
        if (getXAcc() >= 0) {
            if (getXAcc() > xAccOfGround + maxXAcc) {
                return false;
            } else {
                return true;
            }
        } else {
            if (getXAcc() < xAccOfGround - maxXAcc) {
                return false;
            } else {
                return true;
            }
        }
    }

    //if has been 10 seconds since ate carrot, reset jump
    public void checkCarrotRanOut() {
        if (System.currentTimeMillis() - whenAteCarrot > 10000) {
            ateCarrot = false;
            setJumpHeight(getJumpHeight() + 5.0);
        }
    }

    //check when to update walk frame
    private boolean checkAnimationCounter() {
        int xDiff = (int) (getXAcc() - getXAccOfGround());
        if (xDiff > 0 && animationCounter > 17 - xDiff) {
            return true;
        } else if (animationCounter > 17 + xDiff) {
            return true;
        }
        return false;
    }

    public void changeWalkFrame() {
        //change walk frame
        if (getXAcc() != getXAccOfGround() && checkAnimationCounter()) {
            if (getWalkFrame() < 3) {
                setWalkFrame(getWalkFrame() + 1);
                animationCounter = 0;
            } else {
                setWalkFrame(0);
            }
        } else if (getXAcc() == getXAccOfGround()){
            setWalkFrame(0);
            animationCounter = 0;
        }
        animationCounter += 1;
    }

    //draws player
    public void draw(Graphics g) {
        if (facingRight) {
            if (getGrounded() && getXAcc() == xAccOfGround){
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
