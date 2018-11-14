package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class EventInventoryClose implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if (CraftedSouls.getGUIManager().getGUI(inventory) != null) {
            CraftedSouls.getGUIManager().getGUI(inventory).onPlayerCloseInventory(player);
        }
    }
}
