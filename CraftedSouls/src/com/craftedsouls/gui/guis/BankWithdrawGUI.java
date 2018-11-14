package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.managers.eco.EcoType;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BankWithdrawGUI extends GameGUI {

    public BankWithdrawGUI() {
        super();
    }

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§6Bank §8(§aWithdraw§8)", 9 * 6);
    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);

        inventory.setItem(0, GameItemList.empty);
        inventory.setItem(1, GameItemList.empty);
        inventory.setItem(2, GameItemList.empty);
        inventory.setItem(3, GameItemList.empty);
        inventory.setItem(4, new ItemStack(GameItemList.createEconomy(player)));
        inventory.setItem(5, GameItemList.empty);
        inventory.setItem(6, GameItemList.back);
        inventory.setItem(7, GameItemList.empty);
        inventory.setItem(8, GameItemList.empty);
        for(int i = 9; i < 18; i++) {
            inventory.setItem(i, GameItemList.empty);
        }
        inventory.setItem(18, GameItemList.empty);

        //Copper
        inventory.setItem(19, GameItemList.setCopperAmount(1));
        inventory.setItem(20, GameItemList.setCopperAmount(2));
        inventory.setItem(21, GameItemList.setCopperAmount(4));
        inventory.setItem(22, GameItemList.setCopperAmount(8));
        inventory.setItem(23, GameItemList.setCopperAmount(16));
        inventory.setItem(24, GameItemList.setCopperAmount(32));
        inventory.setItem(25, GameItemList.setCopperAmount(64));

        inventory.setItem(26, GameItemList.empty);
        inventory.setItem(27, GameItemList.empty);

        //Silver
        inventory.setItem(28, GameItemList.setSilverAmount(1));
        inventory.setItem(29, GameItemList.setSilverAmount(2));
        inventory.setItem(30, GameItemList.setSilverAmount(4));
        inventory.setItem(31, GameItemList.setSilverAmount(8));
        inventory.setItem(32, GameItemList.setSilverAmount(16));
        inventory.setItem(33, GameItemList.setSilverAmount(32));
        inventory.setItem(34, GameItemList.setSilverAmount(64));

        inventory.setItem(35, GameItemList.empty);
        inventory.setItem(36, GameItemList.empty);

        //Gold
        inventory.setItem(37, GameItemList.setGoldAmount(1));
        inventory.setItem(38, GameItemList.setGoldAmount(2));
        inventory.setItem(39, GameItemList.setGoldAmount(4));
        inventory.setItem(40, GameItemList.setGoldAmount(8));
        inventory.setItem(41, GameItemList.setGoldAmount(16));
        inventory.setItem(42, GameItemList.setGoldAmount(32));
        inventory.setItem(43, GameItemList.setGoldAmount(64));

        inventory.setItem(44, GameItemList.empty);
        inventory.setItem(45, GameItemList.empty);
        for(int i = 46; i < 54; i++) {
            inventory.setItem(i, GameItemList.empty);
        }
    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(6, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        super.onPlayerClickBlock(player, is);

        String uuid = player.getUniqueId().toString();

        UserData userData = UserData.getInstance();
        FileConfiguration userFile = userData.get(uuid);

        int charslot = userData.getCurrentChar(uuid);

        int total = -1;

        if(is.isSimilar(GameItemList.copper_eco)) {
            total = is.getAmount();
        }
        if(is.isSimilar(GameItemList.silver_eco)) {
            total = is.getAmount() * 100;
        }
        if(is.isSimilar(GameItemList.gold_eco)) {
            total = is.getAmount() * 10000;
        }

        if(userFile.getInt("characters." + charslot + ".balance") < total) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You don't have enough money in the bank!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10, 2);
            return;
        }

        if (is.getItemMeta().getDisplayName().equals(GameItemList.back.getItemMeta().getDisplayName())) {
            CraftedSouls.getGUIManager().openGUI(5, player);
        }

        if(player.getInventory().firstEmpty() == -1) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Your inventory is full!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10, 2);
            return;
        }

        if(is.isSimilar(GameItemList.setCopperAmount(is.getAmount())) ||
                is.isSimilar(GameItemList.setSilverAmount(is.getAmount())) ||
                        is.isSimilar(GameItemList.setGoldAmount(is.getAmount()))) {

            player.getInventory().addItem(is.clone());

            int bankTotal = userFile.getInt("characters." + charslot + ".balance");

            int finalTotal = (bankTotal - total);

            userData.get(uuid).set("characters." + charslot + ".balance", finalTotal);
            userData.save(uuid);

            player.getOpenInventory().setItem(4, GameItemList.createEconomy(player));

            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 10, 2);
        }
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return super.onInventoryClick(event);
    }
}
