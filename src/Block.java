import java.awt.Graphics;
import java.util.Arrays;

public class Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("default.png"), 50, 50);
    private static boolean solid = true;

    protected int pos[];
    protected int texture;

    public Block(int[] pos) {
        this.pos = Arrays.copyOf(pos, 2);
    }

    public SpriteSheet getSheet() {
        return this.sheet;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public void setTexture(int index) {
        this.texture = index;
    }

    public void render(Graphics g, double cameraPos[]) {
        double x_pos = this.pos[0] * 64 - cameraPos[0];
        double y_pos = this.pos[1] * 64 - cameraPos[1];
        g.drawImage(this.getSheet().getSprite(this.texture), (int)x_pos, (int)y_pos, null);
    }
}
