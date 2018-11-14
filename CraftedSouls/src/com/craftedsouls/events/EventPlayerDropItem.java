package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.data.builders.AbilityBuilder;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class EventPlayerDropItem implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        ItemStack item = event.getItemDrop().getItemStack();
        UserData userData = UserData.getInstance();
        int econTotalAmount = userData.get(uuid).getInt("economy.total");
        int econBankAmount = userData.get(uuid).getInt("economy.bank");

        if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            event.setCancelled(true);
            return;
        }

        if(player.getGameMode() != GameMode.CREATIVE) {
            ItemStack playerInformation = GameItemList.playerInformation;
            ItemStack economy = GameItemList.createEconomy(player);
            if (item.isSimilar(playerInformation) || item.isSimilar(GameItemList.economy)) {
                event.setCancelled(true);
            }
        }

        if (CraftedSouls.getCombatManager().isPlayerCombatmode(player)) {

            ItemStack droppedItem = event.getItemDrop().getItemStack();

            if(player.getInventory().getHeldItemSlot() != 0 && !droppedItem.isSimilar(GameItemList.empty)) {
                short ID = 0;
                try {
                    ID = Short.valueOf(ChatColor.stripColor(droppedItem.getItemMeta().getLore().get(droppedItem.getItemMeta().getLore().size() - 1)));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;

                }
                AbilityBuilder ability = new AbilityBuilder(ID);
                ability.build();

                CraftedSouls.getCombatManager().useAbility(ability, player);
            }

            event.setCancelled(true);
        }

        if(CraftedSouls.dead.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
