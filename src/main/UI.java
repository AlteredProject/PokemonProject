package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    ClickHandler clickH = new ClickHandler(gp);
    UtilityTool uTool = new UtilityTool();
    public BufferedImage dialogueWindowImage, pokedexBoy, pokedexGirl, pokedexIcon, searchButtonReleased, searchButtonPressed, previousButtonReleased, nextButtonReleased, previousButtonPressed, nextButtonPressed, onOffButton;

    public Font pkmnFont;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";

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
    }

    public void drawPokedexIcon() {
        int x = 25;
        int y = 690;
        BufferedImage image = pokedexIcon;
        g2.drawImage(image, x, y, image.getWidth() * 2, image.getHeight() * 2, null);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

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
            System.out.println("DRAWING PRESSED BUTTON");
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

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
