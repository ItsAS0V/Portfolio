package com.craftedsouls.data.builders;

import com.craftedsouls.data.DamageData;
import com.craftedsouls.data.types.DamageType;
import com.craftedsouls.data.ItemData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public ItemStack createdItem;
    public Material material;
    public String name;

    public int id;
    public int level;
    public DamageData damageData;


    public ItemBuilder(Material material, String name, int id, DamageData damageData, int level) {
        this.material = material;
        this.name = name;
        this.id = id;
        this.level = level;
        this.damageData = damageData;
    }

    public ItemBuilder(int id) {
        ItemData itemData = new ItemData();

        name = itemData.get(id).getString("name");
        material = Material.valueOf(itemData.get(id).getString("material"));
        this.id = id;
        level = itemData.get(id).getInt("level");
        int maxDamage = itemData.get(id).getInt("damage.max");
        int minDamage = itemData.get(id).getInt("damage.min");
        DamageType type = DamageType.valueOf(itemData.get(id).getString("damage.type"));
        damageData = new DamageData(type, minDamage, maxDamage);
    }

    public void build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(name);
        List<String> stringList = new ArrayList<String>();
        stringList.add("" + id);
        stringList.add("§7Damage: §8[§a" + damageData.min + " §7- §c" + damageData.max + "§8]");
        stringList.add("§7Level: §6" + level);
        meta.setLore(stringList);
        item.setItemMeta(meta);
        createdItem = item;
    }

    public void writeData() {
        ItemData itemData = new ItemData();
        itemData.get(id).set("name", name.replace('_', ' '));
        itemData.save(id);
        itemData.get(id).set("material", material.name());
        itemData.save(id);
        itemData.get(id).set("level", level);
        itemData.save(id);
        itemData.get(id).set("damage.max", damageData.max);
        itemData.save(id);
        itemData.get(id).set("damage.min", damageData.min);
        itemData.save(id);
        itemData.get(id).set("damage.type", damageData.type.toString());
        itemData.save(id);
    }

}
