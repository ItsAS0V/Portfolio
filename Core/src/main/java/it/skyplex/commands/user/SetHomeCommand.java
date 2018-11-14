package it.skyplex.commands.user;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 2) {
            Core.getManager().getMessage().invalidCommandUse(sender, "/sethome <home>");
            return true;
        }

        String name;

        if (args.length == 0) {
            name = "home";
        } else {
            name = args[0].toLowerCase();
        }

        UsersFiles users = UsersFiles.get(player);
        FileConfiguration playerFile = users.getFile();

        if (playerFile.get("homes." + name) != null) {
            Core.getManager().getMessage().sendMessage(sender, "&cWarning: Home is being overwritten from location:\n &6" +
                    playerFile.getDouble("homes." + name + ".X") + " "
                    + playerFile.getDouble("homes." + name + ".Y") + " "
                    + playerFile.getDouble("homes." + name + ".Z"));
        }

        Location loc = ((Player) sender).getLocation();

        playerFile.set("homes." + name + ".WORLD", loc.getWorld().getName());
        playerFile.set("homes." + name + ".X", loc.getX());
        playerFile.set("homes." + name + ".Y", loc.getY());
        playerFile.set("homes." + name + ".Z", loc.getZ());
        playerFile.set("homes." + name + ".PITCH", loc.getPitch());
        playerFile.set("homes." + name + ".YAW", loc.getYaw());

        Core.getManager().getMessage().sendMessage(sender, "&7Set home &a" + name + "&7 successfully at X: &6" + loc.getX() + " &7Y:&6 " + loc.getY() + " &7Z: &6" + loc.getZ());

        users.saveFile();

        return true;
    }
}
