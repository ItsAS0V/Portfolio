package it.skyplexfactions.database;

import it.skyplexfactions.Factions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ClaimsFile {
    private static ClaimsFile instance = new ClaimsFile();
    public static ClaimsFile getInstance() { return instance; }

    private Factions factions = Factions.getInstance();

    private FileConfiguration claims;
    private File cFile;

    public void setup() {
        if(!factions.getDataFolder().exists()) {
            factions.getDataFolder().mkdir();
        }

        cFile = new File(factions.getDataFolder(), "claims.yml");
        if(!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create claims.yml!");
            }
        }
        claims = YamlConfiguration.loadConfiguration(cFile);
    }

    /**
     * Get the Data files.
     * @return
     */
    public FileConfiguration getClaims() {
        return claims;
    }

    /**
     * Save the Data file.
     */
    public void saveClaims() {
        try {
            claims.save(cFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save claims.yml!");
        }
    }

    public void reloadClaims() {
        claims = YamlConfiguration.loadConfiguration(cFile);
    }
}
