package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(args.length != 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/speed <speed>");
            return true;
        }

        if(!player.hasPermission("cscore.fly")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        float speed;
        try {
            speed = Float.parseFloat(args[0]);
        } catch (Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a float value for the speed");
            return true;
        }

        if(speed > 10 || speed < -10) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT,"Speed value must be between -10 to 10");
            return true;
        }

        if(player.isFlying()) {
            player.setFlySpeed(speed / 10);
        } else {
            player.setWalkSpeed(speed / 10);
        }

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Speed set to " + args[0]);
        return false;
    }
}