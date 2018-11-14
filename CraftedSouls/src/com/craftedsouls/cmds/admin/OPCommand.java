package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(sender, "/op <player>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(!sender.hasPermission("cscore.op")) {
            if(!target.getName().equals(sender.getName())) {
                CraftedSouls.getChatManager().sendChat(sender, Prefix.ALERT, "You can only OP yourself!");
                return true;
            }
            String kickmsg = "§cYour account has been kicked from the server." + "\n" + "\n" + "§7Kicked by: §a§oCONSOLE" + "\n" + "§7Reason: §fAsking for OP";
            target.kickPlayer(kickmsg);
            CraftedSouls.getChatManager().staffMessage(target.getName() + " has attempted to OP themselves");
            return true;
        } else {
            if(target.isOp()) {
                CraftedSouls.getChatManager().sendChat(sender, Prefix.ALERT, "That player is already OPed");
            }
            target.setOp(true);
            CraftedSouls.getChatManager().sendChat(sender, Prefix.GENERAL, "You have OPed " + target.getName());
            CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, "You are now OPe.");
        }

       

        return true;
    }
}
