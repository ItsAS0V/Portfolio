package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;

import com.craftedsouls.data.LevelData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.utils.Prefix;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EventEntityDamageByEntity implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        if (damager instanceof Player) {

            Player player = (Player) damager;

            String uuid = player.getUniqueId().toString();
            UserData userData = UserData.getInstance();
            int currentChar = userData.getCurrentChar(uuid);

            ItemStack mainhand = player.getInventory().getItemInMainHand();
            short ID = 0;
            try {
                ID = Short.valueOf(mainhand.getItemMeta().getLore().get(0));
            } catch (Exception e) {
                return;
            }

            ItemBuilder item = new ItemBuilder(ID);
            item.build();

            int min = item.damageData.min;
            int max = item.damageData.max;
            int itemLevel = item.level;

            int playerLevel = userData.get(uuid).getInt("characters." + currentChar + ".level");

            int damage = ThreadLocalRandom.current().nextInt(min, max + 1);

            if (playerLevel < itemLevel) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§7You must be at least level §a" + itemLevel + " §7to use this item!");
                event.setDamage(0);
                event.setCancelled(true);
                return;
            }

            if (damaged instanceof Player) {
                event.setDamage(0);
                CraftedSouls.getBarManager().damagePlayer((Player)damaged, damage);
                CraftedSouls.getDeathManager().checkDeath((Player)damaged);
            } else {
                event.setDamage(damage);
            }

            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§7You damaged the entity for §a" + damage);
        }

    }
}



