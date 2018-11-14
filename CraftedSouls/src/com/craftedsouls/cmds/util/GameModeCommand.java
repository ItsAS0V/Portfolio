package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("cscore.gamemode")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length < 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/gamemode <mode> [player]");
            return true;
        }

        GameMode mode = null;

        try {
            mode = GameMode.getByValue(Integer.parseInt(args[0]));
        }
        catch (Exception e) {
            for (GameMode modes : GameMode.values()) {
                if (modes.name().startsWith(args[0].toUpperCase())) {
                    mode = modes;
                    break;
                }
            }
        }

        if(mode == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT,args[0] + " isn't a valid gamemode");
            return true;
        }

        if(args.length == 2) {
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if(target == null) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "The player §f" + args[1] + " §7isn't online!");
                return true;
            }
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have changed §f" + target.getName() + "'s §7gamemode to §f" + mode.name().toLowerCase());
            CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, "Your gamemode has been changed to §f" + mode.name().toLowerCase() + "§7 by §f" + player.getName());
            target.setGameMode(mode);
            return true;
        }

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have changed your gamemode to §f" + mode.name().toLowerCase());
        player.setGameMode(mode);
        return true;
    }
}
