package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.gui.GameGUIManager;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.managers.eco.EconomyManager;
import com.craftedsouls.utils.managers.eco.EconomyValueTree;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BankDepositGUI extends GameGUI{

    public BankDepositGUI() {
        super();
    }

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§6Bank §8(§cDeposit§8)", 9 * 6);
    }

    @Override
    public int getID() {
        return 7;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);

        inventory.setItem(0, GameItemList.empty);
        inventory.setItem(1, GameItemList.empty);
        inventory.setItem(2, GameItemList.confirm);
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
        inventory.setItem(27, GameItemList.empty);
        inventory.setItem(36, GameItemList.empty);


        inventory.setItem(44, GameItemList.empty);
        inventory.setItem(35, GameItemList.empty);
        inventory.setItem(26, GameItemList.empty);

        inventory.setItem(45, GameItemList.empty);

        for(int i = 46; i < 54; i++) {
            inventory.setItem(i, GameItemList.empty);
        }
    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(7, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        super.onPlayerClickBlock(player, is);

        if(is.hasItemMeta()) {
            if (is.getItemMeta().getDisplayName().equals(GameItemList.back.getItemMeta().getDisplayName())) {
                CraftedSouls.getGUIManager().openGUI(5, player);
            }

            if (is.getItemMeta().getDisplayName().equals(GameItemList.confirm.getItemMeta().getDisplayName())) {
                //INIT
                int totalWealth = 0;

                String uuid = player.getUniqueId().toString();

                UserData userData = UserData.getInstance();
                FileConfiguration userFile = userData.get(uuid);

                //CALCULATE
                for (int i = 19; i <= 25; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        totalWealth += EconomyManager.calculateItemWealth(player.getOpenInventory().getItem(i));
                    }
                }
                for (int i = 28; i <= 34; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        totalWealth += EconomyManager.calculateItemWealth(player.getOpenInventory().getItem(i));
                    }
                }
                for (int i = 37; i <= 43; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        totalWealth += EconomyManager.calculateItemWealth(player.getOpenInventory().getItem(i));
                    }
                }

                if(totalWealth < 1) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Place the currency you want to deposit into the menu before confirming!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10, 2);
                    return;
                }

                //PROCESS
                EconomyValueTree tree = new EconomyValueTree(totalWealth);
                tree.SimplifyValues();
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Deposited: §cCopper: §f" + tree.copper + ", §7Silver: §f" + tree.silver + ", §6Gold: §f" + tree.gold);
                int charslot = userData.getCurrentChar(uuid);
                int bankTotal = userFile.getInt("characters." + charslot + ".balance");
                userData.get(uuid).set("characters." + charslot + ".balance", bankTotal + totalWealth);
                userData.save(uuid);

                //DELETE
                for (int i = 19; i <= 25; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        player.getOpenInventory().setItem(i, new ItemStack(Material.AIR, 1));
                    }
                }
                for (int i = 28; i <= 34; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        player.getOpenInventory().setItem(i, new ItemStack(Material.AIR, 1));
                    }
                }
                for (int i = 37; i <= 43; i++) {
                    if (player.getOpenInventory().getItem(i) != null) {
                        player.getOpenInventory().setItem(i, new ItemStack(Material.AIR, 1));
                    }
                }

                player.getOpenInventory().setItem(4, GameItemList.createEconomy(player));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);

            }
        }

    }

    @Override
    public void onPlayerCloseInventory(Player player) {
        super.onPlayerCloseInventory(player);

        for (int i = 19; i < 25; i++) {
            if(player.getOpenInventory().getItem(i) != null) {
                player.getInventory().addItem(player.getOpenInventory().getItem(i));
            }
        }
        for (int i = 28; i < 34; i++) {
            if(player.getOpenInventory().getItem(i) != null) {
                player.getInventory().addItem(player.getOpenInventory().getItem(i));
            }
        }
        for (int i = 37; i < 43; i++) {
            if(player.getOpenInventory().getItem(i) != null) {
                player.getInventory().addItem(player.getOpenInventory().getItem(i));
            }
        }

    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        boolean block = true;

        if(event.getAction() == InventoryAction.HOTBAR_SWAP) {
            return true;
        }
        if(event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
            return true;
        }

        if(event.getCursor().isSimilar(GameItemList.copper_eco)|
                event.getCursor().isSimilar(GameItemList.silver_eco) ||
                event.getCursor().isSimilar(GameItemList.gold_eco) ||
                event.getCursor().getType() == Material.AIR) {
            if(event.getCurrentItem().isSimilar(GameItemList.copper_eco)|
                    event.getCurrentItem().isSimilar(GameItemList.silver_eco) ||
                    event.getCurrentItem().isSimilar(GameItemList.gold_eco) ||
                    event.getCurrentItem().getType() == Material.AIR) {
                block = false;

            }
        }

        return block;
    }
}

