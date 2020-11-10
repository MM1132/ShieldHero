import java.awt.Graphics;

public class Ice extends Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("ice.png"), 50, 50);

    public Ice(int pos[]) {
        super(pos);
    }

    public SpriteSheet getSheet() {
        return Ice.sheet;
    }

}