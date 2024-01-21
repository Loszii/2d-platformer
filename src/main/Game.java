package main;

public class Game implements Runnable {
    
    static final public int width = 1280;
    static final public int height = 720;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); //sets inputs to focus on window
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this); //makes new thread object that takes in this implementation of Runnable
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET; //nano seconds per frame
        long lastFrame = System.nanoTime(); //time of last frame
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis(); //time of last fps count
        while (true) {
            now = System.nanoTime(); //constantly update clock
            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                frames++;
                lastFrame = now;
            } 
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames); //every second print repaint count
                frames = 0;
            }
        }
    }

}
