package it.skyplex.commands.user;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("skyplex.fly")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }


        if (args.length < 1) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
                api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have &aenabled &7flight.");
            } else {
                player.setAllowFlight(false);
                player.setFlying(false);
                api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have &cdisabled &7flight.");
            }
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                api.getAPI().getMessages().sendMessage("Skyplex", player, "&cInvalid Player: &6" + args[1]);
                return true;
            }

            if (!player.hasPermission("skyplex.fly.other")) {
                return true;
            }

            player = Bukkit.getPlayer(args[0]);
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
                api.getAPI().getMessages().sendMessage("Skyplex", player, "&aenabled &7flight.");
            } else {
                player.setAllowFlight(false);
                player.setFlying(false);
                api.getAPI().getMessages().sendMessage("Skyplex", player, "&cdisabled &7flight.");
            }
        }
        return true;
    }
}
