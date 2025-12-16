package entity;

import java.util.Random;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;
    private Random random = new Random();
    public final int screenX;
    public final int screenY;
    private boolean hasChecked;
    int standCounter = 0;
    public boolean moving = false;
    boolean sprinting = false;
    int pixelCounter = 0;
    public int chance = 0;
    int[] spriteOrder = {1, 2, 1, 3};
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
        worldY = (gp.tileSize * 53) + 16;  // =o=
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        String gender = switch (gp.genderState) {
            case 1 -> "female";
            case 2 -> "male";
            default -> throw new IllegalStateException("No gender with the genderSate: " + gp.genderState);
        };

        String path = "/player/" + gender + "/";

        up1 = setup(path + "walk_up_1");
        up2 = setup(path + "walk_up_2");
        up3 = setup(path + "walk_up_3");
        left1 = setup(path + "walk_left_1");
        left2 = setup(path + "walk_left_2");
        left3 = setup(path + "walk_left_3");
        down1 = setup(path + "walk_down_1");
        down2 = setup(path + "walk_down_2");
        down3 = setup(path + "walk_down_3");
        right1 = setup(path + "walk_right_1");
        right2 = setup(path + "walk_right_2");
        right3 = setup(path + "walk_right_3");
    }

    public void update() {
        if (keyH.ePressed) {
            int npcIndex = gp.cChecker.checkEntityInteraction(this, gp.npc);
            interactNPC(npcIndex);
        }

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
                hasChecked = false;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objectIndex = gp.cChecker.checkObject(this, true);


                // CHECK NPC COLLISION
                gp.cChecker.checkEntityCollision(this, gp.npc);
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
            gp.cChecker.checkGrass(this);
            if (sprinting) {
                move = speed * 2;
            } else {
                move = speed;
            }
            if (!collisionOn) {
                if (pixelCounter + move > gp.tileSize) {
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

            if (pixelCounter >= gp.tileSize) {
                moving = false;
                pixelCounter = 0;
                if (isGrassOn && hasChecked == false) {
                    chance = random.nextInt(10);
                    if (chance == 1) {
                        gp.ui.drawBattleIntro();
                        if (gp.ui.getGrassFadeCounter() > 100) {
                            gp.startWildBattle();
                            gp.ui.setGrassFadeCounter(0);
                        }
                    } else {
                        hasChecked = true;
                    }
                }

            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        } else {
            keyH.ePressed = false;
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
        g2.drawImage(image, screenX, screenY - 8, null);
    }


}
