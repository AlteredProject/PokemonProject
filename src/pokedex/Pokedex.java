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
    private GamePanel gp;
    Graphics2D g2;
    KeyHandler keyH;
    Pokemon pokemon = new Pokemon();

    private BufferedImage pokedexCache;

    // Constructor
    public Pokedex(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;


    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(gp.ui.pkmnFont);
        if (gp.gameState == gp.playState) {
        }
        if (gp.gameState == gp.pauseState){

        }



//        if (gp.gameState == gp.pokedexState || gp.gameState == gp.pokedexSearchState) {
//            gp.pokedex.drawPokedexSprite(g2);
//        }
//        button.drawSearchButton(g2);
//            button.drawDirectionRight(g2);
//            button.drawDirectionLeft(g2);
    }





    // Scanner for input og ser om filen findes lokalt eller skal loades fra API
    public void searchForPokemon() {
        gp.gameState = gp.pokedexSearchState;
        scanForInput();

        pokemon.pokedexLoad();

        path = cachePath();
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
            URI uri = new URI(imageUrl);
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

    // Scanner for input fra bruger
    public void scanForInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println("Welcome to the international pokédex database");
        System.out.print("Please enter pokemon name: ");
        name = sc.nextLine().toLowerCase();
        pokemon.setName(name);
        try {
            pokemon.setId(Integer.parseInt(name));
        } catch (NumberFormatException _) {
        }
    }

    private String cachePath() {
        return "src/resources/pokedexPngCache/pokemon_name_" + pokemon.name + ".png";
    }
}



