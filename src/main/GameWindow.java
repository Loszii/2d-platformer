package main;
import javax.swing.JFrame;


public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.setUndecorated(true);
        jframe.setResizable(false);
        jframe.setSize(Game.width, Game.height);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null); //starts in center of monitor
        jframe.setVisible(true);
    }
}
