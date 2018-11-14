package com.craftedsouls.cmds.ability;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.builders.AbilityBuilder;
import com.craftedsouls.data.types.AbilityType;
import com.craftedsouls.utils.Prefix;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateAbilityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        //DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();

        if(!player.hasPermission("cscore.ability")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        //String name, int id, Material material int cooldown, AbilityType abilityType, DamageFocusType focusType

        if (args.length != 7) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/createability <abilityname> <id> <material> <cooldown> <ability-type> <focus-type> <power>");
            return true;
        }

        //Name
        String name = ChatColor.translateAlternateColorCodes('&', args[0]);

        //Id
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type id for the item (Integer)");
            return true;
        }

        //Material
        Material material = Material.getMaterial(args[2]);
        if(material == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Material " + args[1] + " not found!");
            return true;
        }

        //Cooldown
        int cooldown;
        try {
            cooldown = Integer.parseInt(args[3]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'short' type ID for the item (Integer)");
            return true;
        }

        //Ability type
        AbilityType abilitytype;
        try {
            abilitytype = AbilityType.valueOf(args[4]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type ability-type for the item (Integer)");
            return true;
        }

        //Ability focus type
        /*DamageFocusType damageFocusType;
        try {
            damageFocusType = DamageFocusType.valueOf(args[5]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type damage-focus for the item. (Integer)");
            return true;
        }

        //Power
        int power = 0;
        try {
            power = Integer.valueOf(args[6]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' type power for the item. (Integer)");
            return true;
        }


        AbilityBuilder newAbility = new AbilityBuilder(name, id, material, cooldown, abilitytype, damageFocusType, power);
        newAbility.writeData(id);

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Ability called " + name + "§r created on ID " + id);*/

        return true;
    }

}
