package main;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

public class KeyBinding {

    private GamePanel gamePanel;
    private Action exitAction;
    private Action upAction;
    private Action upActionRelease;
    private Action leftAction;
    private Action leftActionRelease;
    private Action rightAction;
    private Action rightActionRelease;

    public KeyBinding(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        //keybindings
        exitAction = new exitAction();
        upAction = new UpAction();
        upActionRelease = new UpActionRelease();
        leftAction = new LeftAction();
        leftActionRelease = new LeftActionRelease();
        rightAction = new RightAction();
        rightActionRelease = new RightActionRelease();

        setInputMap();
        setActionMap();
        
    }

    //actions
    public class exitAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    public class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setWPressed(true);
        }
    }
    public class UpActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setWPressed(false);
        }
    }
    public class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setAPressed(true);
        }
    }
    public class LeftActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setAPressed(false);
        }
    }
    public class RightAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setDPressed(true);
        }
    }
    public class RightActionRelease extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gamePanel.setDPressed(false);
        }
    }

    //sets inputMap values for keybindings in GamePanel
    public void setInputMap() {
        //esc
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");//true is onRelease
        //space
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "up");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "upRelease");
        //wasd
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "upRelease");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftRelease");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightRelease");
        //arrow keys
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upRelease");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftRelease");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightRelease");
    }

    //sets actionMap values for keybindings in GamePanel
    public void setActionMap() {
        //exit
        gamePanel.getActionMap().put("exit", exitAction);
        //movement
        gamePanel.getActionMap().put("up", upAction);
        gamePanel.getActionMap().put("upRelease", upActionRelease);
        gamePanel.getActionMap().put("left", leftAction);
        gamePanel.getActionMap().put("leftRelease", leftActionRelease);
        gamePanel.getActionMap().put("right", rightAction);
        gamePanel.getActionMap().put("rightRelease", rightActionRelease);
    }

}
