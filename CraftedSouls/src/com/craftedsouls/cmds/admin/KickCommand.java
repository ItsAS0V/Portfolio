package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("cscore.kick"))) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        if(args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(sender, "/kick <player> <reason>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(target == null) {
            CraftedSouls.getChatManager().sendChat(sender, Prefix.ALERT, "§cThe player §f" + args[0] + " §cisn't online!");
            return true;
        }

        StringBuilder reason = new StringBuilder("");

        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        String kickmsg = "§cYour account has been kicked from the server" + "\n" + "\n" + "§7Kicked by: §a" + sender.getName() + "\n" + "§7Reason: §f" + message;

        CraftedSouls.getChatManager().kickMessage(target, message);
        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickmsg));
        return true;
    }
}
