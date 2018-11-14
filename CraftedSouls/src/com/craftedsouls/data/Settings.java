package com.craftedsouls.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.craftedsouls.CraftedSouls.plugin;

public class Settings {
    private static Settings instance = new Settings();

    private FileConfiguration playerdata;
    private File pdFile;

    private FileConfiguration warps;
    private File wFile;

    private FileConfiguration data;
    private File dFile;

    private FileConfiguration tickets;
    private File tFile;

    private FileConfiguration log;
    private File lFile;

    private FileConfiguration ips;
    private File ipFile;


    public static Settings getInstance() {
        return instance;
    }

    public void setup() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        wFile = new File(plugin.getDataFolder(), "warps.yml");

        if(!wFile.exists()) {
            try {
                wFile.createNewFile();
            } catch(Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create warps.yml!");
            }
        }

        warps = YamlConfiguration.loadConfiguration(wFile);

        dFile = new File(plugin.getDataFolder(), "data.yml");

        if(!dFile.exists()) {
            try {
                dFile.createNewFile();
            } catch(Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create data.yml!");
            }
        }

        data = YamlConfiguration.loadConfiguration(dFile);

        tFile = new File(plugin.getDataFolder(), "tickets.yml");

        if(!tFile.exists()) {
            try {
                tFile.createNewFile();
            } catch(Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create tickets.yml!");
            }
        }

        tickets = YamlConfiguration.loadConfiguration(tFile);

        lFile = new File(plugin.getDataFolder(), "log.yml");

        if(!lFile.exists()) {
            try {
                lFile.createNewFile();
            } catch(Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create log.yml!");
            }
        }

        log = YamlConfiguration.loadConfiguration(lFile);

        ipFile = new File(plugin.getDataFolder(), "ips.yml");

        if(!ipFile.exists()) {
            try {
                ipFile.createNewFile();
            } catch(Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create ip.yml!");
            }
        }

        ips = YamlConfiguration.loadConfiguration(ipFile);
    }

    public FileConfiguration getWarps() {
        return warps;
    }

    public void saveWarps() {
        try {
            warps.save(wFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save warps.yml!");
        }
    }

    public void reloadWarps() {
        warps = YamlConfiguration.loadConfiguration(wFile);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save data.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dFile);
    }

    public FileConfiguration getTickets() {
        return tickets;
    }

    public void saveTickets() {
        try {
            tickets.save(tFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save tickets.yml!");
        }
    }

    public void reloadTickets() {
        tickets = YamlConfiguration.loadConfiguration(tFile);
    }

    public File getLog() {
        return lFile;
    }

    public void saveLog() {
        try {
            log.save(lFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save log.yml!");
        }
    }

    public void reloadLog() {
        log = YamlConfiguration.loadConfiguration(lFile);
    }

    public FileConfiguration getIPS() {
        return ips;
    }

    public void saveIPS() {
        try {
            ips.save(ipFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save ips.yml!");
        }
    }

    public void reloadIPS() {
        ips = YamlConfiguration.loadConfiguration(ipFile);
    }
}

