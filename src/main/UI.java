package main;

import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    ClickHandler clickH;

    UtilityTool uTool = new UtilityTool();
    public BufferedImage dialogueWindowImage, pokedexBoy, pokedexGirl, pokedexIcon, searchButtonReleased, searchButtonPressed, previousButtonReleased, nextButtonReleased, previousButtonPressed, nextButtonPressed, onOffButton, titleScreenBackground, logo, opal, rowan, lucas, dawn;

    public Font pkmnFont;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";

    // === Area Icons ===
    Image[] areaIcons = new Image[10];
    String[] areaNames = new String[8];
    private int currentArea = -1;
    private long areaDisplayStartTime = 0;
    private static final long AREA_DISPLAY_DURATION = 3000; // 3 seconds
    int animatedIconY = -200;

    int resetter = 0;
    boolean display;

    public UI(GamePanel gp, ClickHandler clickH) {
        this.gp = gp;
        this.clickH = clickH;

        InputStream is = getClass().getResourceAsStream("/font/pkmnFont.ttf");
        try {
            pkmnFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getUIImages();
        getAreaIcons();
        getAreaNames();
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void getUIImages() {
        try {
            dialogueWindowImage = ImageIO.read(getClass().getResourceAsStream("/ui/dialogueBox.png"));
            uTool.scaleImage(dialogueWindowImage, 3, 3);
            pokedexBoy = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/boy.png"));
            pokedexGirl = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/girl.png"));
            pokedexIcon = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/pokedexIcon.png"));
            searchButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonGreen.png"));
            searchButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonOrange.png"));
            previousButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueLeft.png"));
            previousButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedLeft.png"));
            nextButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueRight.png"));
            nextButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedRight.png"));
            onOffButton = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/onOffButton.png"));
            titleScreenBackground = ImageIO.read(getClass().getResourceAsStream("/titleScreen/background.png"));
            logo = ImageIO.read(getClass().getResourceAsStream("/titleScreen/logo.png"));
            opal = ImageIO.read(getClass().getResourceAsStream("/titleScreen/opal.png"));
            rowan = ImageIO.read(getClass().getResourceAsStream("/titleScreen/rowan.png"));
            lucas = ImageIO.read(getClass().getResourceAsStream("/titleScreen/lucas.png"));
            dawn = ImageIO.read(getClass().getResourceAsStream("/titleScreen/dawn.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pkmnFont);
        g2.setColor(Color.white);

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPokedexIcon();
            drawAreaIcons();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // POKEDEX STATE
        if (gp.gameState == gp.pokedexState) {
            drawPokedexScreen();
        }
        // TITLE SCREEN STATE
        if (gp.gameState == gp.titleScreenState) {
            drawTitleScreen();
        }

    }

    public void drawPokedexIcon() {
        int x = 25;
        int y = 690;
        BufferedImage image = pokedexIcon;
        g2.drawImage(image, x, y, image.getWidth() * 2, image.getHeight() * 2, null);
    }

    // ===== PAUSE =====
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    // ===== DIALOGUE =====
    public void drawDialogueScreen() {
        // WINDOW
        int x = (gp.screenWidth - (dialogueWindowImage.getWidth() * 4)) / 2;
        int y = gp.screenHeight - (dialogueWindowImage.getHeight() * 4) - (gp.tileSize / 8);
        drawDialogueWindow(x, y, dialogueWindowImage);

        // TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26));
        x += gp.tileSize - 5;
        y += gp.tileSize + 10;
        g2.setColor(Color.black);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 55;
        }
    }

    public void drawDialogueWindow(int x, int y, BufferedImage image) {
        g2.drawImage(image, x, y, image.getWidth() * 4, image.getHeight() * 4, null);
    }

    // ===== POKEDEX =====
    public void drawPokedexScreen() {
        // POKEDEX
        int x = 0;
        int y = 0;
        drawPokedex(x, y, pokedexGirl, 1);

        // BUTTONS
        drawButtons();
    }

    public void drawPokedex(int x, int y, BufferedImage image, int genderState) {
        if (genderState == 1) {
            image = pokedexGirl;
        }
        if (genderState == 2) {
            image = pokedexBoy;
        }
        g2.drawImage(image, x, y, image.getWidth() * 4, image.getHeight() * 4, null);
    }

    public void drawButtons() {
        int pButtonX = 190;
        int buttonY = 576;
        int nButtonX = 398;
        int width = 147;
        int height = 64;
        int size = 48;
        int sButtonX = 245;

        if (clickH.previousButtonPressed) {
            g2.drawImage(previousButtonPressed, pButtonX, buttonY, size, size, null);
        } else {
            g2.drawImage(previousButtonReleased, pButtonX, buttonY, size, size, null);
        }

        if (clickH.searchButtonPressed) {
            g2.drawImage(searchButtonPressed, sButtonX, buttonY - 11, width, height, null);
        } else {
            g2.drawImage(searchButtonReleased, sButtonX, buttonY - 11, width, height, null);
        }

        if (clickH.nextButtonPressed) {
            g2.drawImage(nextButtonPressed, nButtonX, buttonY, size, size, null);
        } else {
            g2.drawImage(nextButtonReleased, nButtonX, buttonY, size, size, null);
        }
    }

    // ===== AREA ICONS =====
    public void getAreaIcons() {
        try {
            areaIcons[0] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneSmallCity.png"));
            areaIcons[1] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneOcean.png"));
            areaIcons[2] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneBeach.png"));
            areaIcons[3] = ImageIO.read(getClass().getResourceAsStream("/ui/zonePlains.png"));
            areaIcons[4] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneForest.png"));
            areaIcons[5] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneVillage.png"));
            areaIcons[6] = ImageIO.read(getClass().getResourceAsStream("/ui/zoneMountain.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAreaNames() {
        areaNames[0] = "Twinleaf Town";
        areaNames[1] = "Route 201";
        areaNames[2] = "Lake of Rage";
        areaNames[3] = "Floaroma Fields";
        areaNames[4] = "Eterna Forest";
        areaNames[5] = "Route 202";
        areaNames[6] = "Solaceon Town";
        areaNames[7] = " Mt.Coronet";
    }

    public void drawAreaIcons() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        g2.setColor(Color.BLACK);

        int x = (gp.player.worldX / gp.tileSize) + 1;
        int y = (gp.player.worldY / gp.tileSize) + 1;

        long elapsed = System.currentTimeMillis() - areaDisplayStartTime;

        int iconX = 635;
        int iconY = -200;

        int nameX = 660;
        int nameY = -130;

        int iconWidth = 345;
        int iconHeight = 95;

        int newArea = -1;

        if (x > 42 && x <= 71 && y >= 5 && y <= 22) {
            newArea = 3;
        } else if (x >= 49 && x <= 55 && y >= 62 && y <= 66) {
            newArea = 5;
        } else if (x >= 38 && x < 94 && y >= 83 && y <= 94) {
            newArea = 7;
        } else if (x <= 38 && y >= 62 && y <= 91) {
            newArea = 6;
        } else if (x >= 27 && x <= 54 && y >= 17 && y <= 65) {
            newArea = 1;
        } else if (x >= 40 && x <= 92 && y >= 55 && y <= 73) {
            newArea = 5;
        } else if (x <= 27 && y >= 38 && y <= 57) {
            newArea = 0;
        } else if (x <= 42 && y < 34) {
            newArea = 2;
        } else if (x >= 71 && x <= 92 && y >= 7 && y <= 54) {
            newArea = 4;
        }

        // If area changed, reset timer
        if (newArea != -1 && newArea != currentArea) {
            currentArea = newArea;
            areaDisplayStartTime = System.currentTimeMillis();
            animatedIconY = -200;
        }

        // Draw only if within 3 seconds
        if (currentArea != -1 && elapsed <= AREA_DISPLAY_DURATION) {
            if (animatedIconY < 0) {
                animatedIconY += 4;
            }
            g2.drawImage(areaIcons[currentArea], iconX, animatedIconY, iconWidth, iconHeight, null);
            g2.drawString(areaNames[currentArea], nameX, animatedIconY + 70);
        }

        if (elapsed > AREA_DISPLAY_DURATION) {
            if (animatedIconY > -200) {
                animatedIconY -= 4;
            }
            g2.drawImage(areaIcons[currentArea], iconX, animatedIconY, iconWidth, iconHeight, null);
            g2.drawString(areaNames[currentArea], nameX, animatedIconY + 70);
        }
    }


    public void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        g2.setColor(Color.BLACK);

        g2.drawImage(titleScreenBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(logo, (gp.screenWidth / 2) - 350, 25, 700, 250, null);

        if (!display) {
            g2.drawString("Press enter to start", gp.screenWidth / 2 - 150, gp.screenHeight / 2 + 100);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
        g2.drawString("Made By: Andreas, Jacob, Theis & Bertram", 25, 750);
        g2.drawImage(opal, gp.screenWidth / 2 - 150, 250, 350, 150, null);
        g2.drawImage(rowan, 750, 400, 94 * 2, 139 * 2, null);
        g2.drawImage(lucas, 100, 400, 56 * 2, 123 * 2, null);
        g2.drawImage(dawn, 200, 450, 63 * 2, 125 * 2, null);

        resetter += 1;

        if (resetter < 75){
            display = false;
        }
         if (resetter > 75){
            display = true;
        }
         if (resetter > 150){
            resetter = 0;
        }
    }
}




