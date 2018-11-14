package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.LevelData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class EventEntityDeath implements Listener {

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = (LivingEntity) event.getEntity();
        if(entity != null) {
            event.setDroppedExp(0);
            event.getDrops().clear();

            int drops = randInt(0, 100);

            ItemStack copper = GameItemList.copper_eco.clone();

            if(drops >= 40 && drops < 60) { //60% chance of dropping items
                copper.setAmount(1);
            }
            else if(drops > 60 && drops < 80) { //40% chance of dropping items x 2
                copper.setAmount(2);
            }
            else if(drops >= 80) { //20% chance of dropping items x 3
                copper.setAmount(3);
            }

            boolean hasCustomName = entity.getCustomName() != null;
            if(hasCustomName) {
                if (entity.getCustomName().startsWith("§7§oGhost of")) {
                    ItemStack will = new ItemStack(Material.PAPER);
                    ItemMeta willMeta = will.getItemMeta();
                    willMeta.setDisplayName("§7Will of " + entity.getCustomName().split(" ")[2]);
                    will.setItemMeta(willMeta);
                    event.getDrops().add(will);
                }
            }

            event.getDrops().add(copper);

            if(entity instanceof Creature || entity instanceof Player) {
                if (entity.getKiller() != null) {

                    Player player = entity.getKiller();
                    String uuid = player.getUniqueId().toString();
                    UserData userData = UserData.getInstance();
                    LevelData levelData = LevelData.getInstance();

                    int currentchar = userData.get(uuid).getInt("selectedchar");


                    int level = userData.get(uuid).getInt("characters." + currentchar + ".level");
                    int userXP = userData.get(uuid).getInt("characters." + currentchar + ".xp");
                    int levelXP = levelData.get().getInt(level + ".RXP");

                    //mobEXP = (userLevel + mobLevel / 2 + abs(userLevel - mobLevel)) * random(0.2, 0.5)

                    //temporary
                    int increasedXP = randInt(10, 30);

                    userData.get(uuid).set("characters." + currentchar + ".xp", userXP + increasedXP);
                    userData.save(uuid);

                    userXP = userXP + increasedXP;

                    boolean levelup = false;

                    while (userXP >= levelXP) {  //<

                        //Level up by one
                        level = level + 1;

                        //Level-CAP
                        if (level > 125) {
                            player.setExp(0.0f);
                            player.setLevel(125);
                            return;
                        }

                        levelup = true;

                        userXP = userXP - levelXP;
                    }
                    CraftedSouls.getXPManager().displayXPInformation(player, increasedXP, userXP, levelXP);
                    CraftedSouls.getXPManager().setValues(player, level, userXP, levelup);

                }
            }
        }
    }


}
