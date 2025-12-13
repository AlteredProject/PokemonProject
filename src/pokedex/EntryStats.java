package pokedex;

public class EntryStats {

    public int base_stat;
    public StatInfo stat;

    public static EntryStats createStat(String name, int baseStat) {
        EntryStats entry = new EntryStats();
        entry.base_stat = baseStat;
        entry.stat = new StatInfo();
        entry.stat.name = name;
        return entry;
    }
}
