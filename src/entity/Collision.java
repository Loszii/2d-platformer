package entity;
import main.Game;

public class Collision {

    private Platform[] platforms;
    private Player mainPlayer;

    public Collision(Player mainPlayer, Platform[] platforms) {
        this.mainPlayer = mainPlayer;
        this.platforms = platforms;
    }

    public void checkCol() {
        checkOutOfBounds();
        checkPlatformBot();
        checkCarrot();
    }

    //collision detections
    private void checkPlatformBot() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if ((mainPlayer.getY() + mainPlayer.getHeight()) > platforms[i].getY() || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                    ;
                } else if ((mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc()) > platforms[i].getY() + platforms[i].getYAcc()) {
                    mainPlayer.setGrounded(true);
                    mainPlayer.setXAccOfGround(platforms[i].getXAcc());
                    mainPlayer.setYAcc(platforms[i].getYAcc());
                    break;
                }
                mainPlayer.setGrounded(false);
            }
        }
    }

    //for each platforms if has carrot, check if player inside, if so, add jump height and set hasCarrot attribute in palt to no
    public void checkCarrot() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if (platforms[i].getCarrot() != null) {
                    if ((mainPlayer.getX() + mainPlayer.getWidth()) > (platforms[i].getX() + (platforms[i].getCarrot().getX())) && mainPlayer.getX() < (platforms[i].getX() + (platforms[i].getCarrot().getX() + platforms[i].getCarrot().getWidth()))) {
                        if ((mainPlayer.getY() + mainPlayer.getHeight()) > (platforms[i].getY() - platforms[i].getCarrot().getHeight()) && mainPlayer.getY() < platforms[i].getY()) {
                            platforms[i].setCarrot(null);
                            if (!mainPlayer.getAteCarrot()) { //doesnt stack just increase duration
                                mainPlayer.setJumpHeight(mainPlayer.getJumpHeight() - 2.5);
                            }
                            mainPlayer.setAteCarrot(true);
                            mainPlayer.setWhenAteCarrot(System.currentTimeMillis());
                        }
                    }
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

}
