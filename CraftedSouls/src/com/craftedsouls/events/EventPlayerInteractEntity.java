package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EventPlayerInteractEntity implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getRightClicked();
            if(villager.getName().contains("§aBanker")) {
                event.setCancelled(true);
                CraftedSouls.getGUIManager().openGUI(5, player);
            }
        }
    }
}
