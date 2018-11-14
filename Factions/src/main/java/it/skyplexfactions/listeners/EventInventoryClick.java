package it.skyplexfactions.listeners;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import it.skyplexfactions.Factions;
import it.skyplexfactions.database.FactionsFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class EventInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        UsersFiles usersFiles = UsersFiles.get(player);
        FactionsFiles factionsFiles = FactionsFiles.get(usersFiles.getFaction());

        //TODO: Inventory checks
    }
}
