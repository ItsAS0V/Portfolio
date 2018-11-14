package it.skyplex.commands.user;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;
        ServerFiles server = ServerFiles.getInstance();

        if (server.getData().getString("spawn.world") != null) {
            int x = server.getData().getInt("spawn.x");
            int y = server.getData().getInt("spawn.y");
            int z = server.getData().getInt("spawn.z");
            float yaw = server.getData().getInt("spawn.yaw");
            float pitch = server.getData().getInt("spawn.pitch");
            World world = Bukkit.getWorld(server.getData().getString("spawn.world"));
            Location spawn = new Location(world, x + 0.5, y + 1, z + 0.5, yaw, pitch);
            player.teleport(spawn);
        } else {
            Core.getManager().getMessage().sendMessage(player, "&cSpawn not set!\n &7going to world's default spawn!");
            player.teleport(player.getWorld().getSpawnLocation());
        }

        return true;
    }
}
