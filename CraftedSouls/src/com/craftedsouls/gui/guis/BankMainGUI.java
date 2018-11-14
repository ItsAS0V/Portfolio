package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BankMainGUI extends GameGUI {

    public BankMainGUI() { super(); }

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§6Bank", 9 * 3);
    }

    @Override
    public int getID() {
        return 5;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);

        for(int i = 0; i < 9; i++) {
            inventory.setItem(i, GameItemList.empty);
        }
        inventory.setItem(9, GameItemList.empty);
        inventory.setItem(10, GameItemList.empty);
        inventory.setItem(11, GameItemList.withdraw);
        inventory.setItem(12, GameItemList.empty);
        inventory.setItem(13, new ItemStack(GameItemList.createEconomy(player)));
        inventory.setItem(14, GameItemList.empty);
        inventory.setItem(15, GameItemList.deposit);
        inventory.setItem(16, GameItemList.empty);
        inventory.setItem(17, GameItemList.empty);

        for(int i = 18; i < 27; i++) {
            inventory.setItem(i, GameItemList.empty);
        }
    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(5, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        super.onPlayerClickBlock(player, is);

        if (is.getItemMeta().getDisplayName().equals(GameItemList.withdraw.getItemMeta().getDisplayName())) {
            CraftedSouls.getGUIManager().openGUI(6, player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);
        } else if (is.getItemMeta().getDisplayName().equals(GameItemList.deposit.getItemMeta().getDisplayName())) {
            CraftedSouls.getGUIManager().openGUI(7, player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);
        }
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return super.onInventoryClick(event);
    }
}
