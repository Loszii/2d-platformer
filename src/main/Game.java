package main;

public class Game implements Runnable {
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private final int FPS_SET = 120;
    private GamePanel gamePanel;
    private Thread gameThread;

    public Game() {
        gamePanel = new GamePanel();
        new GameWindow(gamePanel);
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
