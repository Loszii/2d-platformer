package inputs;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;

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
                gamePanel.setWPressed(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.setAPressed(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDPressed(true);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: //code for virtual key w
                gamePanel.setWPressed(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.setAPressed(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDPressed(false);
                break;
        }
    }
}
