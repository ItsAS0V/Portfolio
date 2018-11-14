package it.skyplex.database;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Arrays;

public class ServerFiles {
    private Core core = Core.getInstance();

    private static ServerFiles instance = new ServerFiles();
    public static ServerFiles getInstance() { return instance; }

    private boolean creating = false;

    private FileConfiguration data;
    private File dFile;

    private FileConfiguration permissions;
    private File pFile;

    private FileConfiguration warps;
    private File wFile;

    private FileConfiguration kits;
    private File kFile;

    private FileConfiguration rules;
    private File rFile;

    private FileConfiguration donations;
    private File doFile;


    /**
     * Config Setup
     */
    public void setup() {
        if(!core.getDataFolder().exists()) {
            core.getDataFolder().mkdir();
            creating = true;
        }

        File dataFolder = new File(core.getDataFolder() + File.separator + "data" + File.separator);
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        dFile = new File(dataFolder,  "data.yml");
        if(!dFile.exists()) {
            try {
                dFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create data.yml!");
            }
        }
        data = YamlConfiguration.loadConfiguration(dFile);

        pFile = new File(core.getDataFolder(), "permissions.yml");
        if(!pFile.exists()) {
            try {
                pFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create permissions.yml!");
            }
        }
        permissions = YamlConfiguration.loadConfiguration(pFile);

        wFile = new File(dataFolder, "warps.yml");
        if(!wFile.exists()) {
            try {
                wFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create warps.yml!");
            }
        }

        warps = YamlConfiguration.loadConfiguration(wFile);

        kFile = new File(core.getDataFolder(), "kits.yml");
        if(!kFile.exists()) {
            try {
                kFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create kits.yml!");
            }
        }
        kits = YamlConfiguration.loadConfiguration(kFile);

        rFile = new File(dataFolder, "rules.yml");
        if(!rFile.exists()) {
            try {
                rFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create rules.yml!");
            }
        }
        rules = YamlConfiguration.loadConfiguration(rFile);

        doFile = new File(dataFolder, "donations.yml");
        if(!doFile.exists()) {
            try {
                doFile.createNewFile();
                creating = true;
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe("Could not create donations.yml!");
            }
        }
        donations = YamlConfiguration.loadConfiguration(dFile);

        if(creating) {
            data.set("chat.muted", false);
            data.set("chat.slew", false);
            data.set("lockdown", false);
            data.set("warp.shorten_coords", false);
            saveData();

            kits.set("kits", "[]");
            saveKits();

            rules.set("rules", Arrays.asList("No racial sayings", "No hate speech", "No leaking personal information", "No impersonating members", "No arguing with the staff team", "No hacking", "No begging for a role"));
            saveRules();

            donations.set("donations", Arrays.asList("Example 1", "Example 2"));

            permissions.set("groups.Member.prefix", "");
            permissions.set("groups.Member.color", "WHITE");
            permissions.set("groups.Member.permissions", Arrays.asList("[]"));
            permissions.set("groups.Member.op", false);

            permissions.set("groups.VIP.prefix", "&8[&dVIP&8]");
            permissions.set("groups.VIP.color", "LIGHT_PURPLE");
            permissions.set("groups.VIP.permissions", Arrays.asList("[]"));
            permissions.set("groups.VIP.op", false);

            permissions.set("groups.VIP+.prefix", "&8[&5VIP&7+&8]");
            permissions.set("groups.VIP+.color", "DARK_PURPLE");
            permissions.set("groups.VIP+.permissions", Arrays.asList("skyplex.echest"));
            permissions.set("groups.VIP+.op", false);

            permissions.set("groups.MVP.prefix", "&8[&aMVP&8]");
            permissions.set("groups.MVP.color", "GREEN");
            permissions.set("groups.MVP.permissions", Arrays.asList("skyplex.echest", "skyplex.feed"));
            permissions.set("groups.MVP.op", false);

            permissions.set("groups.MVP+.prefix", "&8[&2MVP&7+&8]");
            permissions.set("groups.MVP+.color", "DARK_GREEN");
            permissions.set("groups.MVP+.permissions", Arrays.asList("skyplex.echest", "skyplex.feed", "skyplex.fly"));
            permissions.set("groups.MVP+.op", false);

            permissions.set("groups.Helper.prefix", "&8[&eHelper&8]");
            permissions.set("groups.Helper.color", "YELLOW");
            permissions.set("groups.Helper.permissions", Arrays.asList("skyplex.echest", "skyplex.feed", "skyplex.fly", "skyplex.warn", "skyplex.mute"));
            permissions.set("groups.Helper.op", false);

            permissions.set("groups.Moderator.prefix", "&8[&6Mod&8]");
            permissions.set("groups.Moderator.color", "GOLD");
            permissions.set("groups.Moderator.permissions", Arrays.asList("skyplex.echest", "skyplex.feed", "skyplex.fly", "skyplex.warn", "skyplex.mute", "skyplex.kick", "skyplex.ban"));
            permissions.set("groups.Moderator.op", false);

            permissions.set("groups.Developer.prefix", "&8[&b&lDEV&8]");
            permissions.set("groups.Developer.color", "AQUA");
            permissions.set("groups.Developer.permissions", Arrays.asList("[]"));
            permissions.set("groups.Developer.op", false);

            permissions.set("groups.Admin.prefix", "&8[&cAdmin&8]");
            permissions.set("groups.Admin.color", "RED");
            permissions.set("groups.Admin.permissions", Arrays.asList("skyplex.echest", "skyplex.feed", "skyplex.fly", "skyplex.warn", "skyplex.mute", "skyplex.kick", "skyplex.ban",
                    "skyplex.unban", "skyplex.broadcast", "skyplex.invsee", "skyplex.lockdown", "skyplex.lookup", "skyplex.vanish", "skyplex.warps.set", "skyplex.warps.delete", "skyplex.chat.manage"));
            permissions.set("groups.Admin.op", false);

            permissions.set("groups.Owner.prefix", "&8[&4&lOwner&8]");
            permissions.set("groups.Owner.color", "DARK_RED");
            permissions.set("groups.Owner.permissions", Arrays.asList("*"));
            permissions.set("groups.Owner.op", true);

            permissions.set("default_group", "Member");
            savePermissions();
        }
    }

    /**
     * Get the Data files.
     * @return
     */
    public FileConfiguration getData() {
        return data;
    }

    /**
     * Save the Data file.
     */
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

    /**
     * Get the permissions file
     * @return
     */
    public FileConfiguration getPermissions() {
        return permissions;
    }

    /**
     * Save the permissions file
     */
    public void savePermissions() {
        try {
            permissions.save(pFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save permissions.yml!");
        }
    }

    /**
     * Reload the permissions file
     */
    public void reloadPermissions() {
        permissions = YamlConfiguration.loadConfiguration(pFile);
    }

    /**
     * Get the warps file
     * @return
     */
    public FileConfiguration getWarps() {
        return warps;
    }

    /**
     * Save the warp file
     */
    public void saveWarps() {
        try {
            warps.save(wFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save warps.yml!");
        }
    }

    /**
     * Reload the warps file
     */
    public void reloadWarps() {
        warps = YamlConfiguration.loadConfiguration(wFile);
    }

    /**
     *  Get the Structs file
     * @return
     * **/
    public FileConfiguration getKits() {
        return kits;
    }

    /**
     * Save the structs file
     */
    public void saveKits() {
        try {
            kits.save(kFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save kits.yml!");
        }
    }

    /**
     * Reload the structs file
     */
    public void reloadKits() {
        kits = YamlConfiguration.loadConfiguration(kFile);
    }

    /**
     * Get the rules file
     * @return
     */
    public FileConfiguration getRules() {
        return rules;
    }

    /**
     * Save the rules file
     */
    public void saveRules() {
        try {
            rules.save(rFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save rules.yml!");
        }
    }

    /**
     * Get the rules file
     * @return
     */
    public FileConfiguration getDonations() {
        return donations;
    }

    /**
     * Save the permissions file
     */
    public void saveDonations() {
        try {
            donations.save(doFile);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Could not save donations.yml!");
        }
    }
}
