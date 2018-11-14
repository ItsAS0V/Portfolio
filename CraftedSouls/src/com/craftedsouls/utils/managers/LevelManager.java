package com.craftedsouls.utils.managers;

import com.craftedsouls.data.CraftingData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class LevelManager {
    private static LevelManager instance = new LevelManager();

    public static LevelManager getInstance() {
        return instance;
    }

    private FileConfiguration craftingdata;
    private File cFile;

    public void setup(String name) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        cFile = new File(plugin.getDataFolder() + File.separator + "crafting" + File.separator + name + ".yml");

        if(!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + name + ".yml!");
                e.printStackTrace();
            }
        }

        craftingdata = YamlConfiguration.loadConfiguration(cFile);
    }


    public FileConfiguration get(String name) {

        cFile = new File(plugin.getDataFolder() + File.separator + "crafting" + File.separator + name + ".yml");
        craftingdata = YamlConfiguration.loadConfiguration(cFile);

        return craftingdata;

    }

    public void save(String name) {
        try {
            craftingdata.save(cFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + name + ".yml!");
        }
    }

    public void reload(String uuid) {
        craftingdata = YamlConfiguration.loadConfiguration(cFile);
    }

    public boolean craftFileExists(String name) {
        File f = new File(plugin.getDataFolder() + File.separator + "crafting", name + ".yml");
        return f.exists();
    }
}
