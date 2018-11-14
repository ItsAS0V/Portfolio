package com.craftedsouls.utils.tasks;

import com.craftedsouls.CraftedSouls;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RegenerationTask extends BukkitRunnable {

    final int healthRegenSpeed = 5;
    final int spiritRegenSpeed = 3;

    private final JavaPlugin plugin;

    public RegenerationTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        for(UUID uuid : CraftedSouls.getBarManager().getBarStorage().keySet()){
            Player player = Bukkit.getPlayer(uuid);

            int health = CraftedSouls.getBarManager().getHealthValue(player);
            int maxHealth = CraftedSouls.getBarManager().getMaxHealthValue(player);

            int spirit = CraftedSouls.getBarManager().getSpiritValue(player);
            int maxSpirit = CraftedSouls.getBarManager().getMaxSpiritValue(player);


            //Health Regen Code
            if(health < maxHealth) {
                health += healthRegenSpeed;
            }

            //Spirit Regen Code
            if(spirit < maxSpirit) {
                spirit += spiritRegenSpeed;
            }

            //Boundary Checks
            if(health > maxHealth) {
                health = maxHealth;
            }
            if(spirit > maxSpirit) {
                spirit = maxSpirit;
            }

            CraftedSouls.getDeathManager().checkDeath(player);
            CraftedSouls.getBarManager().updatePlayerHealth(player, health, maxHealth);
            CraftedSouls.getBarManager().updatePlayerSpirit(player, spirit, maxSpirit);

        }
     }
}