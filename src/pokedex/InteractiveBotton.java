package pokedex;

import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;

public class InteractiveBotton {

    public int worldX;
    public int worldY;
    public BufferedImage pokedexIcon, searhPokemonGreen;

    GamePanel gp;
    KeyHandler keyH;
    ClickHandler leftClicked;

    final int originalPokedexWidth = 32;
    final int originalPokedexHeight = 32;

    public final int pokedexSizeWidth = originalPokedexWidth * 2;
    public final int pokedexSizeHeight = originalPokedexHeight * 2;

    public InteractiveBotton(GamePanel gp, KeyHandler keyH, ClickHandler leftClicked) {
        this.gp = gp;
        this.keyH = keyH;
        this.leftClicked = leftClicked;

        getBottonImage();
    }

    public void getBottonImage() {
        try {
            pokedexIcon = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/pokedexIcon.png"));
            searhPokemonGreen = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonGreen.png"));

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
    public void drawpokedexButtons(Graphics2D g2){
        worldX = 225;
        worldY = 575;
        int width = 98*2;
        int height = 43*2;
        BufferedImage image = searhPokemonGreen;
        g2.drawImage(image, worldX, worldY, width, height, null);
    }
}




