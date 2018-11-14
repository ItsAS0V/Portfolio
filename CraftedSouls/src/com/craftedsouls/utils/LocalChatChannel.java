package com.craftedsouls.utils;

import com.craftedsouls.CraftedSouls;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocalChatChannel {

    public static void sendMessage(BaseComponent component, Player player) {
        boolean anyone = false;
        for(Entity e : player.getNearbyEntities(30, 30, 30)) {
            if (e instanceof Player) {
                ((Player) e).spigot().sendMessage(component);
                anyone = true;
            }
        }
        if(!anyone) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "No one could hear you");
        }
        player.spigot().sendMessage(component);
    }
}
