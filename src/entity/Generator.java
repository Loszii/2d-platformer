package entity;
import java.util.ArrayList;
import java.util.Random;
import main.Game;

public class Generator {

    final static int[] PLATWIDTHS = {300, 400, 500};
    static ArrayList<Platform> plats;
    private int yCounter = Game.HEIGHT - 400;

    public Generator() {
        plats = new ArrayList<Platform>();
        plats.add(new Platform(2120, 500, -100, Game.HEIGHT - 220, 0)); //plat under player
    }

    public ArrayList<Platform> getPlats() {
        return plats;
    }

    public int getYCounter() {
        return yCounter;
    }

    public void restartGen() {
        plats = new ArrayList<Platform>();
        plats.add(new Platform(2120, 500, -100, Game.HEIGHT - 220, 0));
        yCounter = Game.HEIGHT - 400;
        Collision.plats = plats;
    }

    //adds plats to fill another game window
    public void addPlats() {
        Random rand = new Random();
        int curWidth;
        int xPos;
        double xVel;

        while (true) {
            if (yCounter <= -Game.HEIGHT) {
                break;
            }
            curWidth = PLATWIDTHS[rand.nextInt(PLATWIDTHS.length)]; //gets random width
            xPos = rand.nextInt(Game.WIDTH - curWidth);
            xVel = (rand.nextInt(6) + 1);

            plats.add(new Platform(curWidth, 10, xPos, yCounter, xVel));

            if (rand.nextInt(100) <= 5) { //5% chance
                plats.get(plats.size() - 1).carrot = new Carrot(25, 25, xPos + rand.nextInt(curWidth - 25), yCounter - 25);
            } else if (rand.nextInt(100) <= 25) { //25% chance
                plats.get(plats.size() - 1).snake = new Snake(75, 20, xPos + rand.nextInt(curWidth - 75), yCounter - 20, 1 + rand.nextInt(2));
                plats.get(plats.size() - 1).snake.groundPlat = plats.get(plats.size() - 1);
            }

            yCounter -= 100 + rand.nextInt(100);
        }
        yCounter = -100 - rand.nextInt(100);
    }
}
