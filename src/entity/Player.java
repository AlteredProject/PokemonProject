package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    boolean moving = false;
    boolean sprinting = false;
    int pixelCounter = 0;

    final int originalPlayerSize = 32;  // 32x32 px
    public final int playerSize = originalPlayerSize * 4; // 128x128 px

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (playerSize / 2);
        screenY = gp.screenHeight / 2 - (playerSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 33;
        solidArea.y = 49;
        solidArea.width = 62;
        solidArea.height = 62;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = (gp.tileSize * 10) + 32;  // WORLD START POS
        worldY = (gp.tileSize * 6) + 16;  // =o=
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Up_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Up_4.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Left_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Left_4.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Down_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Down_4.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Right_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk_Right_4.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            pixelCounter += move;

            if(pixelCounter >= gp.tileSize) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage[] up = {up1, up2, up3, up4};
        BufferedImage[] left = {left1, left2, left3, left4};
        BufferedImage[] down = {down1, down2, down3, down4};
        BufferedImage[] right = {right1, right2, right3, right4};

        switch (direction) {
            case "up" -> image = up[spriteNum - 1];
            case "left" -> image = left[spriteNum - 1];
            case "down" -> image = down[spriteNum - 1];
            case "right" -> image = right[spriteNum - 1];
        }
        g2.drawImage(image, screenX, screenY-8, playerSize, playerSize, null);
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
