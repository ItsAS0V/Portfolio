package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        int x = settings.getData().getInt("spawn.x");
        int y = settings.getData().getInt("spawn.y");
        int z = settings.getData().getInt("spawn.z");
        int yaw = settings.getData().getInt("spawn.yaw");
        int pitch = settings.getData().getInt("spawn.pitch");
        World world = Bukkit.getWorld(settings.getData().getString("spawn.world"));

        Location spawn = new Location(world, x, y, z, yaw, pitch);

        if(!settings.getData().contains("spawn")) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThere is no spawn location set!");
            return true;
        }

        player.teleport(spawn);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "You have teleported to spawn");

        return true;
    }
}
