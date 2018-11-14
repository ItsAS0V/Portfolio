package com.craftedsouls.gui;

import com.craftedsouls.CraftedSouls;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GameGUI {

    public Inventory inventory;

    int size = 9;
    String title = "Untitled inventory";

    protected GameGUI() {
        setDefaults("Untitled", 9);
    }

    public void setDefaults(String title, int size) {
        this.title = title;
        this.size = size;
        inventory = Bukkit.createInventory(null, size, title);
    }

    public void onPlayerClickBlock(Player player, ItemStack is) {
    }

    public void onOpenInventory(Player player) {
        inventory = Bukkit.createInventory(player, size, title);
    }

    public void onPlayerCloseInventory(Player player) {}

    public boolean onInventoryClick(InventoryClickEvent event) { return true; }

    protected Inventory getInventory() {
        return inventory;
    }

    protected void setInventoryBlock(ItemStack is, int slot) {
        inventory.setItem(slot, is);
    }

   private void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getID() {
        return -1;
    }



}

