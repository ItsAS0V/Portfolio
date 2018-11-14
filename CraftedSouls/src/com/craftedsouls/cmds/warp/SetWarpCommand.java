package com.craftedsouls.cmds.warp;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        Settings settings = Settings.getInstance();

        Player player = (Player) sender;
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();
        World world = player.getLocation().getWorld();

        if(!player.hasPermission("cscore.setwarp")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/setwarp <name>");
            return true;
        }

        String warpName = args[0];

        if(!settings.getWarps().contains(args[0])) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have successfully created the warp §f" + args[0]);
            warpName = args[0];
        } else {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have successfully set the warp §f" +  warpName);
        }

        CraftedSouls.getDatabaseManager().setWarp(args[0], x, y, z, yaw, pitch, world, player);
        settings.getWarps().set(args[0] + ".x", x);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".y", y);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".z", z);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".yaw", yaw);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".pitch", pitch);
        settings.saveWarps();
        settings.getWarps().set(args[0] + ".world", world.getName().toString());
        settings.saveWarps();
        settings.reloadWarps();

        return true;
    }
}
