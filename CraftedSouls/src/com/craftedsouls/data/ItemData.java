package com.craftedsouls.data;

import com.craftedsouls.data.builders.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.craftedsouls.CraftedSouls.plugin;


public class ItemData {

    private static ItemData instance = new ItemData();

    public static ItemData getInstance() {
        return instance;
    }

    private FileConfiguration itemData;
    private File iFile;

    public void setup(int id) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        iFile = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + id + ".yml");

        if(!iFile.exists()) {
            try {
                iFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + id + ".yml!");
                e.printStackTrace();
            }
        }

        itemData = YamlConfiguration.loadConfiguration(iFile);
    }


    public FileConfiguration get(int id) {

        iFile = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + id + ".yml");
        itemData = YamlConfiguration.loadConfiguration(iFile);

        return itemData;

    }

    public void save(int id) {
        try {
            itemData.save(iFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + id + ".yml!");
        }
    }

    public void reload(int id) {
        itemData = YamlConfiguration.loadConfiguration(iFile);
    }

    public boolean itemExists(int id) {
        File f = new File(plugin.getDataFolder() + File.separator + "items", id + ".yml");
        return f.exists();
    }


}
