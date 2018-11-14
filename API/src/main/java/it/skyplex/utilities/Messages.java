package it.skyplex.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {
    private String colorize(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(colorize("&b[&c&lBroadcast&8] &7> &r" + message));
    }

    public void sendMessage(String plugin, CommandSender sender, String message) {
        sender.sendMessage(colorize("&8[&b&l" + plugin + "&8] &7> &f" + message));
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void noPermission(String plugin, CommandSender sender) {
        sendMessage(plugin, sender, "&cSorry, but you don't have access to this command.");
    }

    public void invalidUse(String plugin, CommandSender sender, String usage) {
        sendMessage(plugin, sender, "&7Invalid Usage! Usage: &6" + usage);
    }

    public void sendStaffMessage(String message) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            if(online.hasPermission("skyplex.alert")) {
                sendMessage(online, message);
            }
        }
    }

    public void sendStaffMessage(String plugin, String message) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            if(online.hasPermission("skyplex.alert")) {
                sendMessage(plugin, online, message);
            }
        }
    }
}
