package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MOTDCommand  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();
        String uuid = player.getUniqueId().toString();

        if(!player.hasPermission("cscore.motd")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/motd <news|state> <message>");
            return true;
        }

        StringBuilder reason = new StringBuilder("");

        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        if(args[0].equalsIgnoreCase("news")) {
            if(args.length == 1) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You can't set the message to null");
                return true;
            }
            settings.getData().set("news", message);
            settings.saveData();
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have changed the news to " + ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }
        else if(args[0].equalsIgnoreCase("state")) {
            if(args.length == 1) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You can't set the message to null");
                return true;
            }
            settings.getData().set("state", message);
            settings.saveData();
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have changed the state to " + ChatColor.translateAlternateColorCodes('&', message));
        }

        return true;
    }
}
