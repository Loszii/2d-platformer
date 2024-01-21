package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import entity.Player;
import entity.Platform;

//add collisions and gravity

public class GamePanel extends JPanel { //inherits from JPanel

    public Player mainPlayer;
    public Platform testPlat;
    public boolean wPressed = false;
    public boolean aPressed = false;
    public boolean sPressed = false;
    public boolean dPressed = false;
    public int defaultGrav = 5;

    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height - 50, this);
        testPlat = new Platform(50, 50, 500, 550, this);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable
        g.setColor(new Color(0, 0, 0));
        mainPlayer.movePlayer();
        mainPlayer.checkBounds();
        checkCollisions();
        mainPlayer.draw(g);
        testPlat.draw(g);
    }
    //collisions with other entities
    private void checkCollisions() { //fix side collisions
        if (!mainPlayer.isGrounded && mainPlayer.xPos + mainPlayer.width > testPlat.xPos && mainPlayer.xPos < testPlat.xPos + testPlat.width && mainPlayer.yPos + mainPlayer.height > testPlat.yPos && mainPlayer.yPos < testPlat.yPos) {
            mainPlayer.yPos = testPlat.yPos - mainPlayer.height; // top col
            mainPlayer.isGrounded = true;
        } else if (mainPlayer.xPos + mainPlayer.width > testPlat.xPos && mainPlayer.xPos < testPlat.xPos + testPlat.width && mainPlayer.yPos < testPlat.yPos + testPlat.height && mainPlayer.yPos + mainPlayer.height > testPlat.yPos) {
            mainPlayer.yPos = testPlat.yPos + testPlat.height; //bottom col
        }
    }

}
