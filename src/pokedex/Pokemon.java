package pokedex;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

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

    public void pokedexEntry() {
        try {
            while (validPokemon) {

                Scanner sc = new Scanner(System.in);
                System.out.println("=========================================");
                System.out.println("Welcome to the international pokédex database");
                System.out.print("Please enter pokemon name: ");
                String name = sc.nextLine().toLowerCase();
                String url = "https://pokeapi.co/api/v2/pokemon/" + name;

                // søg efter pokemon
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());

                // If pokemon does not exist
                if (response1.statusCode() == 404) {
                    System.out.println("ERROR: The pokemon " + "'" + name + "'" + " can not be found. Please try again.\n");
                    continue; // hop tilbage og spørg igen
                }
                Gson gson = new Gson();
                Pokemon p = gson.fromJson(response1.body(), Pokemon.class);

               p.printInfo();
                break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void printInfo()throws IOException, InterruptedException{
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
        System.out.println();
        System.out.println("====DESCRIPTION & SPRITE====");
        String description = PokemonDescription.getDescription(this.name);
        printAligned("Description", description);
        printAligned("Sprite URL", this.sprites.front_default);
        System.out.println("=========================================");
    }

    public void printAligned(String label, String value) {
        int column = 25;
        String fullLabel = label;

        int dotsCount = column - fullLabel.length();
        dotsCount = Math.max(1, dotsCount);

        String dots = ".".repeat(dotsCount);
        System.out.println(fullLabel + dots + value);
    }
}




