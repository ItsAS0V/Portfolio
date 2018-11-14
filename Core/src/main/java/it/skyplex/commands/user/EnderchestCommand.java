package it.skyplex.commands.user;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&4Must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("skyplex.echest")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        if (args.length > 1) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/echest or /echest <player>");
            return true;
        }
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            return true;
        }

        if (!player.hasPermission("skyplex.echest.other")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cError &6404&c: Player not found.");
            return true;
        }

        player.openInventory(target.getEnderChest());
        api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Opening &a" + target.getName() + "'s&7 Ender Chest.");

        return true;
    }
}
