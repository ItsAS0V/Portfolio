package com.craftedsouls.utils.managers.eco;

import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.inventory.ItemStack;

public class EconomyManager {


    public static int calculateItemWealth(ItemStack is) {
        int wealth = 0;

        if(is.isSimilar(GameItemList.copper_eco)) {
            wealth = is.getAmount();
        }
        if(is.isSimilar(GameItemList.silver_eco)) {
            wealth = is.getAmount() * 100;
        }
        if(is.isSimilar(GameItemList.gold_eco)) {
            wealth = is.getAmount() * 10000;
        }

        return wealth;
    }
}
