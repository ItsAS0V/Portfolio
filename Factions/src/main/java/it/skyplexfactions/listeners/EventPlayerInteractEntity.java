package it.skyplexfactions.listeners;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import it.skyplexfactions.Factions;
import it.skyplexfactions.database.FactionsFiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventPlayerInteractEntity implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        Entity entity = event.getEntity();

        UsersFiles usersFiles = UsersFiles.get(player);

        if(entity instanceof EnderCrystal) {
            event.setCancelled(true);

            if(usersFiles.getFaction().equalsIgnoreCase("Wilderness")) {
                Core.getManager().getMessage().sendMessage(player, "&cYou must be in a faction to use the &5Nexus&c!");
                return;
            }

            Core.getManager().getMessage().sendMessage(player, "&7Opening &5Nexus&7...");
            player.openInventory(Factions.getManager().getInventoryManager().nexusInventory(usersFiles.getFaction()));
        }
    }
}
