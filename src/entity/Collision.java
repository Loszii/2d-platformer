package entity;
import main.Game;

public class Collision {

    private Platform[] platforms;
    private Player mainPlayer;

    public Collision(Player mainPlayer, Platform[] platforms) {
        this.mainPlayer = mainPlayer;
        this.platforms = platforms;
    }

    //to do: bring for loops outside of functions and inside checkCol, and use if (plat on screen) then run all of col functions

    public void checkCol() {
        checkPlatformTop();
        checkPlatformBot();
        checkPlayerLeft();
        checkPlayerRight();
        checkPlayerDiagonal();
        checkOutOfBounds();
    }


    //collision detections
    //these functions check if an object with collide with another by first checking if its coords are lined up with the side to check, and second if their accelerations will make them overlap
    private void checkPlatformTop() {
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getY() < (platforms[i].getY() + platforms[i].getHeight()) || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                ;
            } else if (mainPlayer.getY() + mainPlayer.getYAcc() < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                mainPlayer.setYAcc(0);
                break;
            }
        }
    }

    private void checkPlatformBot() {
        for (int i = 0; i < platforms.length; i++) {
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

    //platform collisions
    private void checkPlayerLeft() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getX() < (mainPlayer.getX() + mainPlayer.getWidth()) || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                ;
            } else if (platforms[i].getX() + platforms[i].getXAcc() < (mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc())) {
                mainPlayer.setXAcc(platforms[i].getXAcc());
                break;
            }
        }
    }

    private void checkPlayerRight() {
        for (int i = 0; i < platforms.length; i++) {
            if ((platforms[i].getX() + platforms[i].getWidth()) > mainPlayer.getX() || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                ;
            } else if ((platforms[i].getX() + platforms[i].getXAcc() + platforms[i].getWidth()) > mainPlayer.getX() + mainPlayer.getXAcc()) {
                mainPlayer.setXAcc(platforms[i].getXAcc());
                break;
            }
        }
    }

    private void checkPlayerDiagonal() {
        //top left
        for (int i = 0; i < platforms.length; i++) {
            if ((mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX() && (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()) {
                if ((mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc()) > (platforms[i].getX() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc() > (platforms[i].getY() + platforms[i].getYAcc()))) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
                }
            }
        }
        //bottom left
        for (int i = 0; i < platforms.length; i++) {
            if ((mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX() && mainPlayer.getY() > platforms[i].getY() + platforms[i].getHeight()) {
                if ((mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc()) > (platforms[i].getX() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getYAcc()) < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
                }
            }
        }
        //top right
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) && (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()) {
                if ((mainPlayer.getX() + mainPlayer.getXAcc()) < (platforms[i].getX() + platforms[i].getWidth() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getHeight() + mainPlayer.getYAcc()) > (platforms[i].getY() + platforms[i].getYAcc())) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
                }
            }
        }
        //bottom right
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) && mainPlayer.getY() > (platforms[i].getY() + platforms[i].getHeight())) {
                if ((mainPlayer.getX() + mainPlayer.getXAcc()) < (platforms[i].getX() + platforms[i].getWidth() + platforms[i].getXAcc()) && (mainPlayer.getY() + mainPlayer.getYAcc()) < (platforms[i].getY() + platforms[i].getHeight() + platforms[i].getYAcc())) {
                    mainPlayer.setXAcc(platforms[i].getXAcc());
                    break;
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
