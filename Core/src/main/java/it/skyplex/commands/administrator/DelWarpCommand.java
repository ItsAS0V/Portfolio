package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp() || !player.hasPermission("skyplex.warps.delete") || !player.hasPermission("skyplex.override")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        String name = args[0].toLowerCase();

        ServerFiles files = ServerFiles.getInstance();
        FileConfiguration warps = files.getWarps();

        if (args.length > 1 || args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/delwarp <warpname>");
            return true;
        }


        if (warps.getString("warps." + name) == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cWarp not found: &6" + name);
            return true;
        }

        warps.set("warps." + name, null);

        api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Removed warp &a" + name + "&7 successfully.");

        files.saveWarps();

        return true;
    }
}
