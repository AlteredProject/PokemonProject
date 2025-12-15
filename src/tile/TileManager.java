package tile;

import entity.Player;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumBackground;
    public int[][] mapTileNumEnvironmentB;
    public int[][] mapTileNumEnvironmentF;
    Player player;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[338];
        mapTileNumBackground = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapTileNumEnvironmentB = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapTileNumEnvironmentF = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/mapBackground.csv", mapTileNumBackground);
        loadMap("/maps/mapEnvironmentB.csv", mapTileNumEnvironmentB);
        loadMap("/maps/mapEnvironmentF.csv", mapTileNumEnvironmentF);
    }

    public void getTileImage() {
        Set<Integer> collisionTiles = Set.of(
                12, 45, 46, 47, 48, 50, 52, 53, 54, 55, 56, 57, 58, 59, 60, 62, 63, 64, 65, 68,
                69, 72, 73, 74, 77, 78, 81, 82, 83, 90, 92, 93, 94, 95, 96, 97, 98, 99, 101, 102,
                103, 104, 105, 106, 107, 108, 109, 110, 113, 115, 116, 126, 127, 128, 129, 130, 131,
                132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148,
                149, 150, 151, 152, 153, 154, 156, 157, 158, 160, 161, 171, 172, 173, 174, 175, 176,
                177, 178, 179, 180, 188, 189, 197, 198, 202, 206, 207, 208, 209, 210, 212, 213, 214,
                215, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 241, 242, 243, 250, 251,
                252, 254, 257, 258, 259, 260, 261, 262, 264, 265, 270, 273, 274, 275, 276, 277, 278,
                284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 301, 302, 304,
                305, 306, 310, 311, 312, 313, 314, 315, 316, 319, 320, 321, 322, 323, 324, 326, 327,
                328, 329, 330, 331, 332, 333, 335
        );
        Set<Integer> grassDetection = Set.of(
                2, 3
        );

        for (int i = 0; i <= 337; i++) {
            String fileName = String.format("tile%03d", i);
            boolean hasCollision = collisionTiles.contains(i);
            boolean isGrass = grassDetection.contains(i);
            setup(i, fileName, hasCollision, isGrass);
        }
    }

    public void setup(int index, String imagePath, boolean collision, boolean isGrass) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            tile[index].isGrass = isGrass;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int[][] mapArray) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(", ");

                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapArray[col][row] = num;
                    col++;
                }

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        br.close();
    } catch(Exception e){
        e.printStackTrace();
    }
}



    public void drawLayer(Graphics2D g2, int[][] map) {
        int cameraLeft = gp.player.worldX - gp.player.screenX;
        int cameraTop = gp.player.worldY - gp.player.screenY;
        int cameraRight = cameraLeft + gp.screenWidth;
        int cameraBottom = cameraTop + gp.screenHeight;

        for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
            for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {

                int tileNum = map[worldCol][worldRow];
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;

                if (worldX + gp.tileSize >= cameraLeft &&
                        worldX <= cameraRight &&
                        worldY + gp.tileSize >= cameraTop &&
                        worldY <= cameraBottom) {

                    int screenX = worldX - cameraLeft;
                    int screenY = worldY - cameraTop;

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }
}
