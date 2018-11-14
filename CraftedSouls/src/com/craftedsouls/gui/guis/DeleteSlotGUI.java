package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteSlotGUI extends GameGUI {

    ItemStack confirm = new ItemStack(Material.GREEN_SHULKER_BOX);
    ItemStack deny = new ItemStack(Material.RED_SHULKER_BOX);

    public DeleteSlotGUI() {
        super();
    }

    ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

    @Override
    public void setDefaults(String name, int size) {
        super.setDefaults("§c§lCharacter Deletion", 9);
    }

    @Override
    public int getID() {
        return 8;
    }


    public int findSlot(Player player) {
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        int slot = 0;
        for(int i = 1; i <= 5; i++) {
            if(userData.get(uuid).getBoolean("characters." + i + ".deleting")) {
                slot = i;
                break;
            }
        }
        if(slot == 0) {
            return -1;
        }
        return slot;
    }


    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);
        String uuid = player.getUniqueId().toString();

        UserData userData = UserData.getInstance();

        int slot = findSlot(player);
        if(slot == -1) {
            return;
        }

        ItemMeta cMeta = confirm.getItemMeta();
        ItemMeta dMeta = deny.getItemMeta();

        cMeta.setDisplayName("§aConfirm");
        dMeta.setDisplayName("§cDeny");

        cMeta.setLore(Collections.singletonList("§7Click to confirm deletion of character slot " + slot));
        dMeta.setLore(Collections.singletonList("§7Click to deny deletion of character slot " + slot));
        confirm.setItemMeta(cMeta);
        deny.setItemMeta(dMeta);

        inventory.setItem(2, confirm);
        inventory.setItem(6, deny);

        CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§c§lDo you really want to delete character slot " + slot + "?");
    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(3, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        int slot = findSlot(player);
        if(slot == -1) {
            return;
        }

        if(is.getItemMeta().getDisplayName().equals(confirm.getItemMeta().getDisplayName())) {

         userData.get(uuid).set("characters." + slot + ".filled", false);
            userData.save(uuid);
            userData.get(uuid).set("characters." + slot + ".deleting", false);
            userData.save(uuid);

            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Character slot " + slot + " §cdeleted!");
            player.closeInventory();
        }

        if(is.getItemMeta().getDisplayName().equals(deny.getItemMeta().getDisplayName())) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You have cancelled the deletion of the character");

            userData.get(uuid).set("characters." + slot + ".deleting", false);
            userData.save(uuid);

            player.closeInventory();
        }
    }

    @Override
    public void onPlayerCloseInventory(Player player) {
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        int slot = findSlot(player);
        if(slot == -1) {
            userData.get(uuid).set("characters." + slot + ".deleting", false);
            userData.save(uuid);
        } else {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You have cancelled the deletion of the character");
        }



    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return true;
    }
}
