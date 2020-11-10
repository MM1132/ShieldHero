import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

    private BufferedImage sheet;
    private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public SpriteSheet(BufferedImage sheet, int width, int height) {
        this.sheet = sheet;

        for(int i = 0; i < this.sheet.getWidth(); i += width) {
            this.sprites.add(this.sheet.getSubimage(i, 0, width, height));
        }
    }

    public void crop(int x, int y, int width, int height) {
        this.sprites.add(this.sheet.getSubimage(x, y, width, height));
    }

    public BufferedImage getSprite(int index) {
        if(this.sprites.size() > index + 1) {
            return this.sprites.get(index);
        } else {
            return this.sprites.get(0);
        }
    }
}
