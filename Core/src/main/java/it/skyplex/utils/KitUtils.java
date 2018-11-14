package it.skyplex.utils;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class KitUtils {

    private ServerFiles serverFiles = ServerFiles.getInstance();

    public void createKit(Player player, String kitName) {
        FileConfiguration kits = serverFiles.getKits();
        PlayerInventory inventory = player.getInventory();

        if(kits.getConfigurationSection("kits." + kitName) != null) {
            messages.sendMessage(player, "&cThat kit already exists!");
            return;
        }
        String path = "kits." + kitName + ".";
        kits.createSection("kits." + kitName);

        for(int i = 0; i < 36; i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null || itemStack.getType().equals(Material.AIR)) continue;

            String slot = path + "items." + i;
            kits.set(slot + ".type", itemStack.getType().toString().toLowerCase());
            kits.set(slot + ".amount", itemStack.getAmount());

            if(itemStack.hasItemMeta()) {
                if (itemStack.getItemMeta().hasDisplayName()) {
                    kits.set(slot + ".name", itemStack.getItemMeta().getDisplayName());
                }
                if (itemStack.getItemMeta().hasLore()) {
                    kits.set(slot + ".lore", itemStack.getItemMeta().getLore());
                }
                if (itemStack.getItemMeta().hasEnchants()) {
                    Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                    List<String> enchantList = new ArrayList<>();
                    for (Enchantment e : itemStack.getEnchantments().keySet()) {
                        int level = enchants.get(e);
                        enchantList.add(e.getName().toLowerCase() + ":" + level);
                    }
                    kits.set(slot + ".enchantments", enchantList);
                }
            }

            serverFiles.saveKits();
        }
    }

    public void giveKit(Player player, String kitName) {
        FileConfiguration kits = serverFiles.getKits();

        if(kits.getConfigurationSection("kits." + kitName) == null) {
            messages.sendMessage(player, "&cThat kit doesn't exist!");
            return;
        }
        String path = "kits." + kitName + ".";
        ConfigurationSection items = kits.getConfigurationSection(path + "items");

        player.getInventory().clear();

        for(String str : items.getKeys(false)) {
            int slot = Integer.parseInt(str);

            if(0 > slot && slot > 36) return;

            String string = path + "items." + slot + ".";
            String type = kits.getString(string + "type");
            String name = kits.getString(string + "name");
            List<String> lore = kits.getStringList(string + "lore");
            List<String> enchants = kits.getStringList(string + "enchantments");
            int amount = kits.getInt(string + "amount");

            ItemStack itemStack = new ItemStack(Material.matchMaterial(type.toUpperCase()), amount);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (name != null) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }
            if (lore != null) {
                itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore.toString())));
            }
            if (enchants != null) {
                for (String s : enchants) {
                    String[] indiEnchants = s.split(":");
                    itemMeta.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]), true);
                }
            }

            itemStack.setItemMeta(itemMeta);
            player.getInventory().setItem(slot, itemStack);
        }
        player.updateInventory();
    }

    public void listKits(Player player) {
        FileConfiguration kits = serverFiles.getKits();

        if(kits.getConfigurationSection("kits").getKeys(false).isEmpty() || !kits.contains("kits")) {
            Core.getManager().getMessage().sendMessage(player, "&cThere are no active kits!");
            return;
        }
        String kitList = kits.getConfigurationSection("kits").getKeys(false).toString().replace("[", "").replace("]", "");

        Core.getManager().getMessage().sendMessage(player, "&6Active Kits: \n" + ChatColor.GRAY + kitList);
    }
}
