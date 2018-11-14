package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.ItemData;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.utils.Prefix;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class EventPlayerSwapHandsItems implements Listener {

    @EventHandler
    public void onPlayerSwapHandsItems(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        ItemStack swappedItem = event.getOffHandItem();

        ItemData itemData = ItemData.getInstance();

        if (player.getGameMode() != GameMode.CREATIVE) {
            if (swappedItem.getType() != Material.AIR) {
                int id = CraftedSouls.getCombatManager().getItemID(swappedItem);
                if (itemData.itemExists(id)) {
                    ItemBuilder builder = new ItemBuilder(id);
                    builder.build();
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======== §a" + builder.name + " §8========");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Min Damage: §a" + builder.damageData.min);
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Max Damage: §c" + builder.damageData.max);
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, " ");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7Level: §6" + builder.level);
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======== §a" + builder.name + " §8========");
                    event.setCancelled(true);
                } else {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "No index information found for this item");
                    event.setCancelled(true);
                }
            }
    }
    }
}
