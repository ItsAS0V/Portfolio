package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class LevelData {
    private static LevelData instance = new LevelData();

    public static LevelData getInstance() {
        return instance;
    }

    private FileConfiguration leveldata;
    private File lFile;

    public void setup() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        lFile = new File(plugin.getDataFolder(), "levels.yml");

        if(!lFile.exists()) {
            try {
                lFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create levels.yml!");
                e.printStackTrace();
            }
        }

        leveldata = YamlConfiguration.loadConfiguration(lFile);
    }


    public FileConfiguration get() {

        lFile = new File(plugin.getDataFolder(), "levels.yml");
        leveldata = YamlConfiguration.loadConfiguration(lFile);

        return leveldata;

    }

    public void save() {
        try {
            leveldata.save(lFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save levels.yml!");
        }
    }

    public void reload() {
        leveldata = YamlConfiguration.loadConfiguration(lFile);
    }

    public void createLevels() {
        for(int i = 1; i < 126; i++) {
            leveldata.set(i + ".RXP", 100);
            save();
        }
    }
}
