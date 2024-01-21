package inputs;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;

//to do, make square move two dir at same time using keyReleased

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override //throws error if cant implement
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: //code for virtual key w
                gamePanel.mainPlayer.changeY(-50);
                break;
            case KeyEvent.VK_A:
                gamePanel.mainPlayer.changeX(-50);
                break;
            case KeyEvent.VK_S:
                gamePanel.mainPlayer.changeY(50);
                break;
            case KeyEvent.VK_D:
                gamePanel.mainPlayer.changeX(50);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
