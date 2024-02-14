package entity;
import main.Game;
import main.GamePanel;

public class Collision {

    private Platform[] platforms;
    private Player mainPlayer;
    private Platform closest;

    public Collision() {
        this.mainPlayer = GamePanel.getPlayer();
        this.platforms = GamePanel.getPlats();
    }

    public void checkCol() {
        closest = getNearestPlat();
        checkOutOfBounds();
        if (checkPlatformBot() && (!GamePanel.getSPressed() || closest.equals(platforms[0]))) { //cant go thru starting plat
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
            mainPlayer.setX(Game.width + 50 - mainPlayer.getWidth());
        } else if (mainPlayer.getX() + mainPlayer.getWidth() > Game.width + 50) {
            mainPlayer.setX(-50);
        }
    }

    //returns plat that is closest to player in yDir
    private Platform getNearestPlat() {
        int curr;
        Platform closest = platforms[0];
        for (int i = 1; i < platforms.length; i++) {
            curr = platforms[i].getY();
            if (Math.abs(curr - (mainPlayer.getY() + mainPlayer.getHeight())) < Math.abs((closest.getY() - (mainPlayer.getY() + mainPlayer.getHeight())))) {
                closest = platforms[i];
            }
        }
        return closest;
    }

}
