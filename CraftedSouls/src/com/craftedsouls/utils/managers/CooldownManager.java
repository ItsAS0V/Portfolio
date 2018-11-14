package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.gui.guis.CharacterGUI;
import com.craftedsouls.utils.ActionBar;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;


class CooldownAbilityInformation {
    int[] cooldowns = new int[8];
}

class CooldownCharacterInformation {
    int slot;
    boolean filled;
    int cooldown;
}

public class CooldownManager extends BukkitRunnable {

    public HashMap<UUID, CooldownAbilityInformation> cooldownAbilities = new HashMap<>();
    public HashMap<UUID, CooldownCharacterInformation> cooldownCharacterChange = new HashMap<>();

    private final JavaPlugin plugin;

    public CooldownManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addCharacterChangeCooldown(UUID uuid, int slot, boolean filled) {
        CooldownCharacterInformation cci = new CooldownCharacterInformation();
        cci.cooldown = 5;
        cci.slot = slot;
        cci.filled = filled;
        cooldownCharacterChange.put(uuid, cci);
    }

    public void removeCharacterChangeCooldown(UUID uuid) {
        cooldownCharacterChange.remove(uuid);
    }

    public int getCharacterCooldown(UUID uuid) {
        return cooldownCharacterChange.get(uuid).cooldown;
    }

    public void addAbilityCooldowns(UUID uuid, int[] cooldowns) {
        CooldownAbilityInformation information = new CooldownAbilityInformation();
        information.cooldowns = cooldowns;
        cooldownAbilities.put(uuid, information);
    }

    public int getAbilityCooldown(UUID uuid, int slot) {
        return cooldownAbilities.get(uuid).cooldowns[slot-1];
    }

    public void setAbilityCooldown(UUID uuid, int slot, int value) {
        cooldownAbilities.get(uuid).cooldowns[slot-1] = value;
    }

    public void run() {

        for(UUID uuid : cooldownCharacterChange.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            int slot = cooldownCharacterChange.get(uuid).slot;
            int cooldown = cooldownCharacterChange.get(uuid).cooldown;
            boolean filled = cooldownCharacterChange.get(uuid).filled;
            cooldown -= 1;
            if((cooldown + 1) > 0) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Don't move, you will change character in §a" + cooldown + " §7seconds!");
                cooldownCharacterChange.get(uuid).cooldown = cooldown;
            } else {
                CharacterGUI.handleCharacterChange(player, slot, filled);
                removeCharacterChangeCooldown(uuid);
            }
        }

        for(UUID uuid : cooldownAbilities.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            int[] cooldowns = cooldownAbilities.get(uuid).cooldowns;
            int slot = 0;
            for(int cooldown : cooldowns) {
                  slot += 1;
                  cooldown -= 1;
                  if((cooldown + 1) > 0) {
                      if(CraftedSouls.getCombatManager().isPlayerCombatmode(player)) {
                          CraftedSouls.getCombatManager().setAbilityCooldown(player, slot, cooldown);
                      }
                      cooldownAbilities.get(uuid).cooldowns[slot-1] = cooldown;
                  }
            }

        }

    }
}
