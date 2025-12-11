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
import java.util.Scanner;

public class Pokedex {
    public int worldX, worldY = 0;
    private int pokemonX = 225;
    private int pokemonY = 300;
    private int pokemonSize = 96;
    private String name;
    private String path;
    private BufferedImage pokedexBoy, pokedexGirl, pokemonSprite;

    GamePanel gp;
    KeyHandler keyH;
    Pokemon pokemon = new Pokemon();
    InteractiveButton interactiveButton;


    final int originalPokedexWidth = 256;  // 256*192 px
    final int originalPokedexHeight = 192;

    public final int pokedexSizeWidth = originalPokedexWidth * 4; // 1024*768 px
    public final int pokedexSizeHeight = originalPokedexHeight * 4;
    private BufferedImage pokedexCache;

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

    public void drawPokedex(Graphics2D g2) {
        pokedexGirl(g2);

    }

    // Tegner pokedex girl
    public void pokedexGirl(Graphics2D g2) {
        BufferedImage image = pokedexGirl;
        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }

    // Tegner pokedex boy
    public void pokedexBoy(Graphics2D g2) {
        BufferedImage image = pokedexBoy;
        g2.drawImage(image, worldX, worldY, pokedexSizeWidth, pokedexSizeHeight, null);
    }

    // Scanner for input og ser om filen findes lokalt eller skal loades fra API
    public void searchForPokemon() {
        gp.gameState = gp.pokedexSearchState;
        scanForInput();

        pokemon.pokedexLoad();

        path = pokemon.getPath();
        if (path == null) { // If statement kan fjernes, når det funker.
            System.err.println("Search failed or path could not be determined. Returning to Pokedex state.");
            gp.gameState = gp.pokedexState;
            return;
        }
        File outputFile = new File(path);
        if (outputFile.exists()) {
            loadPokemonCache();
            System.out.println("Already exist");
        } else {
            System.out.println("loaded from api");
            loadPokemonToPokedex();
        }
        gp.gameState = gp.pokedexState;
    }

    // Tegner pokemon PNG fra enten cache eller API
    public void drawPokedexSprite(Graphics2D g2) {
        if (pokemonSprite != null) {
            g2.drawImage(pokemonSprite, pokemonX, pokemonY, pokemonSize * 2, pokemonSize * 2, null);
        }
    }

    // Loader pokemon sprite til BufferedImage pokemonSprite fra API
    public void loadPokemonToPokedex() {
        String imageUrl = pokemon.getPokemonSprite();
        URL url;
        try {
            URI uri = new URI(imageUrl); //imageurl sættes ind her i stedet, når søgefunktion er klar
            url = uri.toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        pokemonSprite = pokemon.loadImageFromUrl(url);
    }

    // Loader pokemon sprite til BufferedImage pokemonSprite fra cache
    public void loadPokemonCache() {
        try {
            File file = new File(path);
            if (file.exists()) {
                pokedexCache = ImageIO.read(file);
            } else {
                System.out.println("File not found at " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pokemonSprite = pokedexCache;
    }

    public void scanForInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println("Welcome to the international pokédex database");
        System.out.print("Please enter pokemon name: ");
        name = sc.nextLine().toLowerCase();
        pokemon.setName(name);
    }

}



