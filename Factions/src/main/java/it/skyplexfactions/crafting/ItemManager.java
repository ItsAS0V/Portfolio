package it.skyplexfactions.crafting;

import it.skyplex.Core;
import it.skyplexfactions.Factions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {
    /**
     * Reactor Types
     * Level 1 (I): Iron Reactor
     * Level 2 (II): Gold Reactor
     * Level 3 (II): Diamond Reactor
     * Level 4 (IV): Emerald Reactor
     * Level 5 (V): Obsidian Reactor
     * Level 6 (VI): Atomic Reactor
     * Level 7 (VII): Ionic Reactor
     * Level 8 (VIII): Plasma Reactor
     * Level 9 (IX): Nuclear Reactor
     * Level 10 (X): Fission Reactor
     */

    /**
     * DEBUG METHOD
     * @param player
     */
    public void giveAllReactors(Player player) {
        player.getInventory().addItem(createReactor("Fission", ChatColor.DARK_RED, 10));
    }

    public ItemStack createReactor(String name, ChatColor color, int level) {
        ItemStack reactor = new ItemStack(Material.END_CRYSTAL);
        ItemMeta reactorMeta = reactor.getItemMeta();

        reactorMeta.setDisplayName(color + name + " Reactor" + ChatColor.ITALIC + " (Lvl. " + Core.getManager().getName().intToRomanNumeral(level) + ")");
        reactorMeta.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "This item goes in the upgrade space of the Nexus."));
        reactor.setItemMeta(reactorMeta);

        return reactor;
    }
}
