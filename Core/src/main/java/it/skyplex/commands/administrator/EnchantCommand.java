package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&4Only a player can use this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.meta.enchant")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if(args.length < 2) {
            api.getAPI().getMessages().invalidUse("Skyplex", player, "/enchant <enchant> <lvl>");
            return true;
        }
        if(player.getInventory().getItemInMainHand().equals(Material.AIR)) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&cYou must have an item in your hand!");
            return true;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
        int level = 0;
        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&cThe level must be a valid number!");
            return true;
        }
        try {
            itemStack.addUnsafeEnchantment(enchantment, level);
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&7Enchanted with &5" + enchantment.getName() + " &7lvl: &d" + level);
        } catch (IllegalArgumentException e) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&cThe enchantment must be a valid enchantment!");
            return true;
        }

        return true;
    }
}
