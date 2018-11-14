package com.craftedsouls.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventPlayerTeleport implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event)
    {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
        {
            event.setCancelled(true);
        }
    }
}
