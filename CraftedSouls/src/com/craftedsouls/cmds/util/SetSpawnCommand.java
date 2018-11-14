package com.craftedsouls.cmds.util;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        if(!player.hasPermission("cscore.setspawn")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        settings.getData().set("spawn.world", player.getLocation().getWorld().getName());
        settings.saveData();
        settings.getData().set("spawn.x", player.getLocation().getX());
        settings.saveData();
        settings.getData().set("spawn.y", player.getLocation().getY());
        settings.saveData();
        settings.getData().set("spawn.z", player.getLocation().getZ());
        settings.saveData();
        settings.getData().set("spawn.yaw", player.getLocation().getYaw());
        settings.saveData();
        settings.getData().set("spawn.pitch", player.getLocation().getPitch());
        settings.saveData();
        settings.reloadData();
        World world = player.getWorld();
        int x = settings.getData().getInt("spawn.x");
        int y = settings.getData().getInt("spawn.y");
        int z = settings.getData().getInt("spawn.z");

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have sucessfully set the spawnpoint");
        world.setSpawnLocation(x, y, z);

        return true;
    }
}
