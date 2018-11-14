package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.AbilityData;
import com.craftedsouls.data.UserData;
import com.craftedsouls.data.builders.AbilityBuilder;
import com.craftedsouls.data.builders.ItemBuilder;
import com.craftedsouls.data.types.AbilityType;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class CombatManager {


    public HashMap<UUID, AbilityData> combatMode = new HashMap<>();

    public void toggleCombatmode(Player player) {
        if(isPlayerCombatmode(player)) {
            exitCombatmode(player);
        } else {
            enterCombatmode(player);
        }
    }

    public int[] getMaxCooldowns(Player player) {
        UserData userData = UserData.getInstance();
        String UUID = player.getUniqueId().toString();
        int currentChar = userData.getCurrentChar(UUID);
        int cooldowns[] = new int[8];
        for(int i = 1; i < 9; i++) {
            int id = userData.get(UUID).getInt("characters." + currentChar + ".combat.abilities." + i + ".id");
            if(id == 0) {
                cooldowns[i - 1] = 0;
            } else {
                AbilityBuilder builder = new AbilityBuilder(id);
                cooldowns[i - 1] = builder.cooldown;
            }
        }
        return cooldowns;
    }

    public int getMaxCooldown(Player player, int slot) {
        UserData userData = UserData.getInstance();
        String UUID = player.getUniqueId().toString();
        int currentChar = userData.getCurrentChar(UUID);
        int id = userData.get(UUID).getInt("characters." + currentChar + ".combat.abilities." + slot + ".id");
        if(id == 0) {
            return 0;
        } else {
            AbilityBuilder builder = new AbilityBuilder(id);
            return  builder.cooldown;
        }
    }

    public void enterCombatmode(Player player) {
        UserData userData = UserData.getInstance();
        String UUID = player.getUniqueId().toString();

        int currentChar = userData.getCurrentChar(UUID);
        int playerLevel = userData.get(UUID).getInt("characters." + currentChar + ".level");

        ItemStack mainhand = player.getInventory().getItemInMainHand();
        short ID = 0;
        try {
            ID = Short.valueOf(mainhand.getItemMeta().getLore().get(0));
        } catch (Exception e) {
            return;
        }

        ItemBuilder item = new ItemBuilder(ID);
        item.build();

        int itemLevel = item.level;
        if (playerLevel < itemLevel) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§7You must be at least level §a" + itemLevel + " §7to use this item!");
            return;
        }

        if(!CraftedSouls.getCooldownManager().cooldownAbilities.containsKey(player.getUniqueId())) {
            CraftedSouls.getCooldownManager().addAbilityCooldowns(player.getUniqueId(), new int[8]);
        }

        userData.get(UUID).set("combat.hotbar.oslot", player.getInventory().getHeldItemSlot());
        userData.save(UUID);

        for (int i = 9; i >= 0; i--) {
            userData.get(UUID).set("combat.hotbar." + i, player.getInventory().getContents()[i]);
            userData.save(UUID);
        }

        player.getInventory().setItem(0, player.getInventory().getItemInMainHand());
        player.getInventory().setHeldItemSlot(0);

        for (int i = 1; i < 9; i++) {

            int id = 0;

            id = userData.get(UUID).getInt("characters." + currentChar + ".combat.abilities." + i + ".id");

            if(id == 0) {
                player.getInventory().setItem(i, new ItemStack(GameItemList.empty));
            } else {
                AbilityBuilder ability = new AbilityBuilder(id);
                ability.build();
                int cooldown = CraftedSouls.getCooldownManager().getAbilityCooldown(player.getUniqueId(), i);
                if(cooldown <= 0){
                    ability.createdAbility.setType(Material.ENCHANTED_BOOK);
                } else {
                    ability.createdAbility.setType(Material.BOOK);
                }
                ability.createdAbility.setAmount(cooldown + 1);
                player.getInventory().setItem(i, ability.createdAbility);
            }
        }

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have entered combat mode");
        AbilityData abilityData = new AbilityData();
        combatMode.put(player.getUniqueId(), abilityData);
    }


    public void exitCombatmode(Player player) {
        UserData userData = UserData.getInstance();
        String UUID = player.getUniqueId().toString();

        for (int i = 9; i >= 0; i--) {
            ItemStack itemStack = userData.get(UUID).getItemStack("combat.hotbar." + i);
            player.getInventory().setItem(i, itemStack);
        }

        int heldSlot = userData.get(UUID).getInt("combat.hotbar.oslot");
        player.getInventory().setHeldItemSlot(heldSlot);

        userData.get(UUID).set("combat.hotbar", null);
        userData.save(UUID);

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have exited combat mode");
        combatMode.remove(player.getUniqueId());
    }

    public boolean isPlayerCombatmode(Player player) {
        return combatMode.containsKey(player.getUniqueId());
    }

    public void useAbility(AbilityBuilder builder, Player player) {
        int slot = player.getInventory().getHeldItemSlot();

        if(CraftedSouls.getBarManager().getSpiritValue(player) <= builder.spirit) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You do not have enough spiritual energy to use this ability!");
            return;
        }

        if(CraftedSouls.getCooldownManager().getAbilityCooldown(player.getUniqueId(), slot) == 0) {
            CraftedSouls.getBarManager().removeSpirit(player, builder.spirit);

            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You used the ability " + builder.name + "!");

            if (builder.abilityType == AbilityType.HEALING_SELF) {
                CraftedSouls.getBarManager().healPlayer(player, builder.power);
            }

            if (builder.abilityType == AbilityType.SPIRIT_SELF) {
                CraftedSouls.getBarManager().addSpirit(player, builder.power);
            }

            if (builder.abilityType == AbilityType.SNOWBALL_PROJECTILE) {
                Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                Snowball snowball = player.getWorld().spawn(loc, Snowball.class);
                snowball.setShooter(player);
                snowball.setVelocity(player.getEyeLocation().getDirection().multiply(builder.power));
            }

            if (builder.abilityType == AbilityType.FIREBALL_PROJECTILE) {
                Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                Fireball fireball = player.getWorld().spawn(loc, Fireball.class);
                fireball.setShooter(player);
                fireball.setVelocity(player.getEyeLocation().getDirection().multiply(builder.power));
            }

            player.playSound(player.getLocation(), builder.sound, 1, 1);

            int maxCooldown = getMaxCooldown(player, slot);
            CraftedSouls.getCooldownManager().setAbilityCooldown(player.getUniqueId(), slot, maxCooldown);

            player.getInventory().getItemInMainHand().setType(Material.ENCHANTED_BOOK);
            player.getInventory().getItemInMainHand().setAmount(getMaxCooldown(player, slot));

        } else {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "This ability is on cooldown");
        }
    }

    public int getItemID(ItemStack item) {
        short ID = 0;
        try {
            ID = Short.valueOf(item. getItemMeta().getLore().get(0));
        } catch (Exception e) {
            return 0;
        }
        return ID;
    }

    public void setAbilityCooldown(Player player, int slot, int cooldown) {
        ItemStack current = player.getInventory().getItem(slot);
        current.setAmount(cooldown + 1);

        ItemMeta storedMeta = current.getItemMeta();
        if(cooldown <= 0) {
            current.setType(Material.ENCHANTED_BOOK);
        } else {
            current.setType(Material.BOOK);
        }
        current.setItemMeta(storedMeta);

        player.getInventory().setItem(slot, current);
    }

    public int getAbilityID(ItemStack item) {
        short ID = 0;
        try {
            ID = Short.valueOf(item. getItemMeta().getLore().get(0));
        } catch (Exception e) {
            return 0;
        }
        return ID;
    }




}
