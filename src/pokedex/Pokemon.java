package pokedex;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Pokemon {

    // ===== ATTRIBUTES =====
    public String name;
    public int id;
    public int height;
    public int weight;
    public TypeEntry[] types;
    public Sprites sprites;
    public EntryStats[] stats;
    public boolean validPokemon = true;
    private String pokemonSprite;
    private String path;
    private PokedexDatabase pokedexDatabase = new PokedexDatabase();

    public void pokedexLoad() {
        String expectedPath = cachePath();

        if (PokedexDatabase.getPokemonByName(this)||PokedexDatabase.getPokemonById(this,name)) { // <- Attempts to fill basic data (id, height, weight)
            System.out.println("Data loaded from SQLite cache.");
            path = expectedPath;
            return;
        }
        // Hvis ikke i database, hent fra API
        pokedexEntry();
    }

    public void pokedexEntry() {

        try {
            System.out.println("loading pokemon info from API");
                String url = "https://pokeapi.co/api/v2/pokemon/" + this.name;

                // søg efter pokemon
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());

                // If pokemon does not exist
                if (response1.statusCode() == 404) {
                    System.out.println("ERROR: The pokemon " + "'" + this.name + "'" + " can not be found. Please try again.\n");
                    path = null;

                }
                Gson gson = new Gson();
                Pokemon p = gson.fromJson(response1.body(), Pokemon.class);

                this.name = p.name;
                this.id = p.id;
                this.height = p.height;
                this.weight = p.weight;
                this.types = p.types;
                this.sprites = p.sprites;
                this.stats = p.stats;

                this.printInfoFromApi();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void printInfoFromApi() throws IOException, InterruptedException {
        double kg = this.weight * 0.1;
        double mtr = this.height * 0.1;

        System.out.println("============INFO============");
        printAligned("POKEMON ID", "#" + (this.id));
        printAligned("NAME", (this.name));
        printAligned("HEIGHT", String.format("%.1f", mtr) + "M");
        printAligned("WEIGHT", String.format("%.1f", kg) + "KG");


        int typeCounter = 1;
        for (TypeEntry entry : this.types) {
            printAligned("TYPE" + typeCounter, entry.type.name);
            typeCounter++;
        }

        System.out.println();
        System.out.println("=========BASE STATS=========");

        for (EntryStats stat : this.stats) {
            printAligned(String.valueOf(stat.stat.name.toUpperCase()), String.valueOf(stat.base_stat));

        }

        pokemonSprite = this.sprites.front_default;
        System.out.println();
        System.out.println("====DESCRIPTION & SPRITE====");
        String description = PokemonDescription.getDescription(this.name);
        printAligned("Description", description);
        printAligned("Sprite URL", this.sprites.front_default);
        System.out.println("=========================================");

        path = cachePath();
        // Gemmer alt information til databasen, uden PNG
        PokedexDatabase.insertPokemon(this, description);

        pngCache();
    }

    public void printAligned(String label, String value) {
        int column = 25;
        String fullLabel = label;

        int dotsCount = column - fullLabel.length();
        dotsCount = Math.max(1, dotsCount);

        String dots = ".".repeat(dotsCount);
        System.out.println(fullLabel + dots + value);
    }

    public String getPokemonSprite() {
        return pokemonSprite;
    }

    // Gemmer PNG fra API i cache
    public void pngCache() {
        URL url;

        try {
            URI uri = new URI(pokemonSprite);
            try {
                url = uri.toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            BufferedImage pokemonPng;
            pokemonPng = loadImageFromUrl(url);
            if (pokemonPng == null) {
                System.err.println("Kunne ikke afkode billedet fra URL'en.");
                return;
            }
            try {
                File outputFile = new File(cachePath());
                boolean success = ImageIO.write(pokemonPng, "png", outputFile);
                if (success) {
                    System.out.println("Billedet blev gemt korrekt som: " + outputFile.getAbsolutePath());
                } else {
                    System.err.println("Fejl under skrivning af billedet til fil.");
                }
            } catch (IOException e) {
                System.err.println("Der opstod en IO-fejl under hentning/gemning af billedet:");
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    // Tager web adressen og gemmer PNG fra den i BufferedImage
    public BufferedImage loadImageFromUrl(URL url) {
        try {
            // Use ImageIO.read(URL) to fetch and decode the image
            // This method handles the networking (open connection, get input stream)
            BufferedImage bufferedImage = ImageIO.read(url);
            // ImageIO.read() returns null if no registered ImageReader can decode the stream
            if (bufferedImage == null) {
                System.err.println("ImageIO could not decode image format from the URL.");
            }
            return bufferedImage;
        } catch (IOException e) {
            // Catches MalformedURLException (if the URL string is invalid)
            // and other IOExceptions (network errors, file not found, etc.)
            System.err.println("Error loading image from URL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String cachePath() {
        return "src/resources/pokedexPngCache/pokemon_name_" + this.name + ".png";
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}





