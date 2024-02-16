package main;
import java.util.ArrayList;
import java.util.Random;
import entity.Carrot;
import entity.Enemy1;
import entity.Platform;

public class PlatGenerator {

    private final static int[] PLATWIDTHS = {200, 300, 400};
    private static ArrayList<Platform> plats;
    private int yCounter = Game.HEIGHT - 400;

    public PlatGenerator() {
        plats = new ArrayList<Platform>();
        plats.add(new Platform(2120, 500, -100, Game.HEIGHT - 220, 0)); //plat under player
    }

    public ArrayList<Platform> getPlats() {
        return plats;
    }

    public int getYCounter() {
        return yCounter;
    }

    //adds plats to fill another game window
    public void addPlats() {
        Random rand = new Random();
        int curWidth;
        int xPos;
        double xAcc;

        while (true) {
            if (yCounter <= -Game.HEIGHT) {
                break;
            }
            curWidth = PLATWIDTHS[rand.nextInt(PLATWIDTHS.length)]; //gets random width
            xPos = rand.nextInt(Game.WIDTH - curWidth);
            xAcc = (rand.nextInt(6) + 1);

            plats.add(new Platform(curWidth, 10, xPos, yCounter, xAcc));

            if (rand.nextInt(100) <= 5) { //5% chance
                plats.get(plats.size() - 1).setCarrot(new Carrot(25, 25, xPos + rand.nextInt(curWidth - 25), yCounter - 25));
            } else if (rand.nextInt(100) <= 10) { //10% chance
                plats.get(plats.size() - 1).setEnemy(new Enemy1(35, 35, xPos + rand.nextInt(curWidth - 35), yCounter - 35, 1 + rand.nextInt(2)));
                plats.get(plats.size() - 1).getEnemy().setGroundPlat(plats.get(plats.size() - 1));
            }

            yCounter -= 100 + rand.nextInt(100);
        }
        yCounter = -100 - rand.nextInt(100);
    }
}
