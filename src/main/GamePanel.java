package main;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel{ //inherits from JPanel
    public GamePanel(){

    }

    public void paintComponent(Graphics g){ //needs graphics object, auto runs from JPanel
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method

        g.fillRect(100, 100, 200, 50);
    }
}
