package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }


        Player player = (Player) sender;
        //DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        Settings settings = Settings.getInstance();

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/warp <name>");
            return true;
        }

        if(!settings.getWarps().contains(args[0])) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThat warp doesn't exist!");
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/warp <name>");
            return true;
        }

        String warpName = args[0];
        double x = settings.getWarps().getInt(args[0] + ".x");
        double y = settings.getWarps().getInt(args[0] + ".y");
        double z = settings.getWarps().getInt(args[0] + ".z");
        String world = settings.getWarps().getString(args[0] + ".world");
        Location location = new Location(Bukkit.getWorld(world), x, y, z);

        player.teleport(location);
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have been teleported to warp §f" + warpName);
        return true;
    }
}
