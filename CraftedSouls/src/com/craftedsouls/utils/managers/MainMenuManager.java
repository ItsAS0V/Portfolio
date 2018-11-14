package com.craftedsouls.utils.managers;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.ActionBar;
import com.craftedsouls.utils.Prefix;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class MainMenuManager {

    public ArrayList<UUID> mainMenu = new ArrayList<UUID>();

    @SuppressWarnings("deprecation")
    public static void playRecord(Player p, Location loc, Material record)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), record.getId(), false));
    }

    public static void stopRecord(Player p, Location loc)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), 0, false));
    }

    public void enterMainMenu(Player player) {
        mainMenu.add(player.getUniqueId());

        //playRecord(player, player.getLocation().add(0, -2, 0), Material.GOLD_RECORD);

        player.playEffect(player.getLocation(), Effect.RECORD_PLAY, Material.GREEN_RECORD.getId());

        player.teleport(new Location(player.getWorld(),0, 200, 0));

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 255));
        ActionBar bar = new ActionBar("§7Welcome to §f§oCraftedSouls §8§oV" + CraftedSouls.plugin.getDescription().getVersion());
        bar.sendToPlayer(player);

        player.setSilent(true);

        player.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(player);
            player.hidePlayer(online);
        }
    }

    public void exitMainMenu(Player player) {
        mainMenu.remove(player.getUniqueId());

        stopRecord(player, player.getLocation().add(0, -2, 0));

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You've exited the main menu");

        player.setSilent(false);

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.showPlayer(player);
            player.showPlayer(online);
        }

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

    }

    public boolean isInMainMenu(Player player) {
        return mainMenu.contains(player.getUniqueId());
    }


}
