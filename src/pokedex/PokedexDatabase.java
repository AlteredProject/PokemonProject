package pokedex;

import java.sql.*;

public class PokedexDatabase {
    private static final String DB_URL = "jdbc:sqlite:pokedex.db";

    public static boolean getPokemonByNameOrId(Pokemon p) {
        String sql = "SELECT * FROM pokemon_data WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setHeight(rs.getInt("height"));
                p.setWeight(rs.getInt("weight"));
                p.setDescription(rs.getString("description"));

                // 1. Rebuild Stats Array
                EntryStats[] stats = new EntryStats[6];
                stats[0] = EntryStats.createStat("hp", rs.getInt("hp"));
                stats[1] = EntryStats.createStat("attack", rs.getInt("attack"));
                stats[2] = EntryStats.createStat("defense", rs.getInt("defense"));
                stats[3] = EntryStats.createStat("special-attack", rs.getInt("special_attack"));
                stats[4] = EntryStats.createStat("special-defense", rs.getInt("special_defense"));
                stats[5] = EntryStats.createStat("speed", rs.getInt("speed"));
                p.setStats(stats); // <--- Critical: Set the rebuilt stats

                // 2. Rebuild Types Array
                String typesString = rs.getString("types");
                String[] typeNames = typesString.split(",");
               TypeEntry[] types = new TypeEntry[typeNames.length];

                for (int i = 0; i < typeNames.length; i++) {
                    TypeEntry entry = new TypeEntry();
                    entry.type = new TypeInfo();
                    entry.type.name = typeNames[i].trim();
                    types[i] = entry;
                }
                p.setTypes(types); // <--- Critical: Set the rebuilt types

                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error reading Pokemon " + p.getName() + ": " + e.getMessage());
            return false;
        }
    }
    public static void insertPokemon(Pokemon p, String description) { // Indsætter data fra et pokemon object til databasen
        String sql = "INSERT OR IGNORE INTO pokemon_data(id, name, height, weight, types, description, hp, attack, defense, special_attack, special_defense, speed) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, p.getId());
            pstmt.setString(2, p.getName());
            pstmt.setInt(3, p.getHeight());
            pstmt.setInt(4, p.getWeight());

            String types = "";
            for (TypeEntry entry : p.getTypes()) {
                types += entry.type.name + ",";
            }
            pstmt.setString(5, types.replaceAll(",$", "")); // Remove trailing comma
            pstmt.setString(6, description);

            pstmt.setInt(7, p.getStats()[0].base_stat);  // HP
            pstmt.setInt(8, p.getStats()[1].base_stat);  // Attack
            pstmt.setInt(9, p.getStats()[2].base_stat);  // Defense
            pstmt.setInt(10, p.getStats()[3].base_stat); // Special-Attack
            pstmt.setInt(11, p.getStats()[4].base_stat); // Special-Defense
            pstmt.setInt(12, p.getStats()[5].base_stat); // Speed

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting Pokemon " + p.name + ": " + e.getMessage());
        }
    }
}
