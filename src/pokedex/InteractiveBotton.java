package pokedex;

import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InteractiveBotton {

    public final int worldX = 25;
    public final int worldY = 690;
    public BufferedImage pokedexIcon;

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

        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            pokedexIcon = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/pokedexIcon.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawpokedexIcon(Graphics2D g2) {
        BufferedImage image = pokedexIcon;

        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }
}




