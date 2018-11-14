package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KillAllCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("You must be a player to use this command!");
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.killall")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
        }

        if (args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/killall <all|players|mobs>");
            return true;
        }

        String type = args[0].toLowerCase();

        if (type.equals("all")) {
            for (Entity e : player.getWorld().getEntities()) {
                if (e instanceof Player) {
                    Player p = (Player) e;
                    p.setHealth(0.0);
                    continue;
                }
                e.remove();
            }
        } else if (type.equals("players")) {
            for (Entity e : player.getWorld().getEntities()) {
                if (e instanceof Player) {
                    Player p = (Player) e;
                    p.setHealth(0.0);
                }
            }
        } else if (type.equals("mobs")) {
            for (Entity e : player.getWorld().getEntities()) {
                if (e instanceof Player) {
                    continue;
                }
                e.remove();
            }
        } else {
            String unknownType = "Unknown kill type: $type";
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/killall <all|players|mobs>");
            api.getAPI().getMessages().sendMessage("Skyplex", sender, unknownType.replace("$type", args[0]));
            return true;
        }

        return true;
    }
}
