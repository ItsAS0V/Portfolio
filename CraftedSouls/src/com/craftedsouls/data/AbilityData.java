package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class AbilityData {

    private static AbilityData instance = new AbilityData();

    public static AbilityData getInstance() {
        return instance;
    }

    private FileConfiguration abilityData;
    private File aFile;

    public void setup(int id) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        aFile = new File(plugin.getDataFolder() + File.separator + "abilities" + File.separator + id + ".yml");

        if(!aFile.exists()) {
            try {
                aFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + id + ".yml!");
                e.printStackTrace();
            }
        }

        abilityData = YamlConfiguration.loadConfiguration(aFile);
    }


    public FileConfiguration get(int id) {

        aFile = new File(plugin.getDataFolder() + File.separator + "abilities" + File.separator + id + ".yml");
        abilityData = YamlConfiguration.loadConfiguration(aFile);

        return abilityData;

    }

    public void save(int id) {
        try {
            abilityData.save(aFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + id + ".yml!");
        }
    }

    public void reload(int id) {
        abilityData = YamlConfiguration.loadConfiguration(aFile);
    }

    public boolean abilityExists(int id) {
        File f = new File(plugin.getDataFolder() + File.separator + "abilities", id + ".yml");
        return f.exists();
    }
}
