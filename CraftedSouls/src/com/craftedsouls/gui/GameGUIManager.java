package com.craftedsouls.gui;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameGUIManager {

    private ArrayList<GameGUI> gameGUIList = new ArrayList<GameGUI>();

    public void addGUI(GameGUI GUI) {
        gameGUIList.add(GUI);
    }

    public void removeGUI(GameGUI GUI) {
        gameGUIList.remove(GUI);
    }

    public GameGUI getGUI(Inventory i) {
        for(GameGUI gui : gameGUIList) {
             if(gui.inventory.getTitle().equals(i.getTitle())) {
                 return gui;
             }
        }
        return null;
    }

    public GameGUI getGUI(int id) {
        for(GameGUI gui : gameGUIList) {
            if(gui.getID() == id) {
                return gui;
            }
        }
        return null;
    }

    public void openGUI(int id, Player player) {

        player.getInventory().addItem(player.getItemOnCursor());
        player.setItemOnCursor(new ItemStack(Material.AIR, 1));

        GameGUI gui = getGUI(id);
        if(gui == null) {
            player.sendMessage("ERROR: null GUI");
        } else {
            gui.onOpenInventory(player);
            player.openInventory(gui.inventory);
        }


    }

}
