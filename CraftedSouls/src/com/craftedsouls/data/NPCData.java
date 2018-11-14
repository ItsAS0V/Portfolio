package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class NPCData {
    private static NPCData instance = new NPCData();

    private FileConfiguration npcdata;
    private File npcFile;


    public static NPCData getInstance() {
        return instance;
    }

    public void setup(String name) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        //Lemme check something
        npcFile = new File(plugin.getDataFolder() + File.separator + "npc" + File.separator + name + ".yml");

        if(!npcFile.exists()) {
            try {
                npcFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create" + name + ".yml!");
                e.printStackTrace();
            }
        }

        npcdata = YamlConfiguration.loadConfiguration(npcFile);

    }

    public FileConfiguration get(String name) {

        npcFile = new File(plugin.getDataFolder() + File.separator + "npc" + File.separator + name + ".yml");
        npcdata = YamlConfiguration.loadConfiguration(npcFile);

        return npcdata;
    }

    public void save(String name) {
        try {
            npcdata.save(npcFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save" + name + ".yml!");
            e.printStackTrace();
        }
    }

    public void reload(String name) {
        npcdata = YamlConfiguration.loadConfiguration(npcFile);
    }

    public boolean npcFileExists(String name) {
        File f = new File(plugin.getDataFolder() + File.separator + "npc", name + ".yml");
        return f.exists();
    }
}


