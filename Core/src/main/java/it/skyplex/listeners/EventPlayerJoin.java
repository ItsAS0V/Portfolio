package it.skyplex.listeners;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ServerFiles server = ServerFiles.getInstance();
        UsersFiles users = UsersFiles.get(player);

        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a+&8] &7" + event.getPlayer().getName()));

        Core.getPermissionUtils().addDefaultPermission(player);

        if (!server.getPermissions().getConfigurationSection("groups").contains(users.getRank())) {
            users.setRank(server.getPermissions().getString("default_group"));
            users.saveFile();
            Core.getManager().getMessage().sendMessage(player, "&7&m----------------------------------------");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "                   &c&lERROR");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "&7Sorry, but your rank was removed from the server.");
            Core.getManager().getMessage().sendMessage(player, "&7Please contact an &4Administrator &7for assistance.");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "&7&m----------------------------------------");
        }

        if(users.getRank() == null && users.isNew()) {
            users.setRank(server.getPermissions().getString("default_group"));
            Core.getManager().getMessage().sendMessage(player, "&7&m----------------------------------------");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "                   &c&lERROR");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "&7Sorry, but there was an error receiving your rank.");
            Core.getManager().getMessage().sendMessage(player, "&7Please contact an &4Administrator &7for assistance.");
            Core.getManager().getMessage().sendMessage(player, " ");
            Core.getManager().getMessage().sendMessage(player, "&7&m----------------------------------------");
        }

        if (users.isNew()) {
            users.setRank(server.getPermissions().getString("default_group"));
            if (server.getData().getString("spawn.world") != null) {
                int x = server.getData().getInt("spawn.x");
                int y = server.getData().getInt("spawn.y");
                int z = server.getData().getInt("spawn.z");
                float yaw = server.getData().getInt("spawn.yaw");
                float pitch = server.getData().getInt("spawn.pitch");
                World world = Bukkit.getWorld(server.getData().getString("spawn.world"));
                Location spawn = new Location(world, x, y, z, yaw, pitch);
                player.teleport(spawn.add(0.5, 0, 0.5));
            } else {
                player.teleport(player.getWorld().getSpawnLocation());
            }
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Welcome " + ChatColor.GRAY + player.getName() + ChatColor.LIGHT_PURPLE + " to Skyplex!");
        }
    }
}
