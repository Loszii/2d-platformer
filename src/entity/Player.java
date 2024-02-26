package entity;
import main.GamePanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    
    final double maxXAcc = 12.5;
    int walkFrame = 0;
    int animationCounter = 0;
    long whenAteCarrot;
    double xAccOfGround = 0;
    double jumpHeight = -15.0;
    boolean facingRight = true;
    boolean isGrounded = true;
    boolean ateCarrot = false;
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

    public Player(int width, int height, int x, int y) {
        super(width, height, x, y);
        xAcc = 0;
        yAcc = 0;

        importFrames();
    }

    private void importFrames() {
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
    public double getJumpHeight() {
        return jumpHeight;
    }
    public long getWhenAteCarrot() {
        return whenAteCarrot;
    }

    public void setFacingRight (boolean status) {
        facingRight = status;
    }
    public void setWalkFrame(int frame) {
        walkFrame = frame;
    }
    public void setAteCarrot(boolean status) {
        boolean pastStatus = ateCarrot;
        ateCarrot = status;
        if (status) {
            whenAteCarrot = System.currentTimeMillis();
        } else if (pastStatus){
            jumpHeight += 5.0;
        }
    }

    //moves player in x Dir
    public void applyXAcc() {
        if (!isGrounded) {
            xAccOfGround = 0;
        }
        if (xAcc != xAccOfGround) { //friction applied below
            //this removes flickering back and forth when reducing acceleration to norm
            if (xAcc - xAccOfGround >= 1) {
                if (isGrounded) {
                    xAcc -= 0.75;
                } else {
                    xAcc -= 0.7;
                }
            } else if (xAcc - xAccOfGround <= -1) {
                if (isGrounded) {
                    xAcc += 0.75;
                } else {
                    xAcc += 0.7;
                }
            } else {
                xAcc = xAccOfGround;
            }
        }

        x += (int) xAcc; //apply acceleration
    }

    //moves player in y Dir
    public void applyYAcc() {
        if (!isGrounded) {
            y += (int) yAcc;
            if ((yAcc != GamePanel.gravity)) {
                yAcc += 0.5;
            }
        }
    }

    //returns if players acc is within xAccOfGround +/- maxXAcc
    public boolean isWithinSpeed() {
        if (xAcc >= 0) {
            if (xAcc > xAccOfGround + maxXAcc) {
                return false;
            } else {
                return true;
            }
        } else {
            if (xAcc < xAccOfGround - maxXAcc) {
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
            jumpHeight += 5.0;
        }
    }

    //check when to update walk frame
    private boolean checkAnimationCounter() {
        int xDiff = (int) (xAcc - xAccOfGround);
        if ((xDiff > 0) && (animationCounter > 17 - xDiff)) {
            return true;
        } else if (animationCounter > 17 + xDiff) {
            return true;
        }
        return false;
    }

    public void changeWalkFrame() {
        //change walk frame
        if (xAcc != xAccOfGround && checkAnimationCounter()) {
            if (walkFrame < 3) {
                walkFrame += 1;
                animationCounter = 0;
            } else {
                walkFrame = 0;
            }
        } else if (xAcc == xAccOfGround){
            walkFrame = 0;
            animationCounter = 0;
        }
        animationCounter += 1;
    }

    //draws player
    public void draw(Graphics g) {
        if (facingRight) {
            if (isGrounded && xAcc == xAccOfGround){
                g.drawImage(idleRightImg, x, y, null);
            } else if (!isGrounded){
                g.drawImage(airRightImg, x, y, null);
            } else if (walkFrame == 0) {
                g.drawImage(playerWalkR1, x, y, null);
            } else if (walkFrame == 1) {
                g.drawImage(playerWalkR2, x, y, null);
            } else if (walkFrame == 2) {
                g.drawImage(playerWalkR3, x, y, null);
            } else if (walkFrame == 3) {
                g.drawImage(playerWalkR4, x, y, null);
            }
        } else if (!facingRight) {
            if (isGrounded && xAcc == xAccOfGround){
                g.drawImage(idleLeftImg, x, y, null);
            } else if (!isGrounded){
                g.drawImage(airLeftImg, x, y, null);
            } else if (walkFrame == 0) {
                g.drawImage(playerWalkL1, x, y, null);
            } else if (walkFrame == 1) {
                g.drawImage(playerWalkL2, x, y, null);
            } else if (walkFrame == 2) {
                g.drawImage(playerWalkL3, x, y, null);
            } else if (walkFrame == 3) {
                g.drawImage(playerWalkL4, x, y, null);
            }
        }
    }
}
