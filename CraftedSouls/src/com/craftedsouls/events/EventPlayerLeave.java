package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.CraftingData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.guis.CharacterGUI;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class EventPlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        FileConfiguration userFile = userData.get(uuid);
        DatabaseManager db = CraftedSouls.getDatabaseManager();

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            String onlineUUID = online.getUniqueId().toString();
            List<String> friendList = userData.get(onlineUUID).getStringList("Friends");
            if(friendList.contains(online.getName())) {
                CraftedSouls.getChatManager().sendChat(online, Prefix.PLAYER_LEAVE ,player.getName() + " §7has left the server");
                event.setQuitMessage(null);
            } else if(player.hasPermission("cscore.staff")) {
                if(online.hasPermission("cscore.staff")) {
                    CraftedSouls.getChatManager().sendChat(online, Prefix.STAFF, player.getName() + " §ehas left the server");
                    event.setQuitMessage(null);
                }
            } else {
                event.setQuitMessage(null);
                return;
            }
        }

        //db.setUserData(uuid, "CHARSLOT", userFile.getString("selectedchar"));
        //db.setUserData(uuid, "BANS", userFile.getString("bans"));
        //db.setUserData(uuid, "WARNINGS", userFile.getString("warnings"));
        //db.setCharData(uuid, "LEVEL", userFile.getString(userFile.getInt("currentchar") + ".level"));
        //db.setCharData(uuid, "XP", userFile.getString(userFile.getInt("currentchar") + ".xp"));
        //db.setCharData(uuid, "X", String.valueOf(player.getLocation().getBlockX()));
        //db.setCharData(uuid, "Y", String.valueOf(player.getLocation().getBlockY()));
        //db.setCharData(uuid, "Z", String.valueOf(player.getLocation().getBlockZ()));

        if(!CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            CharacterGUI.saveInventory(userData, uuid, player);
            CharacterGUI.saveLocation(uuid, player);
            userData.save(uuid);
        }

        //Remove from bars
        if(CraftedSouls.getBarManager().getBarStorage().get(player.getUniqueId()) != null) {
            CraftedSouls.getBarManager().removePlayer(player.getUniqueId());
        }

        //Exit main menu
        if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            CraftedSouls.getMainMenuManager().exitMainMenu(player);
        }

        //Exit from combat
        if(CraftedSouls.getCombatManager().isPlayerCombatmode(player)) {
            CraftedSouls.getCombatManager().exitCombatmode(player);
        }
    }

}

