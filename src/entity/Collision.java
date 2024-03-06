package entity;
import main.Game;
import main.GamePanel;
import java.util.ArrayList;

public class Collision {

    static ArrayList<Platform> plats;
    static Player mainPlayer;
    private Platform closest;

    public Collision() {
        mainPlayer = GamePanel.getPlayer();
        plats = GamePanel.getPlatGenerator().getPlats();
    }

    public void checkCol() {
        closest = getNearestPlat();
        checkOutOfBounds();
        if (checkPlatformBot() && (!GamePanel.getSPressed() || closest.equals(plats.get(0)))) { //cant go thru starting plat
            mainPlayer.isGrounded = true;
            mainPlayer.yVel = 0;
            mainPlayer.y = closest.y - mainPlayer.height;
            mainPlayer.xVelOfGround = closest.xVel;
        } else {
            mainPlayer.isGrounded = false;
        }
        checkCarrot();
        checkSnake();
    }

    //collision detections
    private boolean checkPlatformBot() {
        if ((mainPlayer.x + mainPlayer.width) > closest.x && mainPlayer.x < closest.x + closest.width) {
            if (mainPlayer.y + mainPlayer.height <= closest.y && (mainPlayer.y + mainPlayer.height + mainPlayer.yVel >= closest.y)) {
                return true;
            }
        }
        return false;
    }

    //for each platforms if has carrot, check if player inside, if so, add jump height and set hasCarrot attribute in palt to no
    public void checkCarrot() {
        if (closest.carrot != null) {
            if ((mainPlayer.x + mainPlayer.width) > (closest.carrot.x) && mainPlayer.x < (closest.carrot.x + closest.carrot.width)) {
                if ((mainPlayer.y + mainPlayer.height) > closest.carrot.y && mainPlayer.y < (closest.carrot.y + closest.carrot.height)) {
                    closest.carrot = null;
                    if (!mainPlayer.ateCarrot) { //doesnt stack just increase duration
                        mainPlayer.jumpHeight -= 5.0;
                    }
                    mainPlayer.setAteCarrot(true);
                }
            }
        }
    }

    public void checkSnake() {
        if (closest.snake != null) {
            if ((mainPlayer.x + mainPlayer.width) > (closest.snake.x) && mainPlayer.x < (closest.snake.x + closest.snake.width)) {
                if ((mainPlayer.y + mainPlayer.height) > closest.snake.y && mainPlayer.y < (closest.snake.y + closest.snake.height)) {
                    mainPlayer.setHealth(mainPlayer.health - 1);
                }
            }
        }
    }

    private void checkOutOfBounds() {
        if (mainPlayer.x < -50) {
            mainPlayer.x = Game.WIDTH + 50 - mainPlayer.width;
        } else if (mainPlayer.x + mainPlayer.width > Game.WIDTH + 50) {
            mainPlayer.x = -50;
        }
    }

    //returns plat that is closest to player in yDir
    private Platform getNearestPlat() {
        int curr;
        Platform closest = plats.get(0);
        for (int i = 1; i < plats.size(); i++) {
            curr = plats.get(i).y;
            if (Math.abs(curr - (mainPlayer.y + mainPlayer.height)) < Math.abs((closest.y - (mainPlayer.y + mainPlayer.height)))) {
                closest = plats.get(i);
            }
        }
        return closest;
    }

}
