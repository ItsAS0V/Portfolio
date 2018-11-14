package it.skyplex.utilities;

import it.skyplex.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Server {
    private API api = API.getInstance();

    public void warnUser(CommandSender sender, Player target, String reason) {
        api.getAPI().getMessages().sendMessage(target, "&7&m----------------------------------------");
        api.getAPI().getMessages().sendMessage(target, " ");
        api.getAPI().getMessages().sendMessage(target, "                   &c&lWARNING");
        api.getAPI().getMessages().sendMessage(target, " ");
        api.getAPI().getMessages().sendMessage(target, "&7You have received a warning from: &c" + sender.getName());
        api.getAPI().getMessages().sendMessage(target, "&7Reason: &f" + reason);
        api.getAPI().getMessages().sendMessage(target, " ");
        api.getAPI().getMessages().sendMessage(target, "&7&m----------------------------------------");

        api.getAPI().getMessages().sendMessage(sender, "&7You have &asucessfully &7sent a warning to &c" + target.getName() + "&7.");
    }

    public void kickUser(CommandSender sender, Player target, String reason) {
        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou have been &4kicked &cfrom &fSkyplex. \n \n"
                + "&7Reason: &f" + reason + "\n"
                + "&7Kicked By: &c" + sender.getName()));
        api.getAPI().getMessages().sendStaffMessage(ChatColor.GOLD + target.getName() + " &7has been kicked.");
    }

    public void lockdownServer(Player player) {
        if(player.hasPermission("skyplex.bypass")) {
            return;
        }
        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cSorry, but the network is currently in &4&llockdown."));
        Bukkit.getServer().setWhitelist(true);
    }
}
