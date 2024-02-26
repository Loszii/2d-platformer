package main;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import entity.Player;
import entity.Platform;
import entity.Collision;
import entity.Generator;
import java.awt.Font;

public class GamePanel extends JPanel {

    public static final double gravity = 7.0;
    static int score;
    static boolean wPressed = false;
    static boolean sPressed = false;
    static boolean aPressed = false;
    static boolean dPressed = false;
    static boolean gameOver = false;
    static Player mainPlayer;
    static ArrayList<Platform> plats;
    static Generator platGen;
    static Collision collision;


    public GamePanel(){
        setPanelSize();

        //object init
        mainPlayer = new Player(50, 50, (Game.WIDTH - 50) / 2, Game.HEIGHT - 270);

        platGen = new Generator();
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
    public static void setGameOver(boolean status) {
        gameOver = status;
        resetGame();
    }


    //getters
    public static boolean getSPressed() {
        return sPressed;
    }
    public static Player getPlayer() {
        return mainPlayer;
    }
    public static Generator getPlatGenerator() {
        return platGen;
    }

    //moves all things down by screenSpeed and changes score
    private static void scrollScreen(int screenSpeed) {
        mainPlayer.setY(mainPlayer.getY() + screenSpeed);
        for (int i = 0; i < plats.size(); i++) {
            plats.get(i).setY(plats.get(i).getY() + screenSpeed);
            if (plats.get(i).getCarrot() != null) {
                plats.get(i).getCarrot().setY(plats.get(i).getCarrot().getY() + screenSpeed);
            } else if (plats.get(i).getSnake() != null) {
                plats.get(i).getSnake().setY(plats.get(i).getSnake().getY() + screenSpeed);
            }
        }
        score += screenSpeed;
    }

    public static void resetGame() { //make private and have gameover screen call it
        scrollScreen(-score);
        mainPlayer.setX((Game.WIDTH - 50) / 2);
        mainPlayer.setY(Game.HEIGHT - 270);
        mainPlayer.setYAcc(0);
        mainPlayer.setXAcc(0);
        score = 0;
        mainPlayer.setAteCarrot(false);
        platGen.restartGen();
        plats = platGen.getPlats();
    }

    //MAIN GAME LOOP
    public void paintComponent(Graphics g) {
        if (!gameOver) {
            //generate new plats if need
            if (plats.get(plats.size() - 1).getY() >= 0) {
                platGen.addPlats();
            }

            //background 
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

            //keyboard inputs
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

            //applying movement
            mainPlayer.applyXAcc();
            mainPlayer.applyYAcc();
            for (int i = 0; i < plats.size(); i++) {
                plats.get(i).applyXAcc();
            }

            //scrolling and score
            if (mainPlayer.getY() < Game.HEIGHT / 2) {
                scrollScreen((int) gravity - 3); //scroll screen of |yAcc| and change score by same amount
            } else if (mainPlayer.getY() > 3 * Game.HEIGHT / 4){
                scrollScreen((int) -gravity);
            }

            //animation
            mainPlayer.changeWalkFrame();
            for (int i = 0; i < plats.size(); i++) {
                if (plats.get(i).getSnake() != null) {
                    plats.get(i).getSnake().changeWalkFrame();
                }
            }

            //drawing
            for (int i = 0; i < plats.size(); i++) {
                plats.get(i).draw(g);
            }
            mainPlayer.draw(g);
            if (mainPlayer.getAteCarrot()) { //plat timer
                g.setColor(new Color(225, 120, 35));
                g.fillRect(mainPlayer.getX(), mainPlayer.getY() - 6, (int) (5 * (10 - (System.currentTimeMillis() - mainPlayer.getWhenAteCarrot()) / 1000)), 3);
            }
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("font", 3, 50));
            g.drawString(String.valueOf(score / 10), Game.WIDTH / 2 - 25, 50); //score
            
        } else {
            //gameover screen
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("font", 1, 200));
            g.drawString("GAME OVER!", 300, Game.HEIGHT / 2 - 200); 
            g.setFont(new Font("font", 1, 150));
            g.drawString("PRESS ENTER OR ESC", 125, Game.HEIGHT / 2 + 200);
        }
    }

}
