import java.awt.Graphics;

public class Wall extends Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("brick_wall_64x.png"), 64, 64);
    private static boolean solid = true;

    public Wall(int pos[]) {
        super(pos);
    }

    public SpriteSheet getSheet() {
        return Wall.sheet;
    }

    public boolean isSolid() {
        return Wall.solid;
    }

}