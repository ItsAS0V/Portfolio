package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;


        if(!player.hasPermission("cscore.tphere")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length < 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/teleporthere <player>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(target == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "The player §f" + args[0] + " §7isn't online!");
            return true;
        }

        target.teleport(player.getLocation());
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have teleported §f" + args[0] + " §7to you");
        CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, "You have teleported to §f" + player.getName());

        return true;
    }
}
