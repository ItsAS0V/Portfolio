package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventPlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        Settings settings = Settings.getInstance();
        UserData userData = UserData.getInstance();
        int charSlot = userData.getCurrentChar(uuid);
        int wallet = userData.get(uuid).getInt("economy.total");
        int bank = userData.get(uuid).getInt("economy.bank");
        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        String message = event.getMessage();
        Date time = Calendar.getInstance().getTime();
        String rankName = PermissionsEx.getUser(player).getParentIdentifiers().get(0);
        String contributor = "§8[§cNULL§8]§r";

        String prefix = PermissionsEx.getUser(player).getPrefix("world");

        if(!prefix.isEmpty()) {
            prefix += " ";
        }

        event.setCancelled(true);

        if (CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You cannot type in the main menu, select a character first");
            event.setCancelled(true);
            return;
        }

        if(databaseManager.getMute() == true) {
            if(!player.hasPermission("cscore.staff")) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "The chat is currently muted");
                return;
            }
        }

        String channelPrefix = "§8[§cNULL§8]";
        int userLevel = 0;

        userLevel = userData.get(uuid).getInt("characters." + charSlot + ".level");

        //User level color
        String userLevelColor = "§f";
        
        if(userLevel >= 0 && userLevel <= 9) {
            userLevelColor = "§f";
        }
        else if(userLevel >= 10 && userLevel <= 19) {
            userLevelColor = "§9";
        }
        else if(userLevel >= 20 && userLevel <= 39) {
            userLevelColor = "§2";
        }
        else if(userLevel >= 40 && userLevel <= 59) {
            userLevelColor = "§5";
        }
        else if(userLevel >= 60 && userLevel <= 89) {
            userLevelColor = "§6";
        }
        else if(userLevel >= 90 && userLevel <= 125) {
            userLevelColor = "§c";
        } else {
            userLevelColor = "§8[§cNULL§8]§r";
        }

        //Chat

        if(userData.get(uuid).getBoolean("chat.global")) {
            channelPrefix = "§8[§eG§8]";
        } else if(userData.get(uuid).getBoolean("chat.staff")) {
            channelPrefix = Prefix.STAFF;
        } else {
            channelPrefix = "§8[§aL§8]";
        }
        String finalString = "§8[§cNULL§8]";

        if(userData.get(uuid).getBoolean("chat.staff")) {
            finalString = ChatColor.translateAlternateColorCodes('&', prefix + player.getName() + " §8»§f " + event.getMessage());
        } else {

            for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (message.contains("@" + online.getName())) {
                    finalString = ChatColor.translateAlternateColorCodes('&', "§8[§f" + userLevelColor + userLevel + "§8]§r"
                            + prefix + player.getName() + " §8»§f " + event.getMessage().replace('@' + online.getName(), "§f@§b" + online.getName() + "§r"));
                    online.playSound(online.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
                    break;
                } else {
                    finalString = ChatColor.translateAlternateColorCodes('&', "§8[§f" + userLevelColor + userLevel + "§8]§r"
                            + prefix + player.getName() + " §8»§f " + event.getMessage());
                }
            }
        }

        if(PermissionsEx.getUser(player.getName()).inGroup("Moderator")) {
            rankName = "§eModerator";
        } else if(PermissionsEx.getUser(player.getName()).inGroup("Admin")) {
            rankName = "§cAdmin";
        } else if(PermissionsEx.getUser(player.getName()).inGroup("Contributor")) {
            rankName = "";
            if(PermissionsEx.getUser(player.getName()).has("craftedsouls.rdev")) {
                rankName += "§aDeveloper ";
            }
            if(PermissionsEx.getUser(player.getName()).has("craftedsouls.rdesigner")) {
                rankName += "§bDesigner ";
            }
            if(PermissionsEx.getUser(player.getName()).has("craftedsouls.rlore")) {
                rankName += "§2Lore ";
            }
            if(PermissionsEx.getUser(player.getName()).has("craftedsouls.rarchitect")) {
                rankName += "§9Architect ";
            }
        } else {
            rankName = "§7User";
        }

            TextComponent component;

            if (userData.get(uuid).getBoolean("chat.global")) {

                component = CraftedSouls.getRankManager().getComponent("§eGlobal", channelPrefix, finalString, player.getName(), rankName, userLevelColor, userLevel, "No Guild");
                CraftedSouls.plugin.getServer().spigot().broadcast(component);

            } else if (userData.get(uuid).getBoolean("chat.staff")) {
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.hasPermission("cscore.staffchat")) {
                        component = CraftedSouls.getRankManager().getComponent("§cStaff", channelPrefix, finalString, player.getName(), rankName, userLevelColor, userLevel, "No Guild");
                        CraftedSouls.plugin.getServer().spigot().broadcast(component);
                    }
                }
            } else {
                component = CraftedSouls.getRankManager().getComponent("§aLocal", channelPrefix, finalString, player.getName(), rankName, userLevelColor, userLevel, "No Guild");
                CraftedSouls.plugin.getServer().spigot().broadcast(component);

        }

        //Logging system
        try {
            FileWriter fileWriter = new FileWriter(settings.getLog(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[CHAT]" + "[" + time + "] " + player + ": " + message);
            bufferedWriter.newLine();
            fileWriter.flush();
            bufferedWriter.close();
            settings.saveLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
