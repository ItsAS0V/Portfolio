package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class GuildsData {
    private static GuildsData instance = new GuildsData();

    public static GuildsData getInstance() {
        return instance;
    }

    private FileConfiguration guilds;
    private File guildsFile;

    public void create(String name) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        guildsFile = new File(plugin.getDataFolder() + File.separator + "guilds" + File.separator + name + ".yml");

        if(!guildsFile.exists()) {
            try {
                guildsFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + name + ".yml!");
                e.printStackTrace();
            }
        }

        guilds = YamlConfiguration.loadConfiguration(guildsFile);
    }


    public FileConfiguration get(String name) {

        guildsFile = new File(plugin.getDataFolder() + File.separator + "guilds" + File.separator + name + ".yml");
        guilds = YamlConfiguration.loadConfiguration(guildsFile);

        return guilds;

    }

    public void save(String name) {
        try {
            guilds.save(guildsFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + name + ".yml!");
        }
    }

    public void reload(String uuid) {
        guilds = YamlConfiguration.loadConfiguration(guildsFile);
    }

    public boolean userFileExists(String name) {
        File f = new File(plugin.getDataFolder() + File.separator + "guilds", name + ".yml");
        return f.exists();
    }
}
