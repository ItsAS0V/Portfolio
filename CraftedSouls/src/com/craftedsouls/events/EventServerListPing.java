package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class EventServerListPing implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        //§
        Settings settings = Settings.getInstance();
        settings.reloadData();

        event.setMotd("§6CraftedSouls §e" + CraftedSouls.plugin.getDescription().getVersion() + " §8► §a" + ChatColor.translateAlternateColorCodes('&', settings.getData().getString("state")) + "\n" + "§6News: §7" + ChatColor.translateAlternateColorCodes('&', settings.getData().getString("news")));
    }
}
