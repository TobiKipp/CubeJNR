package control;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import model.Cube;
import model.Vector3;

/**
 * Created by Tobias Kipp on 2014-10-24.
 */
public class KeyboardInputController implements KeyListener {
    private WorldManager worldManager;

    public KeyboardInputController(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == KeyEvent.VK_LEFT) {
            this.worldManager.keyPressed("left");
        } else if (keycode == KeyEvent.VK_RIGHT) {
            this.worldManager.keyPressed("right");
        } else if (keycode == KeyEvent.VK_UP) {
            this.worldManager.keyPressed("up");
        } else if (keycode == KeyEvent.VK_DOWN) {
            this.worldManager.keyPressed("down");
        } else if (keycode == KeyEvent.VK_PAGE_UP) {
            this.worldManager.keyPressed("page up");
        } else if (keycode == KeyEvent.VK_PAGE_DOWN) {
            this.worldManager.keyPressed("page down");
        } else if (keycode == KeyEvent.VK_HOME) {
            this.worldManager.keyPressed("home");
        } else if (keycode == KeyEvent.VK_END) {
            this.worldManager.keyPressed("end");
        } else if (keycode == KeyEvent.VK_INSERT) {
            this.worldManager.keyPressed("insert");
        } else if (keycode == KeyEvent.VK_DELETE) {
            this.worldManager.keyPressed("delete");
        } else if (keycode == KeyEvent.VK_PLUS) {
            this.worldManager.keyPressed("plus");
        } else if (keycode == KeyEvent.VK_MINUS) {
            this.worldManager.keyPressed("minus");
        }
        
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        if (keycode == KeyEvent.VK_LEFT) {
            this.worldManager.keyReleased("left");
        } else if (keycode == KeyEvent.VK_RIGHT) {
            this.worldManager.keyReleased("right");
        } else if (keycode == KeyEvent.VK_UP) {
            this.worldManager.keyReleased("up");
        } else if (keycode == KeyEvent.VK_DOWN) {
            this.worldManager.keyReleased("down");
        } else if (keycode == KeyEvent.VK_PAGE_UP) {
            this.worldManager.keyReleased("page up");
        } else if (keycode == KeyEvent.VK_PAGE_DOWN) {
            this.worldManager.keyReleased("page down");
        } else if (keycode == KeyEvent.VK_HOME) {
            this.worldManager.keyReleased("home");
        } else if (keycode == KeyEvent.VK_END) {
            this.worldManager.keyReleased("end");
        } else if (keycode == KeyEvent.VK_INSERT) {
            this.worldManager.keyReleased("insert");
        } else if (keycode == KeyEvent.VK_DELETE) {
            this.worldManager.keyReleased("delete");
        } else if (keycode == KeyEvent.VK_PLUS) {
            this.worldManager.keyReleased("plus");
        } else if (keycode == KeyEvent.VK_MINUS) {
            this.worldManager.keyReleased("minus");
        }
    }

}
