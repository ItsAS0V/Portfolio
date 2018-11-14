package it.skyplex.commands.management;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            API.getInstance().getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player)sender;
        ServerFiles server = ServerFiles.getInstance();
        if (!player.hasPermission("skyplex.setspawn")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }
        server.getData().set("spawn.world", player.getLocation().getWorld().getName());
        server.getData().set("spawn.x", player.getLocation().getBlockX());
        server.getData().set("spawn.y", player.getLocation().getBlockY());
        server.getData().set("spawn.z", player.getLocation().getBlockZ());
        server.getData().set("spawn.yaw", player.getLocation().getYaw());
        server.getData().set("spawn.pitch", player.getLocation().getPitch());
        server.saveData();
        World world = player.getWorld();
        int x = server.getData().getInt("spawn.x");
        int y = server.getData().getInt("spawn.y");
        int z = server.getData().getInt("spawn.z");

        api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have successfully set the spawnpoint.");
        world.setSpawnLocation(x, y, z);

        return true;
    }
}
