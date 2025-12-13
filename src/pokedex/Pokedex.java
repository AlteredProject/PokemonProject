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

    private String name;
    private String path;
    public BufferedImage pokemonSprite;
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
public void search(){
    scanForInput();
    searchForPokemon();
}
    // Scanner for input og ser om filen findes lokalt eller skal loades fra API
    public void searchForPokemon() {

        pokemon.pokedexLoad();
        path = cachePath();
        if (path != null) {
            loadPokemonCache();
            System.out.println("Already exist");
        } else {
            System.out.println("loaded from api");
            this.pokemonSprite = null;
        }
    }

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



