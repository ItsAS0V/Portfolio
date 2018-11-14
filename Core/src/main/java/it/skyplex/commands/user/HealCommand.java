package it.skyplex.commands.user;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&4Only a player can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp() && !player.hasPermission("skyplex.heal") || !player.hasPermission("skyplex.override")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        player.setHealth(20.0);
        player.setFoodLevel(20);
        return true;
    }

}
