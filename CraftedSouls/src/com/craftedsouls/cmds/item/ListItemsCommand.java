package com.craftedsouls.cmds.item;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.ItemData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ListItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
         /*if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;

        CraftedSouls.getChatManager().sendNoPrefixChat("§8======= §aItems§8 =======", player);
        String output = "";
        for(File item : ItemData.getDir().listFiles()) {
            short id = Short.parseShort(item.getName().replaceAll(".yml", ""));
            FileConfiguration config = ItemData.getInstance().get(id);
            String name = config.getString(id + ".name");
            int maxDamage = config.getInt(id + ".damage.max");
            int minDamage = config.getInt(id + ".damage.min");

            player.sendMessage("§8(§a" + id  + "§8) §7- §f" + name + " §8(§c" + minDamage + " §7- §c" + maxDamage + "§8)");

        }
        CraftedSouls.getChatManager().sendNoPrefixChat("§8======= §aItems§8 =======", player);

        return false;*/
        return true;
    }
}