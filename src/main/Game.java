package main;

public class Game {
    
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    static final public int width = 500;
    static final public int height = 500;


    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //gets inputs of window
    }

}
