package main;
import javax.swing.JFrame;


public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setUndecorated(true);
        jframe.setResizable(false);
        jframe.add(gamePanel);
        jframe.pack(); //pack jpanel in frame
        jframe.setLocationRelativeTo(null); //starts in center of monitor
        jframe.setVisible(true);
    }
}
