package entity;
import main.Game;
import main.GamePanel;

public class Collision {

    private Platform[] platforms;
    private Player mainPlayer;

    public Collision(Player mainPlayer, Platform[] platforms) {
        this.mainPlayer = mainPlayer;
        this.platforms = platforms;
    }

    public void checkCol() {
        checkPlatformTop();
        checkPlatformBot();
        checkPlayerLeft();
        checkPlayerRight();
        checkPlayerDiagonal();
        checkOutOfBounds();
        checkCarrot();
    }

    //collision detections
    //these functions check if an object with collide with another by first checking if its coords are lined up with the side to check, and second if their accelerations will make them overlap
    private void checkPlatformTop() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) { //if on screen
                if (mainPlayer.getY() < (platforms[i].getY() + platforms[i].getHeight()) || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                    ;
                } else if (mainPlayer.getY() + mainPlayer.getYAcc() < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                    mainPlayer.setYAcc(0);
                    break;
                }
            }
        }
    }

    private void checkPlatformBot() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if ((mainPlayer.getY() + mainPlayer.getHeight()) > platforms[i].getY() || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                    ;
                } else if ((mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc()) > platforms[i].getY() + platforms[i].getYAcc()) {
                    mainPlayer.setXAccOfGround(platforms[i].getXAcc());
                    mainPlayer.setGrounded(true);
                    mainPlayer.setYAcc(platforms[i].getYAcc());
                    break;
                }
                mainPlayer.setGrounded(false);
            }
        }
    }

    //platform collisions
    private void checkPlayerLeft() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if (platforms[i].getX() < (mainPlayer.getX() + mainPlayer.getWidth()) || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                    ;
                } else if (platforms[i].getX() + platforms[i].getXAcc() < (mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc())) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
                }
            }
        }
    }

    private void checkPlayerRight() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if ((platforms[i].getX() + platforms[i].getWidth()) > mainPlayer.getX() || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                    ;
                } else if ((platforms[i].getX() + platforms[i].getXAcc() + platforms[i].getWidth()) > mainPlayer.getX() + mainPlayer.getXAcc()) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
                }
            }
        }
    }

    //to stop player from going through corners of platforms
    private void checkPlayerDiagonal() {
        //top left
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if ((mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX() && (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()) {
                    if ((mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc()) > (platforms[i].getX() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc() > (platforms[i].getY() + platforms[i].getYAcc()))) {
                        mainPlayer.setXAcc(platforms[i].getXAcc());
                        break;
                    }
                }
            }
        }
        //bottom left
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if ((mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX() && mainPlayer.getY() > platforms[i].getY() + platforms[i].getHeight()) {
                    if ((mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc()) > (platforms[i].getX() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getYAcc()) < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                        mainPlayer.setXAcc(platforms[i].getXAcc());
                        break;
                    }
                }
            }
        }
        //top right
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if (mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) && (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()) {
                    if ((mainPlayer.getX() + mainPlayer.getXAcc()) < (platforms[i].getX() + platforms[i].getWidth() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc()) > (platforms[i].getY() + platforms[i].getYAcc())) {
                        mainPlayer.setXAcc(platforms[i].getXAcc());
                        break;
                    }
                }
            }
        }
        //bottom right
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getY() > 0 && platforms[i].getY() < Game.height) {
                if (mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) && mainPlayer.getY() > (platforms[i].getY() + platforms[i].getHeight())) {
                    if ((mainPlayer.getX() + mainPlayer.getXAcc()) < (platforms[i].getX() + platforms[i].getWidth() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getYAcc()) < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                        mainPlayer.setXAcc(platforms[i].getXAcc());
                        break;
                    }
                }
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
                            mainPlayer.setJumpHeight(mainPlayer.getJumpHeight() - 2.5);
                            GamePanel.lowerGrav();
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
