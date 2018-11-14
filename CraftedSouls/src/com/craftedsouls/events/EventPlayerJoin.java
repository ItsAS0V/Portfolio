package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.LevelData;
import com.craftedsouls.data.Settings;
import com.craftedsouls.gui.GameGUIManager;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        Settings settings = Settings.getInstance();
        DatabaseManager db = CraftedSouls.getDatabaseManager();
        FileConfiguration userFile = userData.get(uuid);
        LevelData levelData = LevelData.getInstance();
        List<String> ips = settings.getIPS().getStringList(player.getAddress().getAddress().getHostAddress().replace('.', '-') + ".uuid");

        String rankName = PermissionsEx.getUser(player).getParentIdentifiers().get(0);

        event.setJoinMessage(null);
        ItemStack playerInformation = GameItemList.playerInformation;
        ItemStack economy = GameItemList.createEconomy(player);

        if(!userData.userFileExists(uuid)) {
            userData.setup(uuid);

            //Chat
            userData.get(uuid).set("chat.global", false);
            userData.save(uuid);
            userData.get(uuid).set("chat.staff", false);
            userData.save(uuid);

            //Friends
            userData.get(uuid).set("Friends", "None");
            userData.save(uuid);
            userData.get(uuid).set("PendingFriends", "None");
            userData.save(uuid);

            //Characters

            userData.get(uuid).set("characters.1.locked", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.locked", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.locked", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.locked", true);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.locked", true);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.filled", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.filled", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.filled", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.filled", false);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.filled", false);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.level", 1);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.level", 1);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.level", 1);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.level", 1);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.level", 1);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.xp", 0);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.xp", 0);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.xp", 0);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.xp", 0);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.xp", 0);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.balance", 500);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.balance", 500);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.balance", 500);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.balance", 500);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.balance", 500);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.stats.health", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.stats.health", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.stats.health", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.stats.health", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.stats.health", 200);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.stats.maxhealth", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.stats.maxhealth", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.stats.maxhealth", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.stats.maxhealth", 200);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.stats.maxhealth", 200);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.stats.spirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.stats.spirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.stats.spirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.stats.spirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.stats.spirit", 100);
            userData.save(uuid);

            userData.get(uuid).set("characters.1.stats.maxspirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.2.stats.maxspirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.3.stats.maxspirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.4.stats.maxspirit", 100);
            userData.save(uuid);
            userData.get(uuid).set("characters.5.stats.maxspirit", 100);
            userData.save(uuid);


            userData.get(uuid).set("selectedchar", 0);
            userData.save(uuid);

            userData.get(uuid).set("warnings", 0);
            userData.save(uuid);

            userData.get(uuid).set("last_ip", player.getAddress().getAddress().getHostAddress());
            userData.save(uuid);


            //Sound
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);

            //Create Database User
            //db.createUser(uuid, player.getName(), rankName, 0, 0);
        }

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            String onlineUUID = online.getUniqueId().toString();
            List<String> friendList = userData.get(onlineUUID).getStringList("Friends");
            if(friendList.contains(online.getName())) {
                CraftedSouls.getChatManager().sendChat(online, Prefix.PLAYER_JOIN, player.getName() + " §7has joined the server");
                event.setJoinMessage(null);
            } else if(player.hasPermission("cscore.staff")) {
                if(online.hasPermission("cscore.staff")) {
                    CraftedSouls.getChatManager().sendChat(online, Prefix.STAFF, player.getName() + " §ehas joined the server");
                    event.setJoinMessage(null);
                }
            } else {
                event.setJoinMessage(null);
            }
        }

        CraftedSouls.plugin.getServer().getScheduler().runTaskLater(CraftedSouls.plugin, new Runnable() {
            public void run() {
                /*if (CraftedSouls.getDatabaseManager().userExist(uuid)) {
                    CraftedSouls.getDatabaseManager().createUser(uuid, player.getName(), rankName, 0, 0);
                }*/
                userData.get(uuid).set("selectedchar", 0);
                userData.save(uuid);
            }
        }, 2L);

        CraftedSouls.plugin.getServer().getScheduler().runTaskLater(CraftedSouls.plugin, new Runnable(){

            public void run(){
                CraftedSouls.getDatabaseManager().setUserData(uuid, "RANKS", rankName);

                List<String> pendingFriend = userData.get(uuid).getStringList("PendingFriends");
                int amount = pendingFriend.size();
                if(amount > 0) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.MESSAGESEND, "§7You have §f" + amount + " §7pending friends! Type /friend pending");
                }

                if(!ips.contains(uuid)) {
                    ips.add(uuid);
                    settings.getIPS().set(player.getAddress().getAddress().getHostAddress().replace('.', '-') + ".uuid", ips);
                    settings.saveIPS();
                }

                if(ips.size() > 1) {
                    CraftedSouls.getChatManager().staffMessage("The player: §f" + player.getName() + " §7might have alternate accounts. Use /lookup <player> for more information");
                }
            }
        },1L);

        CraftedSouls.plugin.getServer().getScheduler().runTaskLater(CraftedSouls.plugin, new Runnable() {
            public void run() {
                player.getInventory().clear();
                player.getInventory().setItem(4, GameItemList.characterSelector);
                player.getInventory().setHeldItemSlot(4);
                player.updateInventory();
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Running CraftedSouls §aV." + CraftedSouls.plugin.getDescription().getVersion() + "§7!");
                //UUID LEVEL, XP, CHARSLOT, X, Y, Z, WORLD, FILLED, LOCKED
                //Database Sync (Characters, Current Char Slot, Location, Locked/Filled, Level/XP)

                userData.get(uuid).set("last_ip", player.getAddress().getAddress().getHostAddress());
                userData.save(uuid);
                //db.setUserData(uuid, "USERNAME", player.getName());
                //db.setUserData(uuid, "CHARSLOT", userFile.getString("selectedchar"));
                //db.setUserData(uuid, "BANS", userFile.getString("bans"));
                //db.setUserData(uuid, "WARNINGS", userFile.getString("warnings"));
                //db.setCharData(uuid, "LEVEL", userFile.getString(userFile.getInt("currentchar") + ".level"));
                //db.setCharData(uuid, "XP", userFile.getString(userFile.getInt("currentchar") + ".xp"));
                //db.setCharData(uuid, "X", String.valueOf(player.getLocation().getBlockX()));
                //db.setCharData(uuid, "Y", String.valueOf(player.getLocation().getBlockY()));
                //db.setCharData(uuid, "Z", String.valueOf(player.getLocation().getBlockZ()));
                player.setInvulnerable(false);

                //Health
                player.setHealthScale(0.1);

                if(!CraftedSouls.getDatabaseManager().getDevelopment()) {
                    CraftedSouls.getMainMenuManager().enterMainMenu(player);
                    return;
                }

                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
            }
        }, 5L);
    }
}
