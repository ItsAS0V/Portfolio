package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&4Only a player can use this command!");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0) {
            Core.getManager().getKit().listKits(player);
            return true;
        }
        String kit = args[0];

        Core.getManager().getKit().giveKit(player, kit);
        Core.getManager().getMessage().sendMessage(player, "&aYou have received kit &7" + kit);
        return true;
    }
}
