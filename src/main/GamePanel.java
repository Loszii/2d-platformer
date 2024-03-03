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

    public static final double gravity = 0.5;
    public static final double friction = 0.75;
    public static final double airFriction = 0.7;
    static int prevScore = 0;
    static int highScore = 0;
    static int score = 0;
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
        if (status) {
            prevScore = score;
            if (prevScore > highScore) {
                highScore = score;
            }
            resetGame();
        }
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

    private static void resetGame() {
        scrollScreen(-score);
        mainPlayer.setX((Game.WIDTH - 50) / 2);
        mainPlayer.setY(Game.HEIGHT - 270);
        mainPlayer.setYVel(0);
        mainPlayer.setXVel(0);
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
                    mainPlayer.setYVel(mainPlayer.getJumpHeight());
                }
            }
            if (aPressed && mainPlayer.isWithinSpeed()) {
                mainPlayer.setFacingRight(false);
                mainPlayer.setXVel(mainPlayer.getXVel() - mainPlayer.getXAcc());
            }
            if (dPressed && mainPlayer.isWithinSpeed()) {
                mainPlayer.setFacingRight(true);
                mainPlayer.setXVel(mainPlayer.getXVel() + mainPlayer.getXAcc());
            }

            //collision
            collision.checkCol();

            //items
            if (mainPlayer.getAteCarrot()) {
                mainPlayer.checkCarrotRanOut();
            }

            //applying movement
            mainPlayer.applyXVel();
            mainPlayer.applyYVel();
            for (int i = 0; i < plats.size(); i++) {
                plats.get(i).applyXVel();
            }

            //scrolling and score
            if ((mainPlayer.getY() < Game.HEIGHT / 2) || (mainPlayer.getY() > 3 * Game.HEIGHT / 4)) {
                scrollScreen((int) (-1 * mainPlayer.getYVel()));
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
                g.fillRect(mainPlayer.getX(), mainPlayer.getY() - 6, (int) (5 * (10 - (System.currentTimeMillis() - mainPlayer.getWhenAteCarrot()) / 1000)), 3); //carrot timer
            }
            g.setColor(new Color(100, 0, 0));
            g.setFont(new Font("font", 3, 50));
            g.drawString(String.valueOf(score / 10), Game.WIDTH / 2 - 25, 50); //score
            
        } else {
            //gameover screen
            g.setColor(new Color(100, 100, 100)); //background
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setColor(new Color(125, 125, 125));
            g.fillRect(300, 300, 1320, 480);

            g.setColor(new Color(0, 0, 0)); //gameover
            g.setFont(new Font("font", 1, 200));
            g.drawString("GAME OVER!", 300, Game.HEIGHT / 2 - 300); 

            g.setFont(new Font("font", 3, 100)); //score
            g.drawString("Score: ", 500, Game.HEIGHT / 2 - 100); 
            g.setFont(new Font("font", 2, 100));
            g.setColor(new Color(100, 0, 0));
            g.drawString(String.valueOf(prevScore / 10), 1200, Game.HEIGHT / 2 - 100);
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("font", 3, 100));
            g.drawString("High Score: ", 500, Game.HEIGHT / 2 + 100); 
            g.setFont(new Font("font", 2, 100));
            g.setColor(new Color(100, 0, 0));
            g.drawString(String.valueOf(highScore / 10), 1200, Game.HEIGHT / 2 + 100);
            g.setColor(new Color(0, 0, 0));

            g.setFont(new Font("font", 1, 100)); //last line 
            g.drawString("PRESS ENTER OR ESC", 400, Game.HEIGHT / 2 + 400);
        }
    }

}
