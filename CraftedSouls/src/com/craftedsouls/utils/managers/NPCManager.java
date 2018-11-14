package com.craftedsouls.utils.managers;

import com.craftedsouls.data.NPCData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NPCManager {
    private static NPCManager instance = new NPCManager();

    public static NPCManager getInstance() {
        return instance;
    }

    public void spawnStaticNPC(String name, Location location) {
        NPCData npcData = NPCData.getInstance();
        Villager villager = (Villager) Bukkit.getWorld("world").spawn(location, (Class)Villager.class);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.FARMER);
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 20));


        npcData.setup(name);
    }

    public void spawnNPC(String name, Location location) {
        NPCData npcData = NPCData.getInstance();
        Villager villager = (Villager) Bukkit.getWorld("world").spawn(location, (Class)Villager.class);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.FARMER);

        npcData.setup(name);
    }

    public void spawnBanker(Location location) {

        NPCData npcData = NPCData.getInstance();

        int id = 0;
        while(npcData.npcFileExists("banker-" + id)) { id++; }


        Villager villager = (Villager) Bukkit.getWorld("world").spawn(location, (Class)Villager.class);
        villager.setCustomName("§aBanker §8(§7#" + id + "§8)");
        villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.PRIEST);
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 20));
        //Delete all data?
        npcData.setup("banker-" + id);
    }
}
