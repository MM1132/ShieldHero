import java.awt.Graphics;

public class Stone extends Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("stone.png"), 50, 50);

    public Stone(int pos[]) {
        super(pos);
    }

    public SpriteSheet getSheet() {
        return Stone.sheet;
    }

}