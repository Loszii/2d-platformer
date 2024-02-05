package main;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import entity.Player;
import entity.Platform;
import entity.Collision;
import entity.Carrot;
import java.awt.Font;

//to do: make carrot entity for player to increase jump
//fix inputs sometimes not working

public class GamePanel extends JPanel { //inherits from JPanel

    public static double gravity = 9.0;
    private Player mainPlayer;
    private Platform[] platforms;
    private int[] platWidths = {200, 250, 300, 350, 400};
    private Collision collision;
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private int score;
    private BufferedImage sky;
    private BufferedImage space;
    private int frameCounter = 0; //for walk animation 

    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height - 350, 0, gravity);
        sky = mainPlayer.importImg("/res/background/sky.jpg");
        space = mainPlayer.importImg("/res/background/space.jpg");
        platforms = generatePlats();
        collision = new Collision(mainPlayer, platforms);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
    }

    public static void lowerGrav() {
        if (gravity > 3.0) {
            gravity -= 0.5;
        }
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable

        //background
        if (score / 100 < 500) {
            g.drawImage(sky, 0, 0, null);
        } else {
            g.drawImage(space, 0, 0, null);
        }

        if (wPressed) {
            if (mainPlayer.getGrounded()) {
                mainPlayer.setYAcc(mainPlayer.getJumpHeight());
            }
        }
        if (aPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setFacingRight(false);
            mainPlayer.setXAcc(mainPlayer.getXAcc() - 0.5);
        }
        if (dPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setFacingRight(true);
            mainPlayer.setXAcc(mainPlayer.getXAcc() + 0.5);
        }

        //collision
        collision.checkCol();

        //movement
        mainPlayer.applyXAcc();
        mainPlayer.applyYAcc();
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].applyXAcc();
            platforms[i].applyYAcc(); //remove later
        }

        //scrolling and score
        if (mainPlayer.getY() < Game.height / 2) {
            scrollScreen(Math.abs(((int) mainPlayer.getYAcc()))); //scroll screen of |yAcc| and change score by same amount
            score +=  Math.abs(((int) mainPlayer.getYAcc()));
        } else if (mainPlayer.getY() > 3 * Game.height / 4){
            scrollScreen(-1 * Math.abs(((int) mainPlayer.getYAcc())));
            score -=  Math.abs(((int) mainPlayer.getYAcc()));
        }

        //drawing
        mainPlayer.draw(g);
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].draw(g);
        }
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("font", 3, 50));
        g.drawString(String.valueOf(score / 100), Game.width /2 , 50);
        
        //change walk frame
        if (mainPlayer.getXAcc() != mainPlayer.getXAccOfGround() && checkFrameCounter()) {
            if (mainPlayer.getWalkFrame() < 3) {
                mainPlayer.setWalkFrame(mainPlayer.getWalkFrame() + 1);
                frameCounter = 0;
            } else {
                mainPlayer.setWalkFrame(0);
            }
        } else if (mainPlayer.getXAcc() == mainPlayer.getXAccOfGround()){
            mainPlayer.setWalkFrame(0);
            frameCounter = 0;
        }
        frameCounter += 1;
    }

    public void setWPressed(boolean bool) {
        wPressed = bool;
    }
    public void setAPressed(boolean bool) {
        aPressed = bool;
    }
    public void setDPressed(boolean bool) {
        dPressed = bool;
    }

    //generates a bunch of random platforms in the sky
    public Platform[] generatePlats() {
        Platform[] plats = new Platform[500];
        Random rand = new Random();
        int yCounter = Game.height - 400;
        int curWidth;
        Carrot carrot;
        plats[0] = new Platform(2120, 300, -100, Game.height - 300, 0, 0, null); //platform under player
        for (int i = 1; i < plats.length; i++) {
            curWidth = platWidths[rand.nextInt(platWidths.length)];
            if ((i - 1) % 25 - (i / 50) == 0) { //every 25th plat has carrot, as spacing bigger needs to be more often
                carrot = new Carrot(25, 25, rand.nextInt(curWidth - 25));
            } else {
                carrot = null;
            }
            plats[i] = new Platform(curWidth, 25, rand.nextInt(Game.width - curWidth), yCounter, rand.nextInt(7), 0, carrot);
            yCounter -= 50 + rand.nextInt(100) + (5 * i); //gets farther as go up
        }
        return plats;
    }

    public void scrollScreen(int screenSpeed) {
        mainPlayer.setY(mainPlayer.getY() + screenSpeed);
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].setY(platforms[i].getY() + screenSpeed);
        }
    }

    public boolean checkFrameCounter() {
        int xDiff = (int) (mainPlayer.getXAcc() - mainPlayer.getXAccOfGround());
        if (xDiff > 0 && frameCounter > 17 - xDiff) {
            return true;
        } else if (frameCounter > 17 + xDiff) {
            return true;
        }
        return false;
    }

}
