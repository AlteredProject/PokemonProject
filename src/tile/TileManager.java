package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumBackground;
    public int[][] mapTileNumEnvironment;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[86];
        mapTileNumBackground = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapTileNumEnvironment = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/mapBackground.csv", mapTileNumBackground);
        loadMap("/maps/mapEnvironment.csv", mapTileNumEnvironment);
    }

    public void getTileImage() {

            // ========== BACKGROUND ==========
            setup(0, "Background/(00) Blank", false);
            setup(1, "Background/(01) Grass", false);
            setup(2, "Background/(02) TallGrass", false);
            setup(3, "Background/(03) TallGrass-Dirt", false);
            setup(4, "Background/(04) Flowers-White", false);
            setup(5, "Background/(05) Flowers-Colored", false);
            setup(6, "Background/(06) DirtPath-TopLeft", false);
            setup(7, "Background/(07) DirtPath-TopMiddle", false);
            setup(8, "Background/(08) DirtPath-TopRight", false);
            setup(9, "Background/(09) MudPath-TopLeft", false);
            setup(10, "Background/(10) MudPath-TopMiddle", false);
            setup(11, "Background/(11) MudPath-TopRight", false);
            setup(13, "Background/(13) DirtPath-CornerBottomRight", false);
            setup(14, "Background/(14) DirtPath-CornerBottomLeft", false);
            setup(15, "Background/(15) DirtPath-MiddleLeft", false);
            setup(16, "Background/(16) DirtPath-Center", false);
            setup(17, "Background/(17) DirtPath-MiddleRight", false);
            setup(18, "Background/(18) MudPath-MiddleLeft", false);
            setup(19, "Background/(19) MudPath-Center", false);
            setup(20, "Background/(20) MudPath-MiddleRight", false);
            setup(21, "Background/(21) MudPath-CenterDetail", false);
            setup(22, "Background/(22) DirtPath-CornerTopRight", false);
            setup(23, "Background/(23) DirtPath-CornerTopLeft", false);
            setup(24, "Background/(24) DirtPath-BottomLeft", false);
            setup(25, "Background/(25) DirtPath-BottomMiddle", false);
            setup(26, "Background/(26) DirtPath-BottomRight", false);
            setup(27, "Background/(27) MudPath-BottomLeft", false);
            setup(28, "Background/(28) MudPath-BottomMiddle", false);
            setup(29, "Background/(29) MudPath-BottomRight", false);
            setup(30, "Background/(30) MudPath-CornerBottomRight", false);
            setup(31, "Background/(31) MudPath-CornerBottomLeft", false);
            setup(32, "Background/(32) TransitionUp-TopLeft", false);
            setup(33, "Background/(33) TransitionUp-TopRight", false);
            setup(34, "Background/(34) TransitionDown-TopLeft", false);
            setup(35, "Background/(35) TransitionDown-TopRight", false);
            setup(39, "Background/(39) MudPath-CornerTopRight", false);
            setup(40, "Background/(40) MudPath-CornerTopLeft", false);
            setup(41, "Background/(41) TransitionUp-BottomLeft", false);
            setup(42, "Background/(42) TransitionUp-BottomRight", false);
            setup(43, "Background/(43) TransitionDown-BottomLeft", false);
            setup(44, "Background/(44) TransitionDown-BottomRight", false);
            setup(65, "Background/(65) Water-InnerTopLeft", true);
            setup(66, "Background/(66) Water-Top", true);
            setup(67, "Background/(67) Water-InnerTopRight", true);
            setup(68, "Background/(68) Water-OuterTopLeft", true);
            setup(69, "Background/(69) Water-OuterTopRight", true);
            setup(74, "Background/(74) Water-Left", true);
            setup(76, "Background/(76) Water-Right", true);
            setup(77, "Background/(77) Water-OuterBottomLeft", true);
            setup(78, "Background/(78) Water-OuterBottomRight", true);
            setup(83, "Background/(83) Water-InnerBottomLeft", true);
            setup(84, "Background/(84) Water-Bottom", true);
            setup(85, "Background/(85) Water-InnerBottomRight", true);

            // ========== ENVIRONMENT ==========
            setup(12, "Environment/(12) Sign", true);
            setup(36, "Environment/(36) Tree-TopLeft", false);
            setup(37, "Environment/(37) Tree-TopMiddle", false);
            setup(38, "Environment/(38) Tree-TopRight", false);
            setup(45, "Environment/(45) Tree-MiddleLeft", true);
            setup(46, "Environment/(46) Tree-Center", true);
            setup(47, "Environment/(47) Tree-MiddleRight", true);
            setup(48, "Environment/(48) TreeConnector-VerticalLeft", true);
            setup(49, "Environment/(49) TreeConnector-VerticalMiddle", false);
            setup(50, "Environment/(50) TreeConnector-VerticalRight", true);
            setup(51, "Environment/(51) TreeConnector-HorizontalTop", false);
            setup(52, "Environment/(52) TreeConnector-BottomRight", true);
            setup(53, "Environment/(53) TreeConnector-BottomLeft", true);
            setup(54, "Environment/(54) Tree-BottomLeft", true);
            setup(55, "Environment/(55) Tree-BottomMiddle", true);
            setup(56, "Environment/(56) Tree-BottomRight", true);
            setup(57, "Environment/(57) TreeConnector-HorizontalMiddle", true);
            setup(58, "Environment/(58) TreeConnector-HorizontalBottom", true);
            setup(59, "Environment/(59) TreeConnector-TopLeft", true);
            setup(60, "Environment/(60) TreeConnector-TopRight", true);
            setup(61, "Environment/(61) TreeConnector-Center", false);
            setup(62, "Environment/(62) Rock-Small", true);
            setup(63, "Environment/(63) Rock-TopLeft", true);
            setup(64, "Environment/(64) Rock-TopRight", true);
            setup(70, "Environment/(70) Bikestand", true);
            setup(72, "Environment/(72) Rock-BottomLeft", true);
            setup(73, "Environment/(73) Rock-BottomRight", true);
    }

    public void setup(int index, String imagePath, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
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

                while(col < gp.maxWorldCol) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawLayer(Graphics2D g2, int[][] map) {
        int cameraLeft   = gp.player.worldX - gp.player.screenX;
        int cameraTop    = gp.player.worldY - gp.player.screenY;
        int cameraRight  = cameraLeft + gp.screenWidth;
        int cameraBottom = cameraTop  + gp.screenHeight;

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
