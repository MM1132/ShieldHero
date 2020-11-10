import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageLoader {

    // THe directory of where the game was launched from.
    private static String path = System.getProperty("user.dir").toString() + "\\resources\\textures\\";

    public ImageLoader(){

    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new FileInputStream(ImageLoader.path + path));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
