package main;
import java.util.ArrayList;
import java.util.Random;
import entity.Carrot;
import entity.Platform;

public class PlatGenerator {

    private final static int[] PLATWIDTHS = {200, 300, 400};
    private static ArrayList<Platform> plats;
    private int yCounter = Game.HEIGHT - 400;

    public PlatGenerator() {
        plats = new ArrayList<Platform>();
        plats.add(new Platform(2120, 220, -100, Game.HEIGHT - 220, 0, 0, null)); //plat under player
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
        Carrot carrot;

        while (true) {
            if (yCounter <= -Game.HEIGHT) {
                break;
            }
            curWidth = PLATWIDTHS[rand.nextInt(PLATWIDTHS.length)]; //gets random width
            if (rand.nextInt(100) <= 5) { //5% chance
                carrot = new Carrot(25, 25, rand.nextInt(curWidth - 25));
            } else {
                carrot = null;
            }
            plats.add(new Platform(curWidth, 10, rand.nextInt(Game.WIDTH - curWidth), yCounter, (rand.nextInt(6) + 1), 0, carrot));
            yCounter -= 100 + rand.nextInt(100);
        }
        yCounter = -100 - rand.nextInt(100);
    }
}
