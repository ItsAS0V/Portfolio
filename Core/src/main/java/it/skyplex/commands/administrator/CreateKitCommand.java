package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateKitCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "Only players can run this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.ckit")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if(args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", player, "/ckit [kitName]");
            return true;
        }
        String kit = args[0];

        Core.getManager().getKit().createKit(player, kit);
        api.getAPI().getMessages().sendMessage("Skyplex", player, "You created a kit named &6" + kit);
        return true;
    }
}
