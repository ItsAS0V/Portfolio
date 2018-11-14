package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("cscore.unban")) {
            CraftedSouls.getChatManager().incorrectPermissions((Player) sender);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(sender, "/unban <player>");
            return true;
        }

        BanList list = Bukkit.getBanList(BanList.Type.NAME);
        String target = args[0];

        if(list.isBanned(target)) {
            CraftedSouls.getChatManager().unbanMessage(target);
            list.pardon(target);
        } else {
            sender.sendMessage(CraftedSouls.getChatManager().sendChat(sender, Prefix.ALERT, "§f" + target + " §7isn't banned!"));
        }
        return true;
    }
}
