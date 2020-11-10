import java.awt.Graphics;

public class Dirt extends Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("dirt.png"), 50, 50);
    private static boolean solid = true;

    public Dirt(int pos[]) {
        super(pos);
    }

    public SpriteSheet getSheet() {
        return Dirt.sheet;
    }

    public boolean getSolid() {
        return this.solid;
    }

}