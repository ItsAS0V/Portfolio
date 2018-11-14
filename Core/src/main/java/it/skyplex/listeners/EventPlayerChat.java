package it.skyplex.listeners;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import it.skyplex.database.UsersFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;
import java.util.TimeZone;

import static it.skyplex.utils.MessageUtils.Colorize;

public class EventPlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        ServerFiles server = ServerFiles.getInstance();
        UsersFiles users = UsersFiles.get(player);

        String ranks = users.getRank();
        String prefix = ChatColor.translateAlternateColorCodes('&', server.getPermissions().getString("groups." + ranks + ".prefix"));

        if(server.getData().getBoolean("chat.muted") && !player.hasPermission("skyplex.bypass")) {
            Core.getManager().getMessage().sendMessage(player, "&cThe chat is currently muted.");
            event.setCancelled(true);
            return;
        }

        if (users.isMuted()) {
            TimeZone.setDefault(TimeZone.getTimeZone("EST"));
            Date date = new Date();
            if (users.getUnmuteTime() == -1 || users.getUnmuteTime() > date.getTime()) {
                Core.getManager().getMessage().sendMessage(player, "&cYou have been muted for: &a" + users.getMutedReason());
                event.setCancelled(true);
                if (users.getUnmuteTime() < 0) {
                    Core.getManager().getMessage().sendMessage(player, "&cYour mute is permanent.");
                    event.setCancelled(true);
                } else {
                    Core.getManager().getMessage().sendMessage(player, "&cYour mute expires in: &6" + Core.getManager().getDateUtils().formatDateDiff(users.getUnmuteTime()));
                    event.setCancelled(true);
                }
                return;
            } else {
                users.unmute();
            }
        }

        if(player.isOp()) {
            event.setFormat(prefix + ChatColor.WHITE + " " + player.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
        event.setFormat(prefix + ChatColor.WHITE + " " + player.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + Colorize(event.getMessage()));
    }
}
