import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Player {

    private double[] pos = new double[2];
    private double[] lastPos = new double[2];
    private double speed = 1.2;
    private double diagonalSpeed = this.speed / Math.sqrt(2);
    private double[] velocity = new double[2];
    private double rotation = 0;
    private BufferedImage characterImage;

    public Player(int[] pos) {
        this.pos[0] = pos[0] * 64;
        this.pos[1] = pos[1] * 64;

        this.characterImage = ImageLoader.loadImage("shield.png");
    }

    public void update(Handler handler) {

        boolean[] wasd = handler.getGame().getKeyManager().getWasd();
        int[] mousePos = handler.getGame().getMouseManager().getPos();

        this.lastPos[0] = this.pos[0];
        this.lastPos[1] = this.pos[1];

        // FOUR
        if(wasd[0] && wasd[1] && wasd[2] && wasd[3]) {
            this.velocity[0] += this.speed;
        }

        // THREE
        else if(wasd[1] && wasd[0] && wasd[3]) {
            this.velocity[1] -= this.speed;
        }
        else if(wasd[0] && wasd[3] && wasd[2]) {
            this.velocity[0] += this.speed;
        }
        else if(wasd[3] && wasd[2] && wasd[1]) {
            this.velocity[1] += this.speed;
        }
        else if(wasd[2] && wasd[1] && wasd[0]) {
            this.velocity[0] -= this.speed;
        }
        // TWO
        else if(wasd[0] && wasd[2]) {
            this.velocity[1] -= this.speed;
        }
        else if(wasd[1] && wasd[3]) {
            this.velocity[0] += this.speed;
        }
        // DIAGONAL
        else if(wasd[0] && wasd[1]) {
            this.velocity[0] -= this.diagonalSpeed;
            this.velocity[1] -= this.diagonalSpeed;
        }
        else if(wasd[1] && wasd[2]) {
            this.velocity[0] -= this.diagonalSpeed;
            this.velocity[1] += this.diagonalSpeed;
        }
        else if(wasd[2] && wasd[3]) {
            this.velocity[0] += this.diagonalSpeed;
            this.velocity[1] += this.diagonalSpeed;
        }
        else if(wasd[3] && wasd[0]) {
            this.velocity[0] += this.diagonalSpeed;
            this.velocity[1] -= this.diagonalSpeed;
        }
        // ONE
        else if(wasd[0]) {
            this.velocity[1] -= this.speed;
        }
        else if(wasd[1]) {
            this.velocity[0] -= this.speed;
        }
        else if(wasd[2]) {
            this.velocity[1] += this.speed;
        }
        else if(wasd[3]) {
            this.velocity[0] += this.speed;
        }

        // PERFORM THE MOVEMENT
        int[][] points = {{(int)(this.pos[0] / 64), (int)(this.pos[1] / 64)},
                             {(int)((this.pos[0] + 50) / 64), (int)(this.pos[1] / 64)},
                             {(int)((this.pos[0] + 50) / 64), (int)((this.pos[1] + 50) / 64)},
                             {(int)(this.pos[0] / 64), (int)((this.pos[1] + 50) / 64)}
        };

        Block top_right = handler.getGame().getWorld().getBlock(points[1][0], points[1][1]);
        Block bottom_right = handler.getGame().getWorld().getBlock(points[2][0], points[2][1]);
        Block top_left = handler.getGame().getWorld().getBlock(points[0][0], points[0][1]);
        Block bottom_left = handler.getGame().getWorld().getBlock(points[3][0], points[3][1]);

        this.moveX(handler, top_right, bottom_right, top_left, bottom_left);
        this.moveY(handler, top_right, bottom_right, top_left, bottom_left);

        // FACING DIRECTION CALCULATION
        double offsetX = this.pos[0] - handler.getGame().getCamera().getPos()[0] - mousePos[0] + this.getWidth() / 2;
        double offsetY = this.pos[1] - handler.getGame().getCamera().getPos()[1] - mousePos[1] + this.getHeight() / 2;
        this.rotation = Math.PI - Math.atan(offsetX / offsetY);
        if(mousePos[1] > this.pos[1] - handler.getGame().getCamera().getPos()[1] + this.getHeight() / 2) {
            this.rotation -= Math.PI;
        }
    }

    private void moveX(Handler handler, Block top_right, Block bottom_right, Block top_left, Block bottom_left) {
        this.lastPos[0] = this.pos[0];
        this.pos[0] += this.velocity[0];
        this.velocity[0] *= 0.8;

        if(this.velocity[0] > 0) {
            // Upper right corner
            if(top_right != null) {
                if(top_right.isSolid()) {
                    this.pos[0] = this.lastPos[0];
                    this.velocity[0] = 0;
                }
            }

            // Bottom right corner
            if(bottom_right != null) {
                if(bottom_right.isSolid()) {
                    this.pos[0] = this.lastPos[0];
                    this.velocity[0] = 0;
                }
            }


        } else if(this.velocity[0] < 0) {
            // Top left corner
            if(top_left != null) {
                if(top_left.isSolid()) {
                    this.pos[0] = this.lastPos[0];
                    this.velocity[0] = 0;
                }
            }

            // Bottom left corner
            if(bottom_left != null) {
                if(bottom_left.isSolid()) {
                    this.pos[0] = this.lastPos[0];
                    this.velocity[0] = 0;
                }
            }
        }
    }

    private void moveY(Handler handler, Block top_right, Block bottom_right, Block top_left, Block bottom_left) {
        this.lastPos[1] = this.pos[1];
        this.pos[1] += this.velocity[1];
        this.velocity[1] *= 0.8;

        if(this.velocity[1] > 0) {
            // Bottom right corner
            if(bottom_right != null) {
                if(bottom_right.isSolid()) {
                    this.pos[1] = this.lastPos[1];
                    this.velocity[1] = 0;
                }
            }

            // Bottom left corner
            if(bottom_left != null) {
                if(bottom_left.isSolid()) {
                    this.pos[1] = this.lastPos[1];
                    this.velocity[1] = 0;
                }
            }


        } else if(this.velocity[1] < 0) {
            // Top right corner
            if(top_right != null) {
                if(top_right.isSolid()) {
                    this.pos[1] = this.lastPos[1];
                    this.velocity[1] = 0;
                }
            }

            // Top left corner
            if(top_left != null) {
                if(top_left.isSolid()) {
                    this.pos[1] = this.lastPos[1];
                    this.velocity[1] = 0;
                }
            }
        }
    }

    public void render(Handler handler) {
        Graphics2D g2d = (Graphics2D)handler.getGame().getGraphics();

        double renderX = this.pos[0] - handler.getGame().getCamera().getPos()[0];
        double renderY = this.pos[1] - handler.getGame().getCamera().getPos()[1];

        AffineTransform at = AffineTransform.getTranslateInstance(renderX, renderY);

        at.rotate(this.rotation, this.getWidth() / 2, this.getHeight() / 2);
        g2d.drawImage(this.characterImage, at, null);
    }

    public double[] getPos(){
        return this.pos;
    }

    public double getWidth() {
        return this.characterImage.getWidth();
    }

    public double getHeight() {
        return this.characterImage.getHeight();
    }
}