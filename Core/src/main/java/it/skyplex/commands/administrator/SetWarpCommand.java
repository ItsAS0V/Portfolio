package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp() || !player.hasPermission("skyplex.warps.set") || !player.hasPermission("skyplex.override")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        String name = args[0].toLowerCase();

        ServerFiles files = ServerFiles.getInstance();
        FileConfiguration warps = files.getWarps();

        if (args.length > 2 || args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/setwarp <warpname>");
            return true;
        }


        if (warps.getString("warps." + name) != null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cWarning: Warp is being overwritten from location:\n &6" +
                    warps.getDouble("warps." + name + ".X") + " "
                    + warps.getDouble("warps." + name + ".Y") + " "
                    + warps.getDouble("warps." + name + ".Z"));
        }

        Location loc = ((Player) sender).getLocation();

        warps.set("warps." + name + ".WORLD", loc.getWorld().getName());
        warps.set("warps." + name + ".X", loc.getX());
        warps.set("warps." + name + ".Y", loc.getY());
        warps.set("warps." + name + ".Z", loc.getZ());
        warps.set("warps." + name + ".PITCH", loc.getPitch());
        warps.set("warps." + name + ".YAW", loc.getYaw());

        api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Set warp &a" + name + "&7 successfully at X: &6" + loc.getX() + " &7Y:&6 " + loc.getY() + " &7Z: &6" + loc.getZ());

        files.saveWarps();

        return true;
    }
}