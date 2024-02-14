package main;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import entity.Player;
import entity.Platform;
import entity.Collision;
import java.awt.Font;

public class GamePanel extends JPanel {

    private static int score;
    private static int frameCounter = 0; //for walk animation  // move to Player
    private static final double gravity = 7.0;
    private static boolean wPressed = false;
    private static boolean sPressed = false;
    private static boolean aPressed = false;
    private static boolean dPressed = false;
    private static Player mainPlayer;
    private static PlatGenerator platGen;
    private static ArrayList<Platform> plats; //update in add method
    private static Collision collision;


    public GamePanel(){
        setPanelSize();

        //player
        mainPlayer = new Player(50, 50, (Game.WIDTH - 50) / 2, Game.HEIGHT - 270, 0, 0);
        //other objects
        platGen = new PlatGenerator();
        collision = new Collision();
        plats = platGen.getPlats();


        //make keybinding actions
        new KeyBinding(this);
    }

    //setters
    private void setPanelSize() {
        Dimension size = new Dimension(Game.WIDTH, Game.HEIGHT);
        setPreferredSize(size);
    }

    public static void setWPressed(boolean bool) {
        wPressed = bool;
    }
    public static void setSPressed(boolean bool) {
        sPressed = bool;
    }
    public static void setAPressed(boolean bool) {
        aPressed = bool;
    }
    public static void setDPressed(boolean bool) {
        dPressed = bool;
    }
    public static void setScore(int newScore) {
        score = newScore;
    }

    //getters
    public static int getScore() {
        return score;
    }
    public static boolean getSPressed() {
        return sPressed;
    }
    public static double getGravity() {
        return gravity;
    }
    public static Player getPlayer() {
        return mainPlayer;
    }
    public static PlatGenerator getPlatGenerator() {
        return platGen;
    }

    //moves all things down by screenSpeed and changes score
    public static void scrollScreen(int screenSpeed) {
        mainPlayer.setY(mainPlayer.getY() + screenSpeed);
        for (int i = 0; i < plats.size(); i++) {
            plats.get(i).setY(plats.get(i).getY() + screenSpeed);
        }
        score += screenSpeed;
    }

    //check when to update walk frame
    public static boolean checkFrameCounter() {
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

        //generate new plats if need
        if (plats.get(plats.size() - 1).getY() >= 0) {
            platGen.addPlats();
        }

        //background 
        g.setColor(new Color(200, 150, 150));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

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
        for (int i = 0; i < plats.size(); i++) {
            plats.get(i).applyXAcc();
        }

        //scrolling and score
        if (mainPlayer.getY() < Game.HEIGHT / 2) {
            scrollScreen((int) gravity); //scroll screen of |yAcc| and change score by same amount
        } else if (mainPlayer.getY() > 3 * Game.HEIGHT / 4){
            scrollScreen((int) -gravity);
        }

        //drawing
        for (int i = 0; i < plats.size(); i++) {
            plats.get(i).draw(g);
        }
        mainPlayer.draw(g);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("font", 3, 50));
        g.drawString(String.valueOf(score / 10), Game.WIDTH /2 , 50);
        
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
