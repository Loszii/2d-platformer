package main;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;

//add collisions and gravity

public class GamePanel extends JPanel { //inherits from JPanel

    public Player mainPlayer;
    public GamePanel(){
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50);

    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable
        //g.setColor(new Color(255, 0, 0));
        mainPlayer.drawPlayer(g);

    }

    public class Player {
        public int width;
        public int height;
        public int xPos = 0;
        public int yPos = 0;

        public Player(int width, int height) {
            this.width = width;
            this.height = height;
            yPos = Game.height - height;
        }

        public void changeX(int value) {
            xPos += value;
            System.out.println("X: " + xPos);
        }
    
        public void changeY(int value) {
            yPos += value;
            System.out.println("Y: " + yPos);
        }

        public void drawPlayer(Graphics g) {
            g.fillRect(xPos, yPos, width, height);
        }
    }

}
