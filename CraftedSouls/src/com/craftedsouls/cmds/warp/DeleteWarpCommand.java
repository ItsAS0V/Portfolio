package com.craftedsouls.cmds.warp;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        //DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        Settings settings = Settings.getInstance();

        if(!player.hasPermission("cscore.delwarp")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/delwarp <name>");
            return true;
        }

        if(!settings.getWarps().contains(args[0])) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That warp doesn't exist!");
            return true;
        }

        settings.getWarps().set(args[0] + ".x", null);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".y", null);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".z", null);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".yaw", null);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".pitch", null);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".world", null);
        settings.saveWarps();
        settings.reloadWarps();
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have sucessfully removed the warp §f" + args[0]);

        return true;
    }
}
