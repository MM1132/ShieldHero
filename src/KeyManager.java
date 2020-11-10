import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys = new boolean[525];
    private boolean closeGame = false;

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;

        if(this.keys[KeyEvent.VK_ESCAPE]) {
            this.closeGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean getWasd()[] {
        return new boolean[] {this.keys[KeyEvent.VK_W], this.keys[KeyEvent.VK_A], this.keys[KeyEvent.VK_S], this.keys[KeyEvent.VK_D]};
    }

    public boolean getCloseGame() {
        return this.closeGame;
    }

}
