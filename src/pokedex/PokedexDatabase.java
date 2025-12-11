package pokedex;

import java.sql.*;

public class PokedexDatabase {
    private static final String DB_URL = "jdbc:sqlite:pokedex.db";

    public static void insertPokemon(Pokemon p, String description) { // Indsætter data fra et pokemon object til databasen
        String sql = "INSERT INTO pokemon_data(id, name, height, weight, types, description, hp, attack, defense, special_attack, special_defense, speed) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 1. Prepare basic info and types string
            pstmt.setInt(1, p.id);
            pstmt.setString(2, p.name);
            pstmt.setInt(3, p.height);
            pstmt.setInt(4, p.weight);

            // Convert types array to a single comma-separated string
            String types = "";
            for (TypeEntry entry : p.types) {
                types += entry.type.name + ",";
            }
            pstmt.setString(5, types.replaceAll(",$", "")); // Remove trailing comma
            pstmt.setString(6, description);

            // 2. Prepare stats (assuming standard order: HP, Attack, Defense, etc.)
            // You should make this more robust in a real application, but for now:
            pstmt.setInt(7, p.stats[0].base_stat);  // HP
            pstmt.setInt(8, p.stats[1].base_stat);  // Attack
            pstmt.setInt(9, p.stats[2].base_stat);  // Defense
            pstmt.setInt(10, p.stats[3].base_stat); // Special-Attack
            pstmt.setInt(11, p.stats[4].base_stat); // Special-Defense
            pstmt.setInt(12, p.stats[5].base_stat); // Speed

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting Pokemon " + p.name + ": " + e.getMessage());
        }
    }

    public static boolean getPokemonByName(Pokemon p) { //Sender data tilbage udfra et pokemon navn
        String sql = "SELECT * FROM pokemon_data WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Found in database! Populate the Pokemon object.
                p.id = rs.getInt("id");
                p.height = rs.getInt("height");
                p.weight = rs.getInt("weight");

                return true; // Fundet i databasen
            }
            return false; // Ikke fundet i databasen

        } catch (SQLException e) {
            System.err.println("Error reading Pokemon " + p.name + ": " + e.getMessage());
            return false;
        }
    }
}
