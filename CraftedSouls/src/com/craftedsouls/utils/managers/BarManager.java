package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class BarManager {

    HashMap<UUID, BossBar[]> barStorage = new HashMap<>();

    public HashMap<UUID, BossBar[]> getBarStorage() {
        return barStorage;
    }

    public String getHealth(int health, int maxHealth) {
        return "§aHealth §8(§2"+ health + "§7/" + "§2" + maxHealth + "§8)";
    }

    public String getSpirit(int spirit, int maxSpirit) {
        return "§dSpirit §8(§5"+ spirit + "§7/" + "§5" + maxSpirit + "§8)";
    }

    public int getHealthValue(Player player) {
        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        int currentChar = data.getCurrentChar(uuid);
        return data.get(uuid).getInt("characters." + currentChar + ".stats.health");
    }

    public int getSpiritValue(Player player) {
        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        int currentChar = data.getCurrentChar(uuid);
        return data.get(uuid).getInt("characters." + currentChar + ".stats.spirit");
    }

    public int getMaxHealthValue(Player player) {
        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        int currentChar = data.getCurrentChar(uuid);
        return data.get(uuid).getInt("characters." + currentChar + ".stats.maxhealth");
    }

    public int getMaxSpiritValue(Player player) {
        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        int currentChar = data.getCurrentChar(uuid);
        return data.get(uuid).getInt("characters." + currentChar + ".stats.maxspirit");
    }

    public void damagePlayer(Player player, int damage) {
        int maxHealth = CraftedSouls.getBarManager().getMaxHealthValue(player);
        int health = CraftedSouls.getBarManager().getHealthValue(player);
        int newHealth = 0;

        newHealth = health - damage;

        if(newHealth <= 0) {
            newHealth = 0;
        }

        updatePlayerHealth(player, newHealth, maxHealth);
    }

    public void healPlayer(Player player, int healing) {
        int maxHealth = getMaxHealthValue(player);
        int health = getHealthValue(player);
        int newHealth = health + healing;
        updatePlayerHealth(player, newHealth, maxHealth);
    }

    public void addSpirit(Player player, int spirit) {
        int currentSpirit = getSpiritValue(player);
        int maxSpirit = getMaxSpiritValue(player);
        int newSpirit = currentSpirit + spirit;
        if(newSpirit > maxSpirit) {
            newSpirit = maxSpirit;
        }
        updatePlayerSpirit(player, newSpirit, maxSpirit);

    }

    public void removeSpirit(Player player, int spirit) {
        int currentSpirit = getSpiritValue(player);
        int maxSpirit = getMaxSpiritValue(player);
        int newSpirit = currentSpirit - spirit;
        if(newSpirit < 0) {
            newSpirit = 0;
        }
        updatePlayerSpirit(player, newSpirit, maxSpirit);
    }

    public void setupPlayer(Player player, int health, int maxHealth, int spirit, int maxSpirit) {
        BossBar healthBar = Bukkit.createBossBar(getHealth(health, maxHealth), BarColor.GREEN, BarStyle.SEGMENTED_20, BarFlag.PLAY_BOSS_MUSIC);
        BossBar spiritBar = Bukkit.createBossBar(getSpirit(spirit, maxSpirit), BarColor.PURPLE, BarStyle.SEGMENTED_20, BarFlag.PLAY_BOSS_MUSIC);
        healthBar.addPlayer(player);
        spiritBar.addPlayer(player);
        healthBar.setVisible(true);
        spiritBar.setVisible(true);

        BossBar[] bossBars = new BossBar[2];
        bossBars[0] = healthBar;
        bossBars[1] = spiritBar;

        barStorage.put(player.getUniqueId(), bossBars);

        updatePlayerHealth(player, health, maxHealth);
        updatePlayerSpirit(player, spirit, maxSpirit);
    }

    public void updatePlayerHealth(Player player, int health, int maxHealth) {

        if(health > maxHealth) {
            health = maxHealth;
        }

        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        BossBar[] playerBars = barStorage.get(player.getUniqueId());
        playerBars[0].setTitle(getHealth(health, maxHealth));

        double percentage = ((double)health/maxHealth);

        playerBars[0].setProgress(percentage);

        int percentageHundred = (int)Math.round(percentage * 100);

        if(percentageHundred > 60) {
            playerBars[0].setColor(BarColor.GREEN);
        }
        if(percentageHundred > 20 & percentageHundred <= 60) {
            playerBars[0].setColor(BarColor.YELLOW);
        }
        if(percentageHundred < 20) {
            playerBars[0].setColor(BarColor.RED);
        }

        int currentChar = data.getCurrentChar(uuid);
        data.get(uuid).set("characters." + currentChar + ".stats.health", health);
        data.save(uuid);
        data.get(uuid).set("characters." + currentChar + ".stats.maxhealth", maxHealth);
        data.save(uuid);
    }

    public void updatePlayerSpirit(Player player, int spirit, int maxSpirit) {
        UserData data = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        BossBar[] playerBars = barStorage.get(player.getUniqueId());
        playerBars[1].setTitle(getSpirit(spirit, maxSpirit));

        double percentage = ((double)spirit/maxSpirit);

        playerBars[1].setProgress(percentage);

        int currentChar = data.getCurrentChar(uuid);

        data.get(uuid).set("characters." + currentChar + ".stats.spirit", spirit);
        data.save(uuid);
        data.get(uuid).set("characters." + currentChar + ".stats.maxspirit",  maxSpirit);
        data.save(uuid);
    }

    public void removePlayer(UUID uuid) {
        barStorage.get(uuid)[0].removeAll();
        barStorage.get(uuid)[1].removeAll();
        barStorage.remove(uuid);
    }
}
