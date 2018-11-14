package com.craftedsouls.cmds.item;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.DamageData;
import com.craftedsouls.data.types.DamageType;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.utils.Prefix;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        //DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();

        if(!player.hasPermission("cscore.item")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }


        if (args.length != 6) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/createitem <itemname> <material> <min-damage> <max-damage> <level> <item-id>");
            return true;
        }

        String name = ChatColor.translateAlternateColorCodes('&', args[0]);


        Material material = Material.getMaterial(args[1]);
        if(material == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Material " + args[1] + " not found!");
            return true;
        }

        int mindamage;
        try {
            mindamage = Integer.parseInt(args[2]);
        } catch (Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type min-damage for the item (Integer)");
            return true;
        }

        int maxdamage;
        try {
        maxdamage = Integer.parseInt(args[3]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type max-damage for the item (Integer)");
            return true;
        }

        short itemID;
        try {
            itemID = Short.parseShort(args[5]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'short' type ID for the item (Small Integer)");
            return true;
        }

        if(mindamage > maxdamage) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please create an item with the min-damage above the max-damage!");
            return true;
        }

        int level;
        try {
            level = Integer.parseInt(args[4]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' for the items level (Integer)");
            return true;
        }

        //"/createitem <itemname> <material> <min-damage> <max-damage> <level> <item-id>"

        String itemName = args[0];
        Material itemMaterial = Material.getMaterial(args[1]);
        int minDamage = Integer.parseInt(args[2]);
        int maxDamage = Integer.parseInt(args[3]);
        int itemLevel = Integer.parseInt(args[4]);
        int id = Integer.parseInt(args[5]);

        DamageData damageData = new DamageData(DamageType.PHSICAL, minDamage, maxDamage);
        ItemBuilder newItem = new ItemBuilder(itemMaterial, itemName, id, damageData, itemLevel);
        newItem.writeData();

         //databaseManager.createItem(name.replace('_', ' '), material, mindamage, maxdamage, DamageType.PHSICAL, level, itemID, player);

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Item called " + name + "§r created on ID " + itemID);

        return true;
    }
}