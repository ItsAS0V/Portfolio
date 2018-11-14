package it.skyplexfactions.database;

import it.skyplexfactions.Factions;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FactionsFiles {

    private Factions factions = Factions.getInstance();

    private FileConfiguration config;
    private File file;

    private boolean creating = false;

    public static FactionsFiles get(String faction) {
        return new FactionsFiles(faction);
    }

    private FactionsFiles(String faction) {
        if (!factions.getDataFolder().exists()) {
            factions.getDataFolder().mkdir();
        }

        File folder = new File(factions.getDataFolder() + File.separator + "factions" + File.separator);

        if (!folder.exists()) {
            folder.mkdir();
        }

        file = new File(folder, faction + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                creating = true;
            } catch (Exception e) {
                e.printStackTrace();
                factions.getLogger().severe(ChatColor.RED + "Could not create " + faction + ".yml!");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        if(creating) {
            config.set("leader", "");
            config.set("balance", 0);
            config.set("power", 20);
            config.set("nexus.level", 0);
            config.set("nexus.active_powerups", "[]");
            config.set("members", "[]");
            config.set("allies", "[]");
            config.set("enemies", "[]");
            saveFile();
        }
    }

    public FileConfiguration getFile() {
        return config;
    }

    public void saveFile() {
        try {
            config.save(file);
        } catch (Exception e) {
            factions.getLogger().severe(ChatColor.RED + "Could not save " + file.getName() + "!");
        }
    }

    public void reloadFile() {
        config = YamlConfiguration.loadConfiguration(file);
    }


    public void setPower(int power) {
        getFile().set("power", power);
        saveFile();
    }
    public int getPower() { return getFile().getInt("power"); }

    public void setBalance(int balance) {
        getFile().set("balance", balance);
        saveFile();
    }
    public int getBalance() {
        return getFile().getInt("balance");
    }

    public void setNexusLevel(int level) {
        getFile().set("nexus.level", level);
        saveFile();
    }
    public int getNexusLevel() {
        return getFile().getInt("nexus.level");
    }
}
