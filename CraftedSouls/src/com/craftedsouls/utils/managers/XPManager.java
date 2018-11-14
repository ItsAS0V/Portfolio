package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.LevelData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.ActionBar;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by User on 08/04/2017.
 */
public class XPManager {

    public void setValues(Player player, int level, int XP, boolean levelup) {

        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        LevelData levelData = LevelData.getInstance();


        if(levelup) {
            if(level == 125) {
                userData.get(uuid).set("characters.4.locked", false);
                userData.save(uuid);
                userData.get(uuid).set("characters.5.locked", false);
                userData.save(uuid);
            }

            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8==============================================");
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§l§cYou have leveled up to level §a§l" + level + "!");
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8==============================================");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
            Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).trail(true).withColor(Color.RED).build();
            fwm.addEffect(effect);
            fwm.setPower(2);
            fw.setFireworkMeta(fwm);
            ActionBar bar = new ActionBar("§c§lLevel up! (lvl." + level + ")");
            bar.sendToPlayer(player);
        }

        int currentchar = userData.get(uuid).getInt("selectedchar");
        int levelXP = levelData.get().getInt(level + ".RXP");

        int health = CraftedSouls.getBarManager().getHealthValue(player);        //<
        int maxHealth = 150 + (level * 50);                                     //<

        if(health > maxHealth) {
            health = maxHealth;
        }

        CraftedSouls.getBarManager().updatePlayerHealth(player, health, maxHealth);

        userData.get(uuid).set("characters." + currentchar + ".level", level);
        userData.save(uuid);

        if(level == 125) {
            return;
        }

        userData.get(uuid).set("characters." + currentchar + ".xp", XP);
        userData.save(uuid);

        float percent = ((float) XP + 1) / (levelXP + 1);

        player.setExp(percent);
        player.setLevel(level);

    }

    public void displayXPInformation(Player player, int newXP, int userXP, int levelXP) {
        ActionBar bar = new ActionBar("§d§l(+" + newXP + " xp) §8- §c§l(" + userXP + "/" + levelXP + ")");
        bar.sendToPlayer(player);
    }

}
