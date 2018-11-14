package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.ItemData;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class EventPlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(item == null) { return; }
            if(item.isSimilar(GameItemList.characterSelector)) {
                CraftedSouls.getGUIManager().openGUI(2, player);
            }

            ItemStack playerInformation = GameItemList.playerInformation;
            if(item.isSimilar(playerInformation)) {
                CraftedSouls.getGUIManager().openGUI(1, player);
            }

            ItemStack mainhand = player.getInventory().getItemInMainHand();
            short ID = 0;
            try {
                ID = Short.valueOf(mainhand.getItemMeta().getLore().get(0));
            } catch (Exception e) {
                return;
            }

            ItemData itemData = ItemData.getInstance();

            if(itemData.itemExists(ID)) {
                if(player.isSneaking()) {
                    CraftedSouls.getCombatManager().toggleCombatmode(player);
                }
            }
        }
    }
}
