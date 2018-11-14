package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import com.mysql.jdbc.Buffer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class FactionEasterEgg implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        EntityType boat = EntityType.BOAT;
        //CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "THERE IS NO FACTIONS!");
        return true;
    }
}
