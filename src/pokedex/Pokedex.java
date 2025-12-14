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
    private final Object searchLock = new Object();
    public int worldX, worldY = 0;
    private String name;
    private String path;
    private volatile boolean isSearching = false;
    public BufferedImage pokemonSprite;
    private GamePanel gp;
    Graphics2D g2;
    KeyHandler keyH;
    Pokemon pokemon;
    private BufferedImage pokedexCache;

    // Constructor
    public Pokedex(GamePanel gp, KeyHandler keyH, Pokemon pokemon) {
        this.gp = gp;
        this.keyH = keyH;
        this.pokemon = pokemon;
    }

    public void search(String nameOrId) {
        if (isSearching) {
            return;
        }
        pokemon.setName(nameOrId.toLowerCase());
        try {
            pokemon.setId(Integer.parseInt(nameOrId));
        } catch (NumberFormatException _) {
        }
        Thread searchThread = new Thread(() -> {
            try {
                isSearching = true;
                System.out.println("DEBUG: Search Thread Started.");
                searchForPokemon();
            } finally {
                isSearching = false;
                System.out.println("DEBUG: Search Thread Finished.");
                if (gp != null) {
                    gp.repaint();
                }
            }
        });
        searchThread.start();
    }

    // Scanner for input og ser om filen findes lokalt eller skal loades fra API
    public void searchForPokemon() {
        pokemon.pokedexLoad();
        path = cachePath();
        if (path != null) {
            loadPokemonCache();
            System.out.println("PNG loaded from cache");
        } else {
            System.out.println("PNG loaded from api");
            this.pokemonSprite = null;
            loadPokemonToPokedex();
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

    private String cachePath() {
        return "src/resources/pokedexPngCache/pokemon_name_" + pokemon.name + ".png";
    }

    public boolean isSearching() {
        return isSearching;
    }
}



