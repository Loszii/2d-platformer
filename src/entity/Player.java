package entity;
import main.GamePanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class Player extends Entity{

    private boolean isGrounded = true;
    private double xAccOfGround = 0;
    private double maxXAcc = 12.5;
    private BufferedImage normImg;
    private BufferedImage airImg;
    private BufferedImage walkImgRight;
    private BufferedImage walkImgLeft;

    public Player(int width, int height, int xPos, int yPos, GamePanel gamePanel, double xAcc, double yAcc) {
        super(width, height, xPos, yPos, gamePanel, xAcc, yAcc);
        normImg = importImg("/res/player.png");
        airImg = importImg("/res/player_air.png");
        walkImgRight = importImg("/res/player_walk_right.png");
        walkImgLeft = importImg("/res/player_walk_left.png");
    }

    public BufferedImage importImg(String filePath) {
        InputStream is = getClass().getResourceAsStream(filePath);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            return new BufferedImage(0, 0, 0); //if dont work just feed random img lol
        }

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

    public void draw(Graphics g) {
        if (!getGrounded()) {
            g.drawImage(airImg, getX(), getY(), null);
        }  else if (getXAcc() > getXAccOfGround()) {
            g.drawImage(walkImgRight, getX(), getY(), null);
        } else if (getXAcc() < getXAccOfGround()) {
            g.drawImage(walkImgLeft, getX(), getY(), null);
        } else {
            g.drawImage(normImg, getX(), getY(), null);
        }
    }
}
