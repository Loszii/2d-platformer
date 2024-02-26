package entity;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Carrot extends Entity {

    private static BufferedImage carrotImg;

    public Carrot(int width, int height, int x, int y) {
        super(width, height, x, y, 0);
        carrotImg = importImg("/res/item/carrot.png");
    }

    public static BufferedImage getCarrotImg() {
        return carrotImg;
    }

    public void draw(Graphics g) {
        g.drawImage(carrotImg, x, y, null);
    }

}
