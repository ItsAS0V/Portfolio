package it.skyplexfactions.listeners;

import it.skyplexfactions.Factions;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Factions.getManager().getFactionUtils().refreshScoreboard(event.getPlayer());
    }
}
