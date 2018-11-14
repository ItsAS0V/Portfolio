package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("cscore.clearinventory")) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cYou don't have permission to use this command!");
            return true;
        }

        player.getInventory().clear();
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have sucessfully cleared your inventory");
        return true;
    }
}
