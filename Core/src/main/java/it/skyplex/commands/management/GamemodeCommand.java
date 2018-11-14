package it.skyplex.commands.management;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("You must be a player to run this command!");
            return false;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.gamemode")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if (args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", player, "/gamemode <mode> [player]");
            return false;
        }

        GameMode mode = null;

        try {
            mode = GameMode.getByValue(Integer.parseInt(args[0]));
        } catch (Exception e) {
            for (GameMode modes : GameMode.values()) {
                if (modes.name().startsWith(args[0].toUpperCase())) {
                    mode = modes;
                    break;
                }
            }
        }

        if (mode == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, ChatColor.RED + args[0] + " is not a vaild gamemode.");
            return true;
        }

        if (args.length == 1) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You are now in &6" + mode.name().toLowerCase() + " &7mode.");
            player.setGameMode(mode);
            return true;
        }
        if (!sender.hasPermission("skyplex.gamemode.other")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[1]);

        if (target == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&eError 404: &cPlayer not found.");
            return true;
        }

        api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have changed &a" + target.getName() + "'s &7gamemode to &6" + mode.name().toLowerCase() + " &7mode.");
        api.getAPI().getMessages().sendMessage("Skyplex", target, "&7You are now in &6" + mode.name().toLowerCase() + " &7mode.");
        target.setGameMode(mode);
        return true;
    }
}
