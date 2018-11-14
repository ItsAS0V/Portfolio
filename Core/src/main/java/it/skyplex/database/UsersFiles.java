package it.skyplex.database;

import it.skyplex.Core;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Date;
import java.util.TimeZone;

public class UsersFiles {
    private Core core = Core.getInstance();

    private OfflinePlayer player;
    private String uuid;

    private FileConfiguration config;
    private File file;

    private ServerFiles serverFiles = ServerFiles.getInstance();

    private boolean creating = false;

    /**
     * Get the file of the given user.
     * @param player
     * @return
     */
    public static UsersFiles get(Player player) {
        return new UsersFiles(player, player.getUniqueId().toString());
    }

    /**
     * Gets the files of the given offline user.
     * @param offline
     * @return
     */
    public static UsersFiles getOffline(OfflinePlayer offline) {
        return new UsersFiles(offline.getPlayer(), offline.getUniqueId().toString());
    }

    private UsersFiles(OfflinePlayer player, String uuid) {
        if (!core.getDataFolder().exists()) {
            core.getDataFolder().mkdir();
        }

        File folder = new File(core.getDataFolder() + File.separator + "users" + File.separator);

        if (!folder.exists()) {
            folder.mkdir();
        }

        file = new File(folder, uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                creating = true;
            } catch (Exception e) {
                e.printStackTrace();
                core.getLogger().severe(ChatColor.RED + "Could not create " + uuid + ".yml!");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        this.player = player;
        this.uuid = uuid;

        if (creating) {
            if (player != null) {
                config.set("username", player.getName());
                config.set("uuid", player.getUniqueId().toString());
            }

            TimeZone.setDefault(TimeZone.getTimeZone("EST"));

            config.set("firstjoined", new Date().getTime());
            config.set("lastlogin", new Date().getTime());
            config.set("lastlogoff", -1l);
            config.set("rank", serverFiles.getPermissions().getString("default_group"));
            config.set("balance", 100);
            config.set("faction", "Wilderness");

            config.set("punishments.warnings", 0);
            config.set("punishments.kicks", 0);

            config.set("muted.status", false);
            config.set("muted.reason", "NOT_MUTED");
            config.set("muted.time", -1);

            config.set("banned.enabled", false);
            config.set("banned.reason", "null");
            config.set("banned.sender", "null");
            saveFile();
        }
    }

    /**
     * Check to see if the user is new.
     * @return
     */
    public boolean isNew() {
        return creating;
    }

    /**
     * Get the player.
     * @return
     */
    public OfflinePlayer getPlayer() {
        return player;
    }

    /**
     * Gets the users file.
     * @return
     */
    public FileConfiguration getFile() {
        return config;
    }

    public void saveFile() {
        try {
            config.save(file);
        } catch (Exception e) {
            core.getLogger().severe(ChatColor.RED + "Could not save " + file.getName() + "!");
        }
    }

    /**
     * Reload the users file.
     */
    public void reloadFile() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Sets the users rank.
     * @param rank
     */
    public void setRank(String rank) {
        getFile().set("rank", rank);
        saveFile();
    }

    /**
     * Gets the users rank.
     * @return
     */
    public String getRank() {
        return getFile().getString("rank");
    }

    /**
     * Mutes the user for the given reason & unmute time.
     * @param reason
     * @param unmute
     */
    public void mute(String reason, Date unmute) {
        config.set("muted.status", true);
        config.set("muted.reason", reason);

        if (unmute == null) {
            config.set("muted.time", -1);
        } else {
            config.set("muted.time", unmute.getTime());
        }

        saveFile();
    }

    /**
     * Unmute the user.
     */
    public void unmute() {
        config.set("muted.status", false);
        config.set("muted.reason", "NOT_MUTED");
        config.set("muted.time", -1);
        saveFile();
    }

    /**
     * Check if the user is muted.
     * @return
     */
    public boolean isMuted() {
        return config.getBoolean("muted.status", false);
    }

    /**
     * Gets the muted reason.
     * @return
     */
    public String getMutedReason() {
        if (!isMuted()) {
            return "NOT_MUTED";
        }

        return config.getString("muted.reason", "NOT_MUTED");
    }

    /***
     * Gets the unmute time.
     * @return
     */
    public long getUnmuteTime() {
        if (!isMuted()) {
            return -1;
        }

        return config.getLong("muted.time", -1);
    }

    /**
     * Sets the users faction.
     * @param faction
     */
    public void setFaction(String faction) {
        getFile().set("faction", faction);
        saveFile();
    }

    /**
     * Gets the users faction.
     * @return
     */
    public String getFaction() {
        return getFile().getString("faction");
    }

    /**
     * Sets the users balance
     * @param balance
     */
    public void setBalance(int balance) {
        getFile().set("balance", balance);
        saveFile();
    }

    /**
     * Gets the users balance
     */
    public int getBalance() { return getFile().getInt("balance"); }

}
