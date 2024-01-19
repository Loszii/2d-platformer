package main;
import javax.swing.JFrame;


public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.setSize(Game.width, Game.height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets default close state to value of exit attribute
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null); //starts in center of monitor
        jframe.setVisible(true);
    }
}
