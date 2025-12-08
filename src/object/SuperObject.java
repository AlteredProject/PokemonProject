package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {
        int cameraLeft   = gp.player.worldX - gp.player.screenX;
        int cameraTop    = gp.player.worldY - gp.player.screenY;
        int cameraRight  = cameraLeft + gp.screenWidth;
        int cameraBottom = cameraTop  + gp.screenHeight;

        if (worldX + gp.tileSize >= cameraLeft &&
                worldX <= cameraRight &&
                worldY + gp.tileSize >= cameraTop &&
                worldY <= cameraBottom) {

            int screenX = worldX - cameraLeft;
            int screenY = worldY - cameraTop;

            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
