package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerInformationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        DatabaseManager db = CraftedSouls.getDatabaseManager();

        if (!(player.hasPermission("cscore.playerinfo"))) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/pinformation <player>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String uuid = target.getUniqueId().toString();

        if(target == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThe player §f" + args[0] + " §cisn't online!");
            return true;
        }

        String dbUUID = db.getUserData("UUID", uuid);
        String dbName = db.getUserData("USERNAME", uuid);
        String dbRank = db.getUserData("RANKS", uuid);
        String dbWarning = db.getUserData("WARNINGS", uuid);
        String dbBan = db.getUserData("BANS", uuid);
        int dbLevel = 0;

        dbLevel = Integer.parseInt(CraftedSouls.getDatabaseManager().getUserData("LEVEL", uuid));

        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §b" + dbName + "§f's Info §8=======");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7UUID: §f" + dbUUID);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Level: §f" + dbLevel);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Rank: §f" + dbRank);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Warnings: §e" + dbWarning);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Bans: §c" + dbBan);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §b" + dbName + "§f's §fInfo §8=======");

        return true;
    }
}
