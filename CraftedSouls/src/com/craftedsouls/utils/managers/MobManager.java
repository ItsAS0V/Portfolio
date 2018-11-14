package com.craftedsouls.utils.managers;

import org.bukkit.entity.Entity;

public class MobManager {
    private static MobManager instance = new MobManager();

    public static MobManager getInstance(){ return instance; }

    public Entity spawnMob(Entity entity, String name) {
        entity.setCustomNameVisible(true);
        entity.setCustomName(name);
        return entity;
    }
}
