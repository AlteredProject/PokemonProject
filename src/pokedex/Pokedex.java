package pokedex;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Pokedex {
    public int worldX, worldY = 0;

    public BufferedImage pokedexBoy, pokedexGirl, pokemonSprite;

    GamePanel gp;
    KeyHandler keyH;
    Pokemon pokemon = new Pokemon();


    final int originalPokedexWidth = 256;  // 256*192 px
    final int originalPokedexHeight = 192;

    public final int pokedexSizeWidth = originalPokedexWidth * 4; // 1024*768 px
    public final int pokedexSizeHeight = originalPokedexHeight * 4;
    private BufferedImage pokedexCache;
    private Graphics2D g2;

    // Constructor
    public Pokedex(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        getPlayerImage();
    }

    // Loader pokedex images
    public void getPlayerImage() {

        try {
            pokedexBoy = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/boy.png"));
            pokedexGirl = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/girl.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // Tegner pokedex girl
    public void drawPokedexGirl(Graphics2D g2) {
        BufferedImage image = pokedexGirl;
        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }

    // Tegner pokedex boy
    public void drawPokedexBoy(Graphics2D g2) {
        BufferedImage image = pokedexBoy;
        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }

    // Tegner pokemon PNG fra enten cache eller API
    public void drawPokedexSprite(Graphics2D g2, int x, int y, int width, int height) {
        BufferedImage image = pokemonSprite;
        g2.drawImage(image, x, y, width * 2, height * 2, null);

        File outputFile = new File("src/resources/pokedexPngCache/" + "pokemon_id_" + pokemon.name + ".png");
        if (outputFile.exists()) {
            loadPokemonCache();
            System.out.println("Already exist");
        } else {
            System.out.println("loaded from api");
            loadPokemonToPokedex();
        }
    }

    // Loader pokemon sprite til pokedex fra API
    public void loadPokemonToPokedex() {
        String imageUrl = pokemon.getPokemonSprite();
        URL url;
        try {
            URI uri = new URI("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png"); //imageurl sættes ind her i stedet, når søgefunktion er klar
            url = uri.toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        pokemonSprite = pokemon.loadImageFromUrl(url);
    }

    // Loader pokemon sprite til pokedex fra cache
    public void loadPokemonCache() {
        try {
            pokedexCache = ImageIO.read(getClass().getResourceAsStream("src/resources/pokedexPngCache/" + "pokemon_id_" + pokemon.name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = 225;
        int y = 300;
        int size = 96;
        BufferedImage image = pokedexCache;
        g2.drawImage(image, x, y, size, size, null);
    }
}



