package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffListGUI extends GameGUI {

    public StaffListGUI() { super(); }

    ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

    @Override
    public void setDefaults(String name, int size) {
        super.setDefaults("§4Staff Menu", 9 * 6);
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);
        String uuid = player.getUniqueId().toString();

        int index = 0;

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            if(online.hasPermission("cscore.staff")) {
                List<String> lore = new ArrayList<>();
                String rankName = ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(online).getParentIdentifiers().get(0));
                playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta phMeta = (SkullMeta) playerHead.getItemMeta();
                phMeta.setOwner(online.getName());
                lore.clear();
                lore.add("§7Rank: §f" + rankName);
                phMeta.setLore(lore);
                phMeta.setDisplayName("§6" + online.getName());
                playerHead.setItemMeta(phMeta);
                inventory.setItem(index * 1, playerHead);
                index += 1;
            }
        }
    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(3, player);
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return true;
    }
}
