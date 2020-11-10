public class Grass extends Block {

    private static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("grass.png"), 64, 64);
    private static boolean solid = false;

    public Grass(int[] pos) {
        super(pos);
    }

    public SpriteSheet getSheet() {
        return Grass.sheet;
    }

    public boolean isSolid() {
        return Grass.solid;
    }

}
