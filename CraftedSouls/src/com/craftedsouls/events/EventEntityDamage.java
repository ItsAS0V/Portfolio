package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.utils.Prefix;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EventEntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        Entity damaged = event.getEntity();




        /*
                ===========  AI MANAGEMENT   =============
         */

        if(damaged instanceof Zombie) {
            if(event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                event.setCancelled(true);
            }
        }

        /*
                ===========   BAR MANAGEMENT   =============
         */

        if(damaged instanceof Player) {
            Player player = (Player)damaged;

            if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
                event.setCancelled(true);
                return;
            }

            if(CraftedSouls.dead.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            CraftedSouls.getBarManager().damagePlayer(player, (int)event.getDamage());
            event.setDamage(0);
        }

        /*
                ===========  DYNAMIC DEATH MANAGER   =============
         */


        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (CraftedSouls.dead.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            CraftedSouls.getDeathManager().checkDeath(player);
        }

        /*
                ===========   BAR MANAGEMENT   =============
         */

        if(damaged instanceof Player) {
            Player player = (Player)damaged;

            if(CraftedSouls.dead.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            double damage = event.getDamage() * 10; //Take into account:buffs/debuffs/wearing

            int maxHealth = CraftedSouls.getBarManager().getMaxHealthValue(player);
            int health = CraftedSouls.getBarManager().getHealthValue(player);
            int newHealth = 0;

            newHealth = health - (int)damage;

            if(newHealth <= 0) {
                newHealth = 0;
            }

            CraftedSouls.getBarManager().updatePlayerHealth(player, newHealth, maxHealth);

            event.setDamage(0);
        }
    }
    
}