import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseMotionListener {

    private int x, y;

    @Override
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    public int[] getPos() {
        return new int[]{x, y};
    }
}
