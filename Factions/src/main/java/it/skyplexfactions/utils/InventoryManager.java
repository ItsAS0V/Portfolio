package it.skyplexfactions.utils;

import it.skyplexfactions.database.FactionsFiles;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class InventoryManager {
    private Inventory inventory;

    public Inventory nexusInventory(String fName) {
        inventory = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', "&b" + fName + " &5&lNexus"));

        FactionsFiles factionsFiles = FactionsFiles.get(fName);

        OfflinePlayer nexusSkull = Bukkit.getOfflinePlayer("MHF_ArrowUp");
        ItemStack upgradeNexus = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta upgradeNexusMeta = (SkullMeta) upgradeNexus.getItemMeta();
        upgradeNexusMeta.setDisplayName(ChatColor.GRAY + "Upgrades");
        upgradeNexusMeta.setLore(Arrays.asList(ChatColor.DARK_AQUA + "Current Level: " + ChatColor.WHITE + factionsFiles.getNexusLevel()));
        upgradeNexusMeta.setOwningPlayer(nexusSkull);
        upgradeNexus.setItemMeta(upgradeNexusMeta);

        OfflinePlayer informationSkull = Bukkit.getOfflinePlayer("MHF_Question");
        ItemStack factionInfo = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta factionInfoMeta = (SkullMeta) factionInfo.getItemMeta();
        factionInfoMeta.setDisplayName(ChatColor.GRAY + "Faction Information");
        factionInfoMeta.setLore(Arrays.asList(ChatColor.DARK_AQUA + "Want to find information about your faction?", ChatColor.DARK_AQUA + "You can click here!"));
        factionInfoMeta.setOwningPlayer(informationSkull);
        factionInfo.setItemMeta(factionInfoMeta);


        inventory.setItem(4, upgradeNexus);
        inventory.setItem(8, factionInfo);

        return inventory;
    }
}
