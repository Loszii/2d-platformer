package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import entity.Player;
import entity.Platform;

//make platforms teleport from one side to the other and move, as well as game scroll upwards

public class GamePanel extends JPanel { //inherits from JPanel

    public Player mainPlayer;
    public Platform[] platforms;
    public boolean wPressed = false;
    public boolean aPressed = false;
    public boolean dPressed = false;
    public int defaultGrav = 5;

    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height - 50, this);
        platforms = new Platform[2];
        platforms[0] = new Platform(100, 25, 500, 500, this);
        platforms[1] = new Platform(100, 25, 200, 400, this);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable
        mainPlayer.movePlayer(platforms);
        mainPlayer.checkBounds();
        g.setColor(new Color(255, 0, 0));
        mainPlayer.draw(g);
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].draw(g);
        }
    }

}
