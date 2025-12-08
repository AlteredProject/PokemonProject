package pokedex;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pokedex {
    public int worldX, worldY = 0;

    public BufferedImage pokedexBoy, pokedexGirl;

    GamePanel gp;
    KeyHandler keyH;

    final int originalPokedexWidth = 256;  // 256*192 px
    final int originalPokedexHeight = 192;

    public final int pokedexSizeWidth = originalPokedexWidth * 4; // 1024*768 px
    public final int pokedexSizeHeight = originalPokedexHeight * 4;

    public Pokedex(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            pokedexBoy = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/boy.png"));
            pokedexGirl = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/girl.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = pokedexGirl;

        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }
}


