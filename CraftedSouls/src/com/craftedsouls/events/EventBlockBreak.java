package com.craftedsouls.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class EventBlockBreak implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if(!event.getPlayer().hasPermission("cscore.tedit")) {
            event.setCancelled(true);
        }
    }
}
