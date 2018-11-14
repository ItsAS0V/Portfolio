package com.craftedsouls.events;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventPlayerLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        BanEntry[] banList = Bukkit.getBanList(BanList.Type.NAME).getBanEntries().toArray(new BanEntry[0]);
        if(event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED)){
            for(BanEntry entry : banList) {
                if(entry.getTarget().equals(event.getPlayer().getName())) {
                    event.setKickMessage(entry.getReason());
                }
            }
        }
    }
}
