package main;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;
import entity.Player;
import entity.Platform;
import entity.Collision;
import entity.Carrot;
import java.awt.Font;

//to do: make gravity scale with score, not jump height. better organize code
//maybe put actions in a different file
//collision detection seems to not be working properly with new friction values
//maybe to simplify make it so player can jump through platforms and add enemies to make game harder, not just parkour
//if keeping carrots, make them bigger

public class GamePanel extends JPanel { //inherits from JPanel

    private int[] platWidths = {200, 250, 300, 350, 400};
    private int score;
    private int frameCounter = 0; //for walk animation 
    public static double gravity = 9.0;
    private Player mainPlayer;
    private Platform[] platforms;
    private Collision collision;
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private BufferedImage sky;
    private BufferedImage space;
    private Action exitAction;
    private Action upAction;
    private Action upActionRelease;
    private Action leftAction;
    private Action leftActionRelease;
    private Action rightAction;
    private Action rightActionRelease;

    //actions
    public class exitAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    public class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            wPressed = true;
        }
    }
    public class UpActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            wPressed = false;
        }
    }
    public class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            aPressed = true;
        }
    }
    public class LeftActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            aPressed = false;
        }
    }
    public class RightAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            dPressed = true;
        }
    }
    public class RightActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            dPressed = false;
        }
    }


    public GamePanel(){
        setPanelSize();

        //instantiating entities
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height - 350, 0, gravity);
        sky = mainPlayer.importImg("/res/background/sky.jpg");
        space = mainPlayer.importImg("/res/background/space.jpg");
        platforms = generatePlats();

        //collision object
        collision = new Collision(mainPlayer, platforms);

        //keybindings
        exitAction = new exitAction();
        upAction = new UpAction();
        upActionRelease = new UpActionRelease();
        leftAction = new LeftAction();
        leftActionRelease = new LeftActionRelease();
        rightAction = new RightAction();
        rightActionRelease = new RightActionRelease();
        //wasd
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");//true is onRelease
        this.getActionMap().put("exit", exitAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");
        this.getActionMap().put("up", upAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "upRelease");
        this.getActionMap().put("upRelease", upActionRelease);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        this.getActionMap().put("left", leftAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftRelease");
        this.getActionMap().put("leftRelease", leftActionRelease);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
        this.getActionMap().put("right", rightAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightRelease");
        this.getActionMap().put("rightRelease", rightActionRelease);
        //arrow keys
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        this.getActionMap().put("up", upAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upRelease");
        this.getActionMap().put("upRelease", upActionRelease);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        this.getActionMap().put("left", leftAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftRelease");
        this.getActionMap().put("leftRelease", leftActionRelease);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        this.getActionMap().put("right", rightAction);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightRelease");
        this.getActionMap().put("rightRelease", rightActionRelease);
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

        //movement
        if (wPressed) {
            if (mainPlayer.getGrounded()) {
                mainPlayer.setYAcc(mainPlayer.getJumpHeight());
            }
        }
        if (aPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setFacingRight(false);
            mainPlayer.setXAcc(mainPlayer.getXAcc() - 1.0);
        }
        if (dPressed && mainPlayer.isWithinSpeed()) {
            mainPlayer.setFacingRight(true);
            mainPlayer.setXAcc(mainPlayer.getXAcc() + 1.0);
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
        if (mainPlayer.getY() < Game.height / 2 && mainPlayer.getYAcc() < 0) {
            scrollScreen(Math.abs(((int) mainPlayer.getYAcc()))); //scroll screen of |yAcc| and change score by same amount
            score +=  Math.abs(((int) mainPlayer.getYAcc()));
        } else if (mainPlayer.getY() > 3 * Game.height / 4 && mainPlayer.getYAcc() > 0){
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
            if ((i - 1) % (25 - (i * (1/25))) == 0) { //every 25th plat has carrot, as spacing bigger needs to be more often
                carrot = new Carrot(25, 25, rand.nextInt(curWidth - 25));
            } else {
                carrot = null;
            }
            plats[i] = new Platform(curWidth, 25, rand.nextInt(Game.width - curWidth), yCounter, rand.nextInt(7), 0, carrot);
            yCounter -= 50 + rand.nextInt(100) + (4 * i); //gets farther as go up
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
