package pokedex;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokemonDescription {

    public static String getDescription(String name)  {

        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = "https://pokeapi.co/api/v2/pokemon-species/" + name.toLowerCase();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            PokemonSpecies species = gson.fromJson(response.body(), PokemonSpecies.class);


            System.out.println("Capture Rate: " + species.capture_rate);

            for (FlavorTextEntry entry : species.flavor_text_entries) {
                if (entry.language.name.equals("en")) {
                    return entry.flavor_text.replace("\n", " ");
                }
            }
            return "No English description found.";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}

