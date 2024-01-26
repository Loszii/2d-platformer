package main;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import entity.Player;
import entity.Platform;
import entity.Entity;
import entity.Collision;

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


    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height / 2 + 100, this, 0, gravity);
        platforms = new Platform[2];
        platforms[0] = new Platform(Game.width, 25, 0, (Game.height / 2 + mainPlayer.getHeight()) + 100, this, 0, 0); //platform under player

        
        platforms[1] = new Platform(100, 25, 200, (Game.height / 2 + mainPlayer.getHeight()) - 100, this, 5, 0);

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
                mainPlayer.setYAcc(-15);
            }
        }
        if (aPressed) {
            mainPlayer.setXAcc(-5);
        }
        if (dPressed) {
            mainPlayer.setXAcc(5);
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


        //movement
        mainPlayer.applyXAcc();
        mainPlayer.applyYAcc();
        
        //make collisions work with moving plat
        platforms[1].applyXAcc();

        //drawing
        g.setColor(new Color(0, 0, 0));
        mainPlayer.draw(g);
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].draw(g);
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

}
