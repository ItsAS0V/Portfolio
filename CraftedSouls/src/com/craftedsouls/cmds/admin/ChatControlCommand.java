package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatControlCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("cscore.chat")) {
            sender.sendMessage(CraftedSouls.getChatManager().incorrectPermissions(sender));
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(sender, "/chat <clear|mute>");
            return true;
        }

        DatabaseManager db = CraftedSouls.getDatabaseManager();

        if(args[0].equalsIgnoreCase("mute")) {
            if(db.getMute() == false) {
                db.setMute(true);

                for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                    CraftedSouls.getChatManager().sendChat(online, Prefix.ALERT, "§cThe chat is now muted");
                }
                return true;

            } else if(db.getMute() == true) {
                db.setMute(false);
                for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                    CraftedSouls.getChatManager().sendChat(online, Prefix.ALERT, "§cThe chat is no longer muted");
                }
                return true;
            }
        }

        if(args[0].equalsIgnoreCase("clear")) {
            for(int i = 0; i < 150; i++) {
                Bukkit.broadcastMessage("");
            }
            for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                CraftedSouls.getChatManager().sendChat(online, Prefix.ALERT, "§cThe chat has been cleared");
            }
        }
        return true;
    }
}
