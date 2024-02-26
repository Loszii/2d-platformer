package entity;
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Entity{

    Carrot carrot = null;
    Snake snake = null;

    public Platform(int width, int height, int x, int y, double xAcc) {
        super(width, height, x, y, xAcc);
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Snake getSnake() {
        return snake;
    }

    //moves platforms side to side
    public void applyXAcc() {

        if (!inBounds(x, y)) {
            xAcc *= -1;
        }
        x += (int) xAcc;

        if (carrot != null) {
            carrot.x += (int) xAcc;
        } else if (snake != null) {
            snake.x += (int) xAcc;
            snake.applyXAcc();
        }

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, width, height);
        if (carrot != null) {
            carrot.draw(g);
        } else if (snake != null) {
            snake.draw(g);
        }
    }

}
    
