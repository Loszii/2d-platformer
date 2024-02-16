package entity;
import java.awt.Graphics;
import java.awt.Color;

public class Platform extends Entity{

    private Carrot carrot = null;
    private Enemy1 enemy = null;

    public Platform(int width, int height, int xPos, int yPos, double xAcc) {
        super(width, height, xPos, yPos, xAcc);
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Enemy1 getEnemy() {
        return enemy;
    }

    public void setCarrot(Carrot carrot) {
        this.carrot = carrot;
    }

    public void setEnemy(Enemy1 enemy) {
        this.enemy = enemy;
    }

    //moves platforms side to side
    public void applyXAcc() {

        if (!inBounds(getX(), getY())) {
            setXAcc(getXAcc() * -1);;
        }
        setX(getX() + (int) getXAcc());

        if (carrot != null) {
            carrot.setX(carrot.getX() + (int) getXAcc());
        } else if (enemy != null) {
            enemy.setX(enemy.getX() + (int) getXAcc());
            enemy.applyXAcc();
        }

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        if (carrot != null) {
            carrot.draw(g);
        } else if (enemy != null) {
            enemy.draw(g);
        }
    }

}
    
