package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;


public class UserData {
    private static UserData instance = new UserData();

    public static UserData getInstance() {
        return instance;
    }

    private FileConfiguration playerdata;
    private File pdFile;

    public void setup(String uuid) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        pdFile = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + uuid + ".yml");

        if(!pdFile.exists()) {
            try {
                pdFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + uuid + ".yml!");
                e.printStackTrace();
            }
        }

        playerdata = YamlConfiguration.loadConfiguration(pdFile);
    }


    public FileConfiguration get(String uuid) {

        pdFile = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + uuid + ".yml");
        playerdata = YamlConfiguration.loadConfiguration(pdFile);

        return playerdata;

    }

    public int getCurrentChar(String uuid) {
       return get(uuid).getInt("selectedchar");
    }

    public void save(String uuid) {
        try {
            playerdata.save(pdFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + uuid + ".yml!");
        }
    }

    public void reload(String uuid) {
        playerdata = YamlConfiguration.loadConfiguration(pdFile);
    }

    public boolean userFileExists(String uuid) {
        File f = new File(plugin.getDataFolder() + File.separator + "userdata", uuid + ".yml");
        return f.exists();
    }
}
