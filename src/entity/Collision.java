package entity;
import main.Game;

public class Collision {

    private Platform[] platforms;
    private Player mainPlayer;

    public Collision(Player mainPlayer, Platform[] platforms) {
        this.mainPlayer = mainPlayer;
        this.platforms = platforms;
    }

    //collision detections
    //these functions take in a new coord position and a platform and check player can move to val despite given platform
    public boolean isTopBlocked() {
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getY() < (platforms[i].getY() + platforms[i].getHeight()) || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                ;
            } else if (mainPlayer.getY() + mainPlayer.getYAcc() < (platforms[i].getY() + platforms[i].getHeight())) {
                return true;
            }
        }
        return false;
    }

    public boolean isRightBlocked() {
        for (int i = 0; i < platforms.length; i++) {
            if ((mainPlayer.getX() + mainPlayer.getWidth()) > platforms[i].getX() || mainPlayer.getY() > (platforms[i].getY() + platforms[i].getHeight()) || (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()){
                ;
            } else if ((mainPlayer.getX() + mainPlayer.getXAcc() + mainPlayer.getWidth()) > platforms[i].getX()) {
                return true;
            }
        }
        return false;
    }
    public boolean isLeftBlocked() {
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getX() < (platforms[i].getX() + platforms[i].getWidth()) || mainPlayer.getY() > (platforms[i].getY() + platforms[i].getHeight()) || (mainPlayer.getY() + mainPlayer.getHeight()) < platforms[i].getY()){
                ;
            } else if (mainPlayer.getX() + mainPlayer.getXAcc() < (platforms[i].getX() + platforms[i].getWidth())) {
                return true;
            }
        }
        return false;
    }
    public boolean isBotBlocked() {
        for (int i = 0; i < platforms.length; i++) {
            if ((mainPlayer.getY() + mainPlayer.getHeight()) > platforms[i].getY() || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
                ;
            } else if ((mainPlayer.getY() + mainPlayer.getYAcc() + mainPlayer.getHeight()) > platforms[i].getY()) {
                mainPlayer.setXAccOfGround(platforms[i].getXAcc());
                return true;
            }
        }
        return false;
    }

    //platform collisions
    public boolean checkPlayerToLeft() {
        for (int i = 0; i < platforms.length; i++) {
            if (platforms[i].getX() < (mainPlayer.getX() + mainPlayer.getWidth()) || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                ;
            } else if (platforms[i].getX() + platforms[i].getXAcc() < (mainPlayer.getX() + mainPlayer.getWidth() + mainPlayer.getXAcc())) {
                mainPlayer.setXAcc(platforms[i].getXAcc());
                return true;
            }
        }
        return false;
    }

    public boolean checkPlayerToRight() {
        for (int i = 0; i < platforms.length; i++) {
            if ((platforms[i].getX() + platforms[i].getWidth()) > mainPlayer.getX() || platforms[i].getY() > (mainPlayer.getY() + mainPlayer.getHeight()) || (platforms[i].getY() + platforms[i].getHeight()) < mainPlayer.getY()){
                ;
            } else if ((platforms[i].getX() + platforms[i].getXAcc() + platforms[i].getWidth()) > mainPlayer.getX() + mainPlayer.getXAcc()) {
                mainPlayer.setXAcc(platforms[i].getXAcc());
                return true;
            }
        }
        return false;
    }

    public void checkOutOfBounds() {
        if (mainPlayer.getX() < -50) {
            mainPlayer.setX(Game.width + 50 - mainPlayer.getWidth());
        } else if (mainPlayer.getX() + mainPlayer.getWidth() > Game.width + 50) {
            mainPlayer.setX(-50);
        }
    }
}
