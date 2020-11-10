import javax.swing.JFrame;
import java.awt.*;

public class Window {

    private JFrame frame;
    private Canvas canvas;
    private int width, height;

    public Window(String title) {
        this.frame = new JFrame(title);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setUndecorated(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        this.width = this.frame.getWidth();
        this.height = this.frame.getHeight();

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(new Dimension(width, height));
        this.canvas.setMinimumSize(new Dimension(width, height));
        this.canvas.setMaximumSize(new Dimension(width, height));
        this.canvas.setFocusable(false);

        this.frame.add(this.canvas);
        this.frame.pack();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}