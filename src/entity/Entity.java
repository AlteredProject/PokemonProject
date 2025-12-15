package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, up3, left1, left2, left3, down1, down2, down3, right1, right2, right3;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    final int originalEntitySize = 32;  // 32x32 px
    public final int entitySize = originalEntitySize * 4; // 128x128 px

    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean isGrassOn = false;

    String[] dialogues = new String[25];
    int dialogueIndex = 0;

    public Entity (GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {} // Might be used later for NPC interaction
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    public void update() {}


    public void draw(Graphics2D g2) {
        int cameraLeft   = gp.player.worldX - gp.player.screenX;
        int cameraTop    = gp.player.worldY - gp.player.screenY;
        int cameraRight  = cameraLeft + gp.screenWidth;
        int cameraBottom = cameraTop  + gp.screenHeight;

        if (worldX + gp.tileSize >= cameraLeft &&
                worldX <= cameraRight &&
                worldY + gp.tileSize >= cameraTop &&
                worldY <= cameraBottom) {

            int screenX = worldX - cameraLeft - 32;  // Sprite offset fixes
            int screenY = worldY - cameraTop - 56; // =o=

            BufferedImage image = null;
            BufferedImage[] up = {up1, up2, up3};
            BufferedImage[] left = {left1, left2, left3};
            BufferedImage[] down = {down1, down2, down3};
            BufferedImage[] right = {right1, right2, right3};

            switch (direction) {
                case "up" -> image = up[spriteNum - 1];
                case "left" -> image = left[spriteNum - 1];
                case "down" -> image = down[spriteNum - 1];
                case "right" -> image = right[spriteNum - 1];
            }

            g2.drawImage(image, screenX, screenY, entitySize, entitySize, null);
        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, entitySize, entitySize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
