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
import java.awt.Font;

//to do: have platforms have random y acceleration and flip when they collide with another platform

public class GamePanel extends JPanel { //inherits from JPanel

    public static final int gravity = 5;
    private Player mainPlayer;
    private Platform[] platforms;
    private Collision collision;
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private int score;
    private BufferedImage grass;
    private BufferedImage sky;
    private BufferedImage space;
    private int frameCounter = 0; //for walk animation 

    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height / 2 + 100, 0, gravity);
        grass = mainPlayer.importImg("/res/grass.jpg");
        sky = mainPlayer.importImg("/res/sky.jpg");
        space = mainPlayer.importImg("/res/space.jpg");
        platforms = generatePlats();
        collision = new Collision(mainPlayer, platforms);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable

        //background
        if (score / 100 < 100) {
            g.drawImage(grass, 0, 0, null);
        } else if (score / 100 < 200) {
            g.drawImage(sky, 0, 0, null);
        } else {
            g.drawImage(space, 0, 0, null);
        }

        if (wPressed) {
            if (mainPlayer.getGrounded()) {
                mainPlayer.setYAcc(-17.5);
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
            scrollScreen(gravity);
            score += gravity;
        } else if (mainPlayer.getY() > 3 * Game.height / 4){
            scrollScreen(-1 * gravity);
            score -= gravity;
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
        Platform[] plats = new Platform[100];
        Random rand = new Random();
        int yCounter = (Game.height / 2 + mainPlayer.getHeight());
        int maxPlatWidth = 400;
        int minPlatWidth = 200;
        int curWidth;
        plats[0] = new Platform(Game.width + 2000, 25, -1000, (Game.height / 2 + mainPlayer.getHeight()) + 100, 0, 0); //platform under player
        for (int i = 1; i < plats.length; i++) {
            curWidth = rand.nextInt(maxPlatWidth - minPlatWidth) + minPlatWidth;
            plats[i] = new Platform(curWidth, 25, rand.nextInt(Game.width - curWidth), yCounter, rand.nextInt(7), 0);
            yCounter -= rand.nextInt(200) + mainPlayer.getHeight() + 50;
        }
        return plats;
    }

    public void scrollScreen(int screenSpeed) {
        mainPlayer.setY(mainPlayer.getY() + screenSpeed);
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].setY(platforms[i].getY() + screenSpeed);
        }
    }

    public void scrollToStart() {
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].setY(platforms[i].getStartY());
        }
        mainPlayer.setY(Game.height / 2 + 100);
        mainPlayer.setX((Game.width - 50) / 2);
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
