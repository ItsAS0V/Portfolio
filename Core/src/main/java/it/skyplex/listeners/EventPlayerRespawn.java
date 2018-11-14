package it.skyplex.listeners;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventPlayerRespawn implements Listener {

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        ServerFiles server = ServerFiles.getInstance();

        if (server.getData().getString("spawn.world") != null) {
            int x = server.getData().getInt("spawn.x");
            int y = server.getData().getInt("spawn.y");
            int z = server.getData().getInt("spawn.z");
            float yaw = server.getData().getInt("spawn.yaw");
            float pitch = server.getData().getInt("spawn.pitch");
            World world = Bukkit.getWorld(server.getData().getString("spawn.world"));
            Location spawn = new Location(world, x, y + 1, z, yaw, pitch);
            event.setRespawnLocation(spawn);
        } else {
            Core.getManager().getMessage().sendMessage(player, "&cSpawn not set!\n &7going to world's default spawn!");
            event.setRespawnLocation(player.getWorld().getSpawnLocation());
        }
    }
}
