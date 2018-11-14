package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;

public class DeathManager {

    public void checkDeath(Player player) {
        if (CraftedSouls.getBarManager().getHealthValue(player) <= 0) {

            //Boss Bar
            int maxHealth = CraftedSouls.getBarManager().getMaxHealthValue(player);

            CraftedSouls.getBarManager().updatePlayerHealth(player, maxHealth, maxHealth);

            player.setAllowFlight(true);
            player.setFlying(true);

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.setFireTicks(0);
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§aA shiver sends down your spine as the world around you darkens");
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 220, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 220, 1));

            player.setGameMode(GameMode.ADVENTURE);

            player.sendTitle("§cYou have died", "§7§oMoving into a new body", 50, 100, 50);

            CraftedSouls.dead.put(player.getUniqueId(), 1);

            player.setVelocity(new Vector(0, 0.35, 0));

            ZombieVillager zombie = (ZombieVillager) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE_VILLAGER);
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 220, 2));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 220, 2));
            zombie.setCustomNameVisible(true);
            zombie.setCustomName("§7§oGhost of §c§o" + player.getDisplayName());
            zombie.setCanPickupItems(false);
            zombie.setSilent(true);
            zombie.setCollidable(false);
            zombie.setRemoveWhenFarAway(true);
            zombie.setFireTicks(0);
        }

    }

    public void deathLoop() {
        BukkitScheduler deathScheduler = CraftedSouls.plugin.getServer().getScheduler();

        deathScheduler.scheduleSyncRepeatingTask(CraftedSouls.plugin, new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < CraftedSouls.dead.entrySet().size(); i++) {
                    Set<UUID> uuids = CraftedSouls.dead.keySet();
                    UUID[] uuidList = uuids.toArray(new UUID[255]);

                    UUID uuid = uuidList[i];
                    int value = CraftedSouls.dead.get(uuid);

                    Player newPlayer = Bukkit.getPlayer(uuid);
                    if (newPlayer == null) {
                        return;
                    }

                    int x = newPlayer.getLocation().getBlockX();
                    int y = newPlayer.getLocation().getBlockY();
                    int z = newPlayer.getLocation().getBlockZ();

                    if(value < 10) {
                        value = value + 1;
                        CraftedSouls.dead.remove(uuid);
                        CraftedSouls.dead.put(uuid, value);
                    } else {
                        CraftedSouls.dead.remove(uuid);

                        //Particle Effects
                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y, z, 3, 0, 3, 5, 10);

                        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
                            ((CraftPlayer) online).getHandle().a(x, y, z);
                        }

                        newPlayer.teleport(newPlayer.getWorld().getSpawnLocation());
                        newPlayer.setGameMode(GameMode.SURVIVAL);

                        CraftedSouls.dead.remove(newPlayer.getUniqueId());

                        newPlayer.setAllowFlight(false);
                        newPlayer.setFlying(false);

                        newPlayer.getActivePotionEffects().removeAll(newPlayer.getActivePotionEffects());

                        CraftedSouls.getChatManager().sendNoPrefixChat(newPlayer, "§aYou have awoken into a new body");
                    }

                }
            }
        }, 0L, 20L);
    }
}




