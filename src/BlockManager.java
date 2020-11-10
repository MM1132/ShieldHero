import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Graphics;
import java.util.Arrays;

public class BlockManager {

    private Block[][] blocks;

    public BlockManager(int dimensions[], JSONObject blocks) {
        this.blocks = new Block[dimensions[0]][dimensions[1]];

        // ### WALLS
        for(Object wall: (JSONArray)blocks.get("wall")) {
            JSONArray pos = (JSONArray)((JSONObject)wall).get("pos");
            int x = Integer.parseInt(pos.get(0).toString());
            int y = Integer.parseInt(pos.get(1).toString());
            this.addBlock(new int[]{y, x}, new Wall(new int[]{x, y}));
        }

        // ### GRASS
        for(Object grass: (JSONArray)blocks.get("grass")) {
            JSONArray pos = (JSONArray)((JSONObject)grass).get("pos");
            int x = Integer.parseInt(pos.get(0).toString());
            int y = Integer.parseInt(pos.get(1).toString());
            this.addBlock(new int[]{y, x}, new Grass(new int[]{x, y}));
        }

        // BIT-MAPPING
        this.bitMapAll();
    }

    public void addBlock(int[] pos, Block block) {
        this.blocks[pos[0]][pos[1]] = block;
    }

    public Block getBlock(int x, int y) {
        // Array index out of range
        if(x < 0 || x > this.blocks[0].length - 1) {
            return null;
        }
        if(y < 0 || y > this.blocks.length - 1) {
            return null;
        }

        return this.blocks[y][x];
    }

    private int getAt(int i, int j) {
        try {
            if(this.blocks[i][j] != null) {
                return 1;
            } else {
                return 0;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private int bitMapAt(int i, int j) {
        int direction[] = new int[4];

        direction[0] = this.getAt(i - 1, j);
        direction[1] = this.getAt(i, j - 1);
        direction[2] = this.getAt(i + 1, j);
        direction[3] = this.getAt(i, j + 1);

        String binary = Arrays.toString(direction)
            .replaceAll(" ", "")
            .replaceAll(",", "")
            .replaceAll("\\[", "")
            .replaceAll("]", "");

        int texture = Integer.parseInt(binary, 2);
        return texture;
    }

    private void bitMapAll() {
        for(int i = 0; i < this.blocks.length; i++) {
            for(int j = 0; j < this.blocks[i].length; j++) {
                if(this.blocks[i][j] != null) {
                    this.blocks[i][j].setTexture(this.bitMapAt(i, j));
                }
            }
        }
    }

    public void render(Graphics g, double pos[]) {
        for(int i = 0; i < this.blocks.length; i++) {
            for(int j = 0; j < this.blocks[i].length; j++) {
                if(this.blocks[i][j] != null) {
                    this.blocks[i][j].render(g, pos);
                }
            }
        }
    }
}