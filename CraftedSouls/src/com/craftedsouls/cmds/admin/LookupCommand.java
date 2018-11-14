package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class LookupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        DatabaseManager db = CraftedSouls.getDatabaseManager();
        UserData userData = UserData.getInstance();
        BanList list = Bukkit.getBanList(BanList.Type.NAME);
        Settings settings = Settings.getInstance();

        List<String> alternate_accounts = settings.getIPS().getStringList(player.getAddress().getAddress().getHostAddress().replace('.', '-') + ".uuid");

        if (!(player.hasPermission("cscore.playerinfo"))) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/lookup <player>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);

        //Offline Target
        if(target == null) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
            if (op.hasPlayedBefore()) {
                String offlineTargetUUID = op.getUniqueId().toString();
                FileConfiguration offlineConfig = userData.get(offlineTargetUUID);
                List<String> offline_alternate_accounts = settings.getIPS().getStringList(offlineConfig.getString("last_ip").replace('.', '-') + ".uuid");

                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§6Information for player: §c" + op.getName() + " §8(§cOffline§8)");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Possible alternate accounts:");
                String output = "";
                if (!offline_alternate_accounts.isEmpty()) {
                    for (String key : offline_alternate_accounts) {
                        if(offline_alternate_accounts.size() > 1) {
                            Player alt = Bukkit.getPlayer(UUID.fromString(key));
                            if(alt == null) { //Run
                                OfflinePlayer o_alt = Bukkit.getOfflinePlayer(UUID.fromString(key));
                                output += "§f, " + "§c" + o_alt.getName();
                            } else {
                                output += "§f, " + "§a" + alt.getName();
                            }
                        } else {
                            output += "§cThere are no alternate accounts!";
                        }
                    }
                }
                output = output.replaceFirst(",", " ");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, output);
                CraftedSouls.getChatManager().sendNoPrefixChat(player, " ");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Last known IP: §e" + offlineConfig.getString("last_ip"));
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cPunishments:");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, " ");
                if(list.isBanned(op.getName())) {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Banned: §aTrue");
                } else {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Banned: §cFalse");
                }
                if(offlineConfig.getInt("warnings") <= 4) {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §a" + offlineConfig.getInt("warnings"));
                } else if(offlineConfig.getInt("warnings") >= 5 && offlineConfig.getInt("warnings") <= 10) {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §6" + offlineConfig.getInt("warnings"));
                } else {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §c" + offlineConfig.getInt("warnings"));
                }
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
            } else {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player hasn't logged in!");
            }
            return true;
        }

        //Online target
        String targetUUID = target.getUniqueId().toString();
        UserData targetData = UserData.getInstance();
        FileConfiguration targetConfig = targetData.get(targetUUID);

        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§6Information for player: §a" + target.getName() + " §8(§aOnline§8)");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Possible alternate accounts:");
        String output = "";
        if (!alternate_accounts.isEmpty()) {
            for (String key : alternate_accounts) {
                if(alternate_accounts.size() > 1) {
                    String alt = Bukkit.getPlayer(UUID.fromString(key)).getName();
                    if(alt == null) {
                        String o_alt = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
                        output += "§f, " + "§c" + o_alt;
                    }
                    output += "§f, " + "§a" + alt;
                } else {
                    output += "§cThere are no alternate accounts!";
                }
            }
        }
        output = output.replaceFirst(",", " ");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, output);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, " ");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Last known IP: §e" + targetConfig.getString("last_ip"));
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cPunishments:");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, " ");
        if(list.isBanned(target.getName())) {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Banned: §aTrue");
        } else {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Banned: §cFalse");
        }
        if(targetConfig.getInt("warnings") <= 4) {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §a" + targetConfig.getInt("warnings"));
        } else if(targetConfig.getInt("warnings") >= 5 && targetConfig.getInt("warnings") <= 10) {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §6" + targetConfig.getInt("warnings"));
        } else {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §c" + targetConfig.getInt("warnings"));
        }
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8-----------------------------------------------------");

        return true;
    }
}
