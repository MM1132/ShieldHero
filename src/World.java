import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import org.json.simple.parser.JSONParser;
import java.lang.Integer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class World {

    private int[] dimensions;
    private int[][] playerSpawns;
    private BlockManager blockManager;

    private BufferedImage backgroundImage;

    private static final String path = System.getProperty("user.dir").toString() + "\\resources\\worlds\\";

    public World(String path) {
        try{

            // OBJECT FOR READING JSON
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(new FileReader(World.path + path));

            // ### DIMENSIONS
            this.dimensions = new int[]{Integer.parseInt(jsonObject.get("width").toString()), Integer.parseInt(jsonObject.get("height").toString())};

            // ### PLAYER_SPAWNS
            JSONArray spawns = (JSONArray)jsonObject.get("playerspawns");
            this.playerSpawns = new int[spawns.size()][2];
            for(int i = 0; i < spawns.size(); i++) {
                this.playerSpawns[i][0] = Integer.parseInt(((JSONArray)spawns.get(i)).get(0).toString());
                this.playerSpawns[i][1] = Integer.parseInt(((JSONArray)spawns.get(i)).get(1).toString());
            }

            // ### BACKGROUND
            String background = (String)jsonObject.get("background");
            this.backgroundImage = ImageLoader.loadImage(background);

            // ### BLOCKS
            JSONObject blocks = ((JSONObject)jsonObject.get("blocks"));
            this.blockManager = new BlockManager(this.dimensions, blocks);



        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ParseException pe) {
            pe.printStackTrace();
        }
    }

    public void render(Handler handler) {
        int backgroundWidth = this.backgroundImage.getWidth();
        int backgroundHeight = this.backgroundImage.getHeight();

        int width = handler.getGame().getWidth();
        int height = handler.getGame().getHeight();

        double[] cameraPos = handler.getGame().getCamera().getPos();

        for(int i = -1; i < (width / backgroundWidth) + 2; i++) {
            for(int j = -1; j < (height / backgroundHeight) + 2; j++) {
                double drawX = i * backgroundWidth - cameraPos[0] % backgroundWidth;
                double drawY = j * backgroundHeight - cameraPos[1] % backgroundHeight;
                handler.getGame().getGraphics().drawImage(this.backgroundImage, (int)drawX, (int)drawY, null);
            }
        }

        this.blockManager.render(handler.getGame().getGraphics(), cameraPos);

    }

    public int[] randomSpawn() {
        return this.playerSpawns[(int)Math.floor(Math.random() * this.playerSpawns.length)];
    }

    public Block getBlock(int x, int y) {
        return this.blockManager.getBlock(x, y);
    }

}
