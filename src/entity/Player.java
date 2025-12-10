package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    boolean moving = false;
    boolean sprinting = false;
    int pixelCounter = 0;

    int[] spriteOrder = { 1, 2, 1, 3 };
    int orderIndex = 0;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (entitySize / 2);
        screenY = gp.screenHeight / 2 - (entitySize / 2);

        solidArea = new Rectangle();
        solidArea.x = 33;
        solidArea.y = 49;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 62;
        solidArea.height = 62;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = (gp.tileSize * 19) + 32;  // WORLD START POS
        worldY = (gp.tileSize * 52) + 16;  // =o=
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = setup("/player/walk_up_1");
        up2 = setup("/player/walk_up_2");
        up3 = setup("/player/walk_up_3");

        left1 = setup("/player/walk_left_1");
        left2 = setup("/player/walk_left_2");
        left3 = setup("/player/walk_left_3");

        down1 = setup("/player/walk_down_1");
        down2 = setup("/player/walk_down_2");
        down3 = setup("/player/walk_down_3");

        right1 = setup("/player/walk_right_1");
        right2 = setup("/player/walk_right_2");
        right3 = setup("/player/walk_right_3");

    }

    public void update() {

        if (moving == false) {
            if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }

                sprinting = keyH.shiftPressed;

                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objectIndex = gp.cChecker.checkObject(this, true);


                // CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        int move;
        if (moving) {
            if (sprinting) {
                move = speed*2;
            } else {
                move = speed;
            }
            if (!collisionOn) {
                if(pixelCounter + move > gp.tileSize) {
                    move = gp.tileSize - pixelCounter;
                }
                switch (direction) {
                    case "up" -> worldY -= move;
                    case "left" -> worldX -= move;
                    case "down" -> worldY += move;
                    case "right" -> worldX += move;
                }
            }

            spriteCounter++;

            if (spriteCounter > 10) {
                orderIndex = (orderIndex + 1) % spriteOrder.length;
                spriteNum = spriteOrder[orderIndex];
                spriteCounter = 0;
            }

            pixelCounter += move;

            if(pixelCounter >= gp.tileSize) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999 ) {
            System.out.println("You are hitting npc");
        }

    }

    public void draw(Graphics2D g2) {
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
        g2.drawImage(image, screenX, screenY-8, null);
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }


}
