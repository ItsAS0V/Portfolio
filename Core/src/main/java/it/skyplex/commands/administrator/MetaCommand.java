package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetaCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&4Only a player can use this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.meta")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if(args.length < 2) {
            api.getAPI().getMessages().invalidUse("Skyplex", player, "/meta <name|lore> <args>");
            return true;
        }
        if(player.getInventory().getItemInMainHand().equals(Material.AIR)) {
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&cYou must have an item in your hand!");
            return true;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(args[0].equalsIgnoreCase("name")) {
            StringBuilder reason = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
            String message = reason.toString().trim();
            String itemName = ChatColor.translateAlternateColorCodes('&', message);

            itemMeta.setDisplayName(itemName);
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&7Set name of the current item to &r" + itemName);
            itemStack.setItemMeta(itemMeta);
        }
        else if(args[0].equalsIgnoreCase("lore")) {
            StringBuilder reason = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
            String message = reason.toString().trim();

            List<String> itemLore = new ArrayList<>();
            itemLore.add(message);

            itemMeta.setLore(color(itemLore));
            itemStack.setItemMeta(itemMeta);
        }

        return true;
    }

    private String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    private List<String> color(List<String> lore){
        return lore.stream().map(this::color).collect(Collectors.toList());
    }
}
