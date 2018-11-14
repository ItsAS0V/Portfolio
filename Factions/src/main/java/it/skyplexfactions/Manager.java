package it.skyplexfactions;

import it.skyplexfactions.crafting.CraftingManager;
import it.skyplexfactions.crafting.ItemManager;
import it.skyplexfactions.utils.FactionUtils;
import it.skyplexfactions.utils.InventoryManager;

public class Manager {
    private InventoryManager inventoryManager;
    private FactionUtils factionUtils;
    private CraftingManager craftingManager;
    private ItemManager itemManager;

    public void register() {
        inventoryManager = new InventoryManager();
        factionUtils = new FactionUtils();
        craftingManager = new CraftingManager();
        itemManager = new ItemManager();
    }

    public void unregister() {
        inventoryManager = null;
        factionUtils = null;
        craftingManager = null;
        itemManager = null;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public FactionUtils getFactionUtils() {
        return factionUtils;
    }

    public CraftingManager getCraftingManager() { return craftingManager; }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
