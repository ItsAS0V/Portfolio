package it.skyplex.commands.user;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;

        ServerFiles files = ServerFiles.getInstance();
        FileConfiguration warps = files.getWarps();

        if (args.length > 1 || args.length == 0) {
            if (args.length == 0) {
                try {
                    Core.getManager().getMessage().sendMessage(sender, "&7Warps: &6" + "\n" +
                            warps.getConfigurationSection("warps").getKeys(false).toString()
                                    .replace("[", "")
                                    .replace("]", ""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Core.getManager().getMessage().sendMessage(sender, "&7No warps set! Contact an admin if this is a problem!");
                }
            } else {
                Core.getManager().getMessage().invalidCommandUse(sender, "/warp <warpname>");
            }
            return true;
        }

        String name = args[0].toLowerCase();

        if (warps.getString("warps." + name) == null) {
            Core.getManager().getMessage().sendMessage(sender, "&cWarp &6" + name + "&c doesn't exist!");
            return true;
        }

        Location loc;

        if (files.getData().getBoolean("warp.shorten_coords")) {
            try {
                loc = new Location(Bukkit.getServer().getWorld(warps.getString("warps." + name + ".WORLD")),
                        Math.round(warps.getDouble("warps." + name + ".X")),
                        Math.round(warps.getDouble("warps." + name + ".Y")),
                        Math.round(warps.getDouble("warps." + name + ".Z")),
                        Math.round((float) warps.getDouble("warps." + name + ".PITCH")),
                        Math.round((float) warps.getDouble("warps." + name + ".YAW")));
            } catch (Exception ex) {
                ex.printStackTrace();
                Core.getManager().getMessage().sendMessage(sender, "&cFatal Error occured. Please contact an admin as soon as possible.");
                return true;
            }
        } else {
            try {
                loc = new Location(Bukkit.getServer().getWorld(warps.getString("warps." + name + ".WORLD")),
                        warps.getDouble("warps." + name + ".X"),
                        warps.getDouble("warps." + name + ".Y"),
                        warps.getDouble("warps." + name + ".Z"),
                        (float) warps.getDouble("warps." + name + ".PITCH"),
                        (float) warps.getDouble("warps." + name + ".YAW"));
            } catch (Exception ex) {
                ex.printStackTrace();
                Core.getManager().getMessage().sendMessage(sender, "&cFatal Error occured. Please contact an admin as soon as possible.");
                return true;
            }
        }


        player.teleport(loc);

        Core.getManager().getMessage().sendMessage(sender, "&7WHOOOSH! Welcome to &6" + name + "&7!");

        return true;
    }
}
