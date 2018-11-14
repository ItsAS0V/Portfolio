package com.craftedsouls.events;

import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class EventBlockPlace implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        ItemStack playerInformation = GameItemList.playerInformation;
        if(!event.getPlayer().hasPermission("cscore.tedit") || event.getItemInHand().isSimilar(playerInformation)) {
            event.setCancelled(true);
        }
    }
}
