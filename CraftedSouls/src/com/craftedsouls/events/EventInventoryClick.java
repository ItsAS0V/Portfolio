package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.gui.GameGUIManager;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack blockClicked = event.getCurrentItem();

        if(blockClicked == null) {
            return;
        }

        if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            if(event.getClickedInventory() == player.getInventory()) {
                event.setCancelled(true);
                return;
            }
        }

        if(CraftedSouls.getCombatManager().isPlayerCombatmode(player)) {
            if(event.getRawSlot() <= 44 && event.getRawSlot() >= 36) {
                event.setCancelled(true);
                return;
            }
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            if (blockClicked.isSimilar(GameItemList.playerInformation)) {
                event.setCancelled(true);
            }
            if (blockClicked.isSimilar(GameItemList.createEconomy(player))) {
                event.setCancelled(true);
            }
        }

        if (CraftedSouls.getGUIManager().getGUI(event.getClickedInventory()) != null) {
            if(event.getAction() != InventoryAction.DROP_ONE_SLOT) {
                CraftedSouls.getGUIManager().getGUI(event.getClickedInventory()).onPlayerClickBlock(player, blockClicked);
                event.setCancelled(CraftedSouls.getGUIManager().getGUI(inventory).onInventoryClick(event));
            } else {
                event.setCancelled(true);
            }
        }

        //Inventory Open
        if (CraftedSouls.getGUIManager().getGUI(inventory) != null) {
            event.setCancelled(CraftedSouls.getGUIManager().getGUI(inventory).onInventoryClick(event));

        }

    }
}

