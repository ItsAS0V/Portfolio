package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        if(!player.hasPermission("cscore.settutorial")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        settings.getData().set("tutorial.world", player.getLocation().getWorld().getName());
        settings.saveData();
        settings.getData().set("tutorial.x", player.getLocation().getX());
        settings.saveData();
        settings.getData().set("tutorial.y", player.getLocation().getY());
        settings.saveData();
        settings.getData().set("tutorial.z", player.getLocation().getZ());
        settings.saveData();
        settings.getData().set("tutorial.yaw", player.getLocation().getYaw());
        settings.saveData();
        settings.getData().set("tutorial.pitch", player.getLocation().getPitch());
        settings.saveData();
        settings.reloadData();
        World world = player.getWorld();

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have sucessfully set the spawnpoint");

        return true;
    }
}
