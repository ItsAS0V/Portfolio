package com.craftedsouls.utils.managers;

import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ChatManager {

    public String sendChat(CommandSender sender, String prefix, String message) {
        String infoString = prefix + message;
        sender.sendMessage(infoString);
        return infoString;
    }

    public String sendNoPrefixChat(CommandSender sender, String message) {
        String infoString = message;
        sender.sendMessage(infoString);
        return infoString;
    }

    public String sendConsoleError(CommandSender sender) {
        String consoleError = "You aren't allowed to use this command!";
        sendChat(sender, Prefix.ALERT, consoleError);
        return consoleError;
    }

    public String incorrectPermissions(CommandSender sender) {
        String incorrectPermissions = "§cYou do not have permission to use this command";
        sendChat(sender, Prefix.ALERT, incorrectPermissions);
        return incorrectPermissions;
    }

    public String incorrectUsage(CommandSender sender, String usage) {
        String incorrectUseage = usage;
        sendChat(sender, Prefix.ALERT, "§7Incorrect usage! Usage: " + usage);
        return incorrectUseage;
    }

    public String kickMessage(Player target, String reason) {
        String kickMessage = Prefix.STAFF + target.getName() + " has been kicked for §c" + reason;
        Bukkit.broadcast(kickMessage, "cscore.staff");
        return kickMessage;
    }

    public String banMessage(String target, String reason) {
        String banMessage = Prefix.STAFF + target + " has been banned for: §f" + reason;
        Bukkit.broadcast(banMessage, "cscore.staff");
        return banMessage;
    }

    public String unbanMessage(String target) {
        String banMessage = Prefix.STAFF + target + " has been unbanned";
        Bukkit.broadcast(banMessage, "cscore.staff");
        return banMessage;
    }

    public String staffMessage(String message) {
        Bukkit.broadcast(Prefix.STAFF + message, "cscore.staff");
        return message;
    }
}
