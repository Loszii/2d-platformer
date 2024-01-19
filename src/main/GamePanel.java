package main;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;

public class GamePanel extends JPanel { //inherits from JPanel

    private int x = 0, y = 0;

    public GamePanel(){
        addKeyListener(new KeyboardInputs(this)); //JPanel function
    }

    public void changeX(int value) {
        x += value;
        repaint();
    }

    public void changeY(int value) {
        y += value;
        repaint();
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable

        g.fillRect(x, y, 200, 50);
    }
}
