package main;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import entity.Player;
import entity.Platform;
import entity.Collision;
import java.awt.Font;

//make platforms teleport from one side to the other and move, as well as game scroll upwards

public class GamePanel extends JPanel { //inherits from JPanel

    public static final int gravity = 5;
    private Player mainPlayer;
    private Platform[] platforms;
    private Collision collision;
    //make platform array of on screen ones to fasten collission detection
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private int screenSpeed = 1;
    private long startTime = System.currentTimeMillis();
    private long currentTime;
    private long score;


    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height / 2 + 100, this, 0, gravity);
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

        if (wPressed) {
            if (mainPlayer.getGrounded()) {
                mainPlayer.setYAcc(-17.5);
            }
        }
        if (aPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setXAcc(mainPlayer.getXAcc() - 0.5);
        }
        if (dPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setXAcc(mainPlayer.getXAcc() + 0.5);
        }

        //collision
        if (collision.isTopBlocked()) {
            mainPlayer.setYAcc(0);
        }
        if (collision.isBotBlocked()) {
            mainPlayer.setYAcc(0);
            mainPlayer.setGrounded(true);
        } else {
            mainPlayer.setGrounded(false);
        }
        if (collision.isRightBlocked() || collision.isLeftBlocked()) {
            mainPlayer.setXAcc(0);
        }
        collision.checkPlayerToLeft();
        collision.checkPlayerToRight();
        collision.checkOutOfBounds();

        //movement
        mainPlayer.applyXAcc();
        mainPlayer.applyYAcc();
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].applyXAcc();
        }

        //drawing
        g.setColor(new Color(0, 0, 0));
        scrollScreen();

        mainPlayer.draw(g);
        g.setColor(new Color(255, 0, 0));
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].draw(g);
        }
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("font", 3, 50));
        g.drawString(String.valueOf(score), Game.width /2 , 50);
        currentTime = System.currentTimeMillis();
        score = (currentTime - startTime) / 1000;

        //game over
        if (mainPlayer.getY() > Game.height + 150) {
            scrollToStart();
            startTime = System.currentTimeMillis();
        }
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

    public Platform[] generatePlats() {
        Platform[] plats = new Platform[1000];
        Random rand = new Random();
        int yCounter = (Game.height / 2 + mainPlayer.getHeight());
        int maxPlatWidth = 400;
        int minPlatWidth = 200;
        int curWidth;
        plats[0] = new Platform(Game.width + 2000, 25, -1000, (Game.height / 2 + mainPlayer.getHeight()) + 100, this, 0, 0); //platform under player
        for (int i = 1; i < plats.length; i++) {
            curWidth = rand.nextInt(maxPlatWidth - minPlatWidth) + minPlatWidth;
            plats[i] = new Platform(curWidth, 25, rand.nextInt(Game.width - curWidth), yCounter, this, rand.nextInt(7), 0);
            yCounter -= rand.nextInt(200) + mainPlayer.getHeight() + 50;
        }
        return plats;
    }

    public void scrollScreen() {
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

}
