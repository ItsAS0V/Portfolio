package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("cscore.tp")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length < 1 || args.length == 2 || args.length > 3) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/teleport <player> | /teleport [x] [y] [z]");
            return true;
      }

        if(args.length == 1) {

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "The player §f" + args[0] + " §7isn't online!");
                return true;
            }

            player.teleport(target.getLocation());
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have teleported to §f" + target.getName());

        }

        if(args.length == 3) {
            Location l = null;
            try {
                l = new Location(player.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } catch (Exception e) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Incorrect data, please provide integers X Y and Z!");
                return true;
            }
            player.teleport(l);
            return true;
        }
        return true;
    }
}
