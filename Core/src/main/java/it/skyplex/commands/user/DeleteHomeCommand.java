package it.skyplex.commands.user;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DeleteHomeCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;

        String name = args[0].toLowerCase();

        UsersFiles users = UsersFiles.get(player);
        FileConfiguration playerFile = users.getFile();

        if (args.length > 1 || args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/delhome <home>");
            return true;
        }


        if (playerFile.getString("homes." + name) == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cHome not found: &6" + name);
            return true;
        }
        playerFile.set("homes." + name, null);

        api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Removed home &a" + name + "&7 successfully.");
        users.saveFile();

        return true;
    }
}
