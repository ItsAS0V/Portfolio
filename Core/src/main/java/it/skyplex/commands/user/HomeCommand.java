package it.skyplex.commands.user;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;

        UsersFiles users = UsersFiles.get(player);
        FileConfiguration playerFile = users.getFile();

        if (args.length > 1 || args.length == 0) {
            if (args.length == 0) {
                try {
                    Core.getManager().getMessage().sendMessage(sender, "&7Homes: &6" + "\n" +
                            playerFile.getConfigurationSection("homes").getKeys(false).toString()
                                    .replace("[", "")
                                    .replace("]", ""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Core.getManager().getMessage().sendMessage(sender, "&7No homes set! try &6/sethome <home> &7to get set a home!");
                }
            } else {
                Core.getManager().getMessage().invalidCommandUse(sender, "/home <home>");
            }
            return true;
        }

        String name = args[0].toLowerCase();

            if (playerFile.getString("homes." + name) == null) {
                Core.getManager().getMessage().sendMessage(sender, "&cHome &6" + name + "&c doesn't exist!");
                return true;
            }

            Location loc;
                try {
                    loc = new Location(Bukkit.getServer().getWorld(playerFile.getString("homes." + name + ".WORLD")),
                            playerFile.getDouble("homes." + name + ".X"),
                            playerFile.getDouble("homes." + name + ".Y"),
                            playerFile.getDouble("homes." + name + ".Z"),
                            (float) playerFile.getDouble("homes." + name + ".PITCH"),
                            (float) playerFile.getDouble("homes." + name + ".YAW"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Core.getManager().getMessage().sendMessage(sender, "&cFatal Error occurred. Please contact an administrator as soon as possible.");
                    return true;
                }

            player.teleport(loc);
            Core.getManager().getMessage().sendMessage(sender, "&7Teleported to home &a" + name + "&7!");

            return true;
    }
}