package entity;

public class Collision {

    //possibly implement collission class to check collissions everyframe


    private Platform[] platforms;
    private Player mainPlayer;

    public Collision(Player mainPlayer, Platform[] platforms) {
        this.mainPlayer = mainPlayer;
        this.platforms = platforms;
    }

    //collision detections
    //these functions take in a new coord position and a platform and check player can move to val despite given platform

    //to do, only use setters and getters once outside of for loops
    public boolean isTopBlocked() {
        for (int i = 0; i < platforms.length; i++) {
            if (mainPlayer.getY() < (platforms[i].getY() + platforms[i].height) || mainPlayer.getX() > (platforms[i].getX() + platforms[i].getWidth()) || (mainPlayer.getX() + mainPlayer.getWidth()) < platforms[i].getX()) {
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
                return true;
            }
        }
        return false;
    }
}
