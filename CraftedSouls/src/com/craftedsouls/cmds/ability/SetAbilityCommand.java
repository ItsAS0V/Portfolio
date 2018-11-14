package com.craftedsouls.cmds.ability;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.AbilityData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.data.builders.AbilityBuilder;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetAbilityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        AbilityData abilityData = AbilityData.getInstance();
        int currentChar = userData.getCurrentChar(uuid);

        if (args.length != 2) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/setability [slot] [id]");
            return true;
        }

        int slot;
        try {
            slot = Integer.parseInt(args[0]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' for the slot (Integer)");
            return true;
        }

        if(slot < 1 || slot > 8) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Slot range must be between 1-8");
            return true;
        }

        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid, 'int' for the ID (Integer)");
            return true;
        }

        if(!abilityData.abilityExists(id)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "An ability on that ID doesn't exist!");
            return true;
        }

        AbilityBuilder ability = new AbilityBuilder(id);
        ability.build();

        userData.get(uuid).set("characters." + currentChar + ".combat.abilities." + slot + ".id", id);
        userData.save(uuid);

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Ability " + ability.name + " has been assigned to slot " + slot);

        return true;
    }
}
