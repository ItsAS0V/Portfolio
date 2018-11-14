package it.skyplexfactions.listeners;

import it.skyplexfactions.Factions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class EventPlayerBuild implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!Factions.getManager().getFactionUtils().checkLandBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!Factions.getManager().getFactionUtils().checkLandBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
