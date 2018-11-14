package it.skyplex.listeners;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventPlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if(Bukkit.getServer().getBannedPlayers().contains(player)) {

            UsersFiles uf = UsersFiles.get(player);

            /*if (uf.getFile().getBoolean("banned.enabled")) {
                String sender = uf.getFile().getString("banned.sender");
                String reason = uf.getFile().getString("banned.reason");
                event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have been &4banned &cfrom &fSkyplex."
                        + "\n" + "\n" + "&7Suspended By: &c" + sender
                        + "\n" + "&7Reason: &a" + reason));
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, event.getKickMessage());
            }*/
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Your account has been suspended from the server.");
        }
    }
}
