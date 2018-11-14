package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class EventMoveItem implements Listener {

    @EventHandler
    public void onInventoryMove(InventoryDragEvent event) {

        if (CraftedSouls.getGUIManager().getGUI(event.getView().getTopInventory()) != null) {
            event.setCancelled(true);
        }
    }
}



