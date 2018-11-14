package it.skyplex.listeners;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.TimeZone;

public class EventPlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UsersFiles users = UsersFiles.get(player);

        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4-&8] &7" + event.getPlayer().getName()));

        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Date date = new Date();

        users.getFile().set("lastlogoff", date.getTime());
        users.saveFile();

        Core.getPermissionUtils().removeDefaultPermissions(player);
    }
}
