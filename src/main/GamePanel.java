package main;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import entity.Player;
import entity.Platform;
import entity.Collision;
import entity.Carrot;
import java.awt.Font;

public class GamePanel extends JPanel {

    private static int[] platWidths = {200, 300, 400};
    private static int score;
    private static int frameCounter = 0; //for walk animation 
    private static final double gravity = 9.0;
    private static boolean wPressed = false;
    private static boolean aPressed = false;
    private static boolean dPressed = false;
    private static Player mainPlayer;
    private static Platform[] platforms;
    private static Collision collision;


    public GamePanel(){
        setPanelSize();

        //instantiating entities
        mainPlayer = new Player(50, 50, (Game.width - 50) / 2, Game.height - 350, 0, gravity);
        platforms = generatePlats();

        //collision object
        collision = new Collision(mainPlayer, platforms);

        //make keybinding actions
        new KeyBinding(this);
    }

    //setters
    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
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

    //getters
    public static double getGravity() {
        return gravity;
    }

    //generates a bunch of random platforms in the sky
    public Platform[] generatePlats() {
        Platform[] plats = new Platform[1000];
        Random rand = new Random();
        int yCounter = Game.height - 400;
        int curWidth;
        Carrot carrot;
        plats[0] = new Platform(2120, 300, -100, Game.height - 300, 0, 0, null); //platform under player
        for (int i = 1; i < plats.length; i++) {
            curWidth = platWidths[rand.nextInt(platWidths.length)]; //gets random width
            if (rand.nextInt(100) <= 7) { //7% chance
                carrot = new Carrot(25, 25, rand.nextInt(curWidth - 25));
            } else {
                carrot = null;
            }
            plats[i] = new Platform(curWidth, 10, rand.nextInt(Game.width - curWidth), yCounter, (rand.nextInt(6) + 1), 0, carrot);
            yCounter -= 100 + rand.nextInt(100); //gets farther as go up
        }
        return plats;
    }

    //moves all thngs down by screenSpeed
    public void scrollScreen(int screenSpeed) {
        mainPlayer.setY(mainPlayer.getY() + screenSpeed);
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].setY(platforms[i].getY() + screenSpeed);
        }
    }

    //check when to update walk frame
    public boolean checkFrameCounter() {
        int xDiff = (int) (mainPlayer.getXAcc() - mainPlayer.getXAccOfGround());
        if (xDiff > 0 && frameCounter > 17 - xDiff) {
            return true;
        } else if (frameCounter > 17 + xDiff) {
            return true;
        }
        return false;
    }

    //MAIN GAME LOOP
    public void paintComponent(Graphics g) {
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //calling original paintComponent()

        //background 
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, Game.width, Game.height);

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

        //items
        if (mainPlayer.getAteCarrot()) {
            mainPlayer.checkCarrotRanOut();
        }

        //movement
        mainPlayer.applyXAcc();
        mainPlayer.applyYAcc();
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].applyXAcc();
            platforms[i].applyYAcc(); //not tested yet
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
        for (int i = 0; i < platforms.length; i++) {
            platforms[i].draw(g);
        }
        mainPlayer.draw(g);
        g.setColor(new Color(0, 0, 0));
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

}
