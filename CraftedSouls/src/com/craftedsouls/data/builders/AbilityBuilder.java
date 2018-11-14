package com.craftedsouls.data.builders;

import com.craftedsouls.data.AbilityData;
import com.craftedsouls.data.types.AbilityType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AbilityBuilder {

    public ItemStack createdAbility;
    public String name;

    public int cooldown;
    public int id;
    public AbilityType abilityType;
    public int power;
    public int range;
    public Sound sound;
    public int spirit;

    /*public AbilityBuilder(String name, int id, Material material, int cooldown, AbilityType abilityType, int power) {
        this.material = material;
        this.name = name;
        this.cooldown = cooldown;
        this.abilityType = abilityType;
        this.id = id;
        this.power = power;
    }*/



    public AbilityBuilder(int id) {
        AbilityData abilityData = AbilityData.getInstance();
        this.id = id;
        name = abilityData.get(id).getString("name");
        cooldown = abilityData.get(id).getInt("cooldown");
        abilityType = AbilityType.valueOf(abilityData.get(id).getString("ability-type"));
        power = abilityData.get(id).getInt("options.power");
        range = abilityData.get(id).getInt("options.range");
        sound = Sound.valueOf(abilityData.get(id).getString("options.sound"));
        spirit = abilityData.get(id).getInt("cost.spirit");
    }

    public void build() {
        createdAbility = new ItemStack(Material.BOOK);
        ItemMeta abilityMeta = createdAbility.getItemMeta();
        abilityMeta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add("§7Cooldown: §c" + cooldown);
        lore.add("§7Ability-type: §a" + abilityType);
        lore.add("§7Power: §a" + power);
        lore.add("§0" + id);
        abilityMeta.setLore(lore);
        createdAbility.setItemMeta(abilityMeta);
    }

    /*public void writeData(int id) {
        AbilityData itemData = AbilityData.getInstance();
        itemData.get(id).set("name", name.replace('_', ' '));
        itemData.save(id);
        itemData.get(id).set("material", material.name());
        itemData.save(id);
        itemData.get(id).set("cooldown", cooldown);
        itemData.save(id);
        itemData.get(id).set("ability-type", abilityType.name());
        itemData.save(id);
        itemData.get(id).set("power", power);
        itemData.save(id);
    }*/
}
