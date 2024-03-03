package entity;
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Entity{

    Carrot carrot = null;
    Snake snake = null;

    public Platform(int width, int height, int x, int y, double xVel) {
        super(width, height, x, y, xVel);
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Snake getSnake() {
        return snake;
    }

    //moves platforms side to side
    public void applyXVel() {

        if (!inBounds()) {
            xVel *= -1;
        }
        x += (int) xVel;

        if (carrot != null) {
            carrot.x += (int) xVel;
        } else if (snake != null) {
            snake.x += (int) xVel;
            snake.applyXVel();
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
    
