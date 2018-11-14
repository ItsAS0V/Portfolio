package com.craftedsouls.cmds.item;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.ItemData;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.utils.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("cscore.item")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length != 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/getitem <item-id>");
            return true;
        }

        ItemData itemData = ItemData.getInstance();

        if(!itemData.itemExists(Integer.parseInt(args[0]))) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That ID doesn't exist!");
            return true;
        }

        ItemBuilder itemBuilder = new ItemBuilder(Integer.parseInt(args[0]));
        itemBuilder.build();
        player.getInventory().addItem(itemBuilder.createdItem);

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Giving item to " + player.getName());

        return true;
    }
}