package pokedex;

import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;

public class InteractiveButton {
    GamePanel gp;
    KeyHandler keyH;
    ClickHandler leftClicked;

    public boolean isSearching = false;
    public boolean isDirectionRight = false;
    public boolean isDirectionLeft = false;
    public int drawTimer = 0;
    public final int drawDuration = 30;

    public int worldX;
    public int worldY;
    int width;
    int height;
    int size;
    public BufferedImage pokedexIcon, searchPokemonGreen, searchPokemonOrange, directionBlueLeft, directionBlueRight, directionRedRight, directionRedLeft, onOffButton;
    final int originalPokedexWidth = 32;
    final int originalPokedexHeight = 32;
    public final int pokedexSizeWidth = originalPokedexWidth * 2;
    public final int pokedexSizeHeight = originalPokedexHeight * 2;

    public InteractiveButton(GamePanel gp, KeyHandler keyH, ClickHandler leftClicked) {
        this.gp = gp;
        this.keyH = keyH;
        this.leftClicked = leftClicked;

        getBottonImage();
    }

    public void getBottonImage() {
        try {
            pokedexIcon = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/pokedexIcon.png"));
            searchPokemonGreen = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonGreen.png"));
            searchPokemonOrange = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonOrange.png"));
            directionBlueLeft = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueLeft.png"));
            directionRedLeft = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedLeft.png"));
            directionBlueRight = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueRight.png"));
            directionRedRight = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedRight.png"));
            onOffButton = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/onOffButton.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawpokedexIcon(Graphics2D g2) {
        worldX = 25;
        worldY = 690;
        BufferedImage image = pokedexIcon;
        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }

    public void drawSearchButton(Graphics2D g2) {
        if (isSearching) {
            searchButtonOrange(g2);
        } else {
            searchButtonGreen(g2);
        }
    }

    public void drawDirectionRight(Graphics2D g2) {
        if (isDirectionRight) {
            directionRedRight(g2);
        } else {
            directionBlueRight(g2);
        }
    }

    public void drawDirectionLeft(Graphics2D g2) {
        if (isDirectionLeft) {
            directionRedLeft(g2);
        } else {
            directionBlueLeft(g2);
        }
    }

    public void searchButtonGreen(Graphics2D g2) {
        worldX = 245;
        worldY = 565;
        width = 147;
        height = 64;
        g2.drawImage(searchPokemonGreen, worldX, worldY, width, height, null);
    }

    public void searchButtonOrange(Graphics2D g2) {
        worldX = 245;
        worldY = 565;
        width = 147;
        height = 64;
        g2.drawImage(searchPokemonOrange, worldX, worldY, width, height, null);
    }

    public void directionBlueRight(Graphics2D g2) {
        worldX = 398;
        worldY = 576;
        size = 48;
        g2.drawImage(directionBlueRight, worldX, worldY, size, size, null);
    }

    public void directionBlueLeft(Graphics2D g2) {
        worldX = 190;
        worldY = 576;
        size = 48;
        g2.drawImage(directionBlueLeft, worldX, worldY, size, size, null);
    }

    public void directionRedRight(Graphics2D g2) {
        worldX = 398;
        worldY = 576;
        size = 48;
        g2.drawImage(directionRedRight, worldX, worldY, size, size, null);
    }

    public void directionRedLeft(Graphics2D g2) {
        worldX = 190;
        worldY = 576;
        size = 48;
        g2.drawImage(directionRedLeft, worldX, worldY, size, size, null);
    }
}




