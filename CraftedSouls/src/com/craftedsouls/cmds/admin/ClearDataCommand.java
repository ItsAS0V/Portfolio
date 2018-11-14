package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearDataCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        if (!(player.hasPermission("cscore.cleardata"))) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/cleardata <ban|warnings|level> <user>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[1]);
        String uuid = target.getUniqueId().toString();
        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        short value = 0;
        if(databaseManager.userExist(uuid)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player doesn't exist!");
            return true;
        }

        if(args[0].equalsIgnoreCase("level")) {
            databaseManager.setUserData(uuid, "LEVEL", Short.toString(value));
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You have cleared the level data for " + target.getName());
            if(target.isOnline()) {
                CraftedSouls.getChatManager().sendChat(target, Prefix.ALERT, "Your data for " + args[0] + " has been cleared by §c" + player.getName());
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("ban")) {
            databaseManager.setUserData(uuid, "BANS", Short.toString(value));
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You have cleared the ban data for " + target.getName());
            if(target.isOnline()) {
                CraftedSouls.getChatManager().sendChat(target, Prefix.ALERT, "Your data for " + args[0] + " has been cleared by §c" + player.getName());
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("warnings")) {
            databaseManager.setUserData(uuid, "WARNINGS", Short.toString(value));
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You have cleared the warning data for " + target.getName());
            if(target.isOnline()) {
                CraftedSouls.getChatManager().sendChat(target, Prefix.ALERT, "Your data for " + args[0] + " has been cleared by §c" + player.getName());
            }
            return true;
        }
        return true;
    }
}
