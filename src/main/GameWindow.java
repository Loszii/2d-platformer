package main;
import javax.swing.JFrame;


public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets default close state to value of exit attribute
        jframe.add(gamePanel);
        jframe.setVisible(true);
    }
}
