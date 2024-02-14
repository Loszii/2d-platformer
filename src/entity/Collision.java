package entity;
import main.Game;
import main.GamePanel;
import java.util.ArrayList;

public class Collision {

    private ArrayList<Platform> plats;
    private Player mainPlayer;
    private Platform closest;

    public Collision() {
        this.mainPlayer = GamePanel.getPlayer();
        this.plats = GamePanel.getPlatGenerator().getPlats();
    }

    public void checkCol() {
        closest = getNearestPlat();
        checkOutOfBounds();
        if (checkPlatformBot() && (!GamePanel.getSPressed() || closest.equals(plats.get(0)))) { //cant go thru starting plat
            mainPlayer.setGrounded(true);
            mainPlayer.setYAcc(0);
            mainPlayer.setY(closest.getY() - mainPlayer.getHeight());
            mainPlayer.setXAccOfGround(closest.getXAcc());
        } else {
            mainPlayer.setGrounded(false);
        }
        checkCarrot();
    }

    //collision detections
    private boolean checkPlatformBot() {
        if ((mainPlayer.getX() + mainPlayer.getWidth()) > closest.getX() && mainPlayer.getX() < closest.getX() + closest.getWidth()) {
            if (mainPlayer.getY() + mainPlayer.getHeight() <= closest.getY() && (mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc() >= closest.getY())) {
                return true;
            }
        }
        return false;
    }

    //for each platforms if has carrot, check if player inside, if so, add jump height and set hasCarrot attribute in palt to no
    public void checkCarrot() {
        if (closest.getCarrot() != null) {
            if ((mainPlayer.getX() + mainPlayer.getWidth()) > (closest.getX() + (closest.getCarrot().getX())) && mainPlayer.getX() < (closest.getX() + (closest.getCarrot().getX() + closest.getCarrot().getWidth()))) {
                if ((mainPlayer.getY() + mainPlayer.getHeight()) > (closest.getY() - closest.getCarrot().getHeight()) && mainPlayer.getY() < closest.getY()) {
                    closest.setCarrot(null);
                    if (!mainPlayer.getAteCarrot()) { //doesnt stack just increase duration
                        mainPlayer.setJumpHeight(mainPlayer.getJumpHeight() - 5.0);
                    }
                    mainPlayer.setAteCarrot(true);
                    mainPlayer.setWhenAteCarrot(System.currentTimeMillis());
                }
            }
        }
    }

    private void checkOutOfBounds() {
        if (mainPlayer.getX() < -50) {
            mainPlayer.setX(Game.WIDTH + 50 - mainPlayer.getWidth());
        } else if (mainPlayer.getX() + mainPlayer.getWidth() > Game.WIDTH + 50) {
            mainPlayer.setX(-50);
        }
    }

    //returns plat that is closest to player in yDir
    private Platform getNearestPlat() {
        int curr;
        Platform closest = plats.get(0);
        for (int i = 1; i < plats.size(); i++) {
            curr = plats.get(i).getY();
            if (Math.abs(curr - (mainPlayer.getY() + mainPlayer.getHeight())) < Math.abs((closest.getY() - (mainPlayer.getY() + mainPlayer.getHeight())))) {
                closest = plats.get(i);
            }
        }
        return closest;
    }

}
