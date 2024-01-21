package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;

//add collisions and gravity

public class GamePanel extends JPanel { //inherits from JPanel

    public Player mainPlayer;
    public boolean wPressed = false;
    public boolean aPressed = false;
    public boolean sPressed = false;
    public boolean dPressed = false;
    public int defaultGrav = 5;

    public GamePanel(){
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //JPanel function
        mainPlayer = new Player(50, 50);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.width, Game.height);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) { //needs graphics object, auto runs from JPanel
        //g is attribute of jPanel that was inherited
        super.paintComponent(g); //super is JPanel, calling JPanels own paint method with its pre defined graphics variable
        //g.setColor(new Color(255, 0, 0));
        mainPlayer.yAcceleration();
        mainPlayer.movePlayer();
        mainPlayer.checkBounds();
        mainPlayer.drawPlayer(g);

    }

    public class Player {
        public int width;
        public int height;
        public int xPos = 0;
        public int yPos = 0;
        public int xAcc = 0;
        public int yAcc = 5;
        public boolean isGrounded = true;

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

        public void movePlayer() {
            if (wPressed && isGrounded) {
                yAcc = -20;
            }
            if (aPressed) {
                changeX(-10);
            }
            if (sPressed) {
                changeY(10);
            }
            if (dPressed) {
                changeX(10);
            }
        }

        private void yAcceleration() {
            yPos += yAcc;
            if (yAcc != defaultGrav) {
                yAcc += 1;
            }
        }

        private void checkBounds() {
            if (yPos < 0) {
                yPos = 0;
            }
            if (xPos < 0) {
                xPos = 0;
            }
            if (yPos > Game.height - height) {
                yPos = Game.height - height;
                isGrounded = true;
            } else {
                isGrounded = false;
            }
            if (xPos > Game.width - width) {
                xPos = Game.width - width;
            }
        }

    }

}
