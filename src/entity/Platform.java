package entity;
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Entity{

    private Carrot carrot = null;
    private Snake snake = null;

    public Platform(int width, int height, int xPos, int yPos, double xAcc) {
        super(width, height, xPos, yPos, xAcc);
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setCarrot(Carrot carrot) {
        this.carrot = carrot;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    //moves platforms side to side
    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

        if (carrot != null) {
            carrot.setX(carrot.getX() + (int) getXAcc());
        } else if (snake != null) {
            snake.setX(snake.getX() + (int) getXAcc());
            snake.applyXAcc();
        }

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        if (carrot != null) {
            carrot.draw(g);
        } else if (snake != null) {
            snake.draw(g);
        }
    }

}
    
