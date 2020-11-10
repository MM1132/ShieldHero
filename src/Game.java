import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private World world;
    private Player player;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    private Handler handler;
    private Camera camera;

    private Window window;
    private Graphics g;
    private BufferStrategy bs;
    private Thread thread;
    private boolean running;

    public Game(String title) {
        this.running = true;

        this.world = new World("another_world.json");
        this.player = new Player(this.world.randomSpawn());

        window = new Window(title);

        this.keyManager = new KeyManager();
        window.getFrame().addKeyListener(this.keyManager);

        this.mouseManager = new MouseManager();
        window.getCanvas().addMouseMotionListener(this.mouseManager);

        this.handler = new Handler(this);
        this.camera = new Camera(this.player.getPos(), new double[]{this.getWidth() / 2 - this.player.getWidth() / 2, this.getHeight() / 2 - this.player.getHeight() / 2});

        this.thread = new Thread(this);
        this.thread.start();
    }

    private void update() {
        this.player.update(this.handler);
        this.camera.update();

        if(this.keyManager.getCloseGame()) {
            System.exit(0);
        }
    }

    public void render() {
        this.bs = this.window.getCanvas().getBufferStrategy();
        if(this.bs == null) {
            this.window.getCanvas().createBufferStrategy(2);
        } else {
            this.g = this.bs.getDrawGraphics();
            this.g.clearRect(0, 0, this.window.getFrame().getWidth(), this.window.getFrame().getHeight());

            this.world.render(this.handler);
            this.player.render(this.handler);

            this.bs.show();
            this.g.dispose();
        }
    }

    private void stop() {
        this.running = false;
        try {
            this.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        int fps = 60;
        double tpt = (double)1000000000 / 60;
        double delta = 0;

        long timer = 0;
        long now;
        long last = 0;
        int ticks = 0;

        while(this.running) {
            now = System.nanoTime();
            timer += now - last;
            delta += (double)(now - last) / tpt;
            last = now;

            if(delta >= 1) {
                this.update();
                this.render();
                ticks++;
                delta = 0;
            }

            if(timer >= 1000000000) {
                //System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
        }
        this.stop();
    }

    public int getWidth() {
        return this.window.getFrame().getWidth();
    }

    public int getHeight() {
        return this.window.getFrame().getHeight();
    }

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    public MouseManager getMouseManager() {
        return this.mouseManager;
    }

    public Graphics getGraphics() {
        return this.g;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public World getWorld() {
        return this.world;
    }
}
