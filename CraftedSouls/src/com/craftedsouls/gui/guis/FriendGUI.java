package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.utils.items.GameItemList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FriendGUI extends GameGUI {

    UserData userData = UserData.getInstance();

    public FriendGUI() { super(); }

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§3Friends", 9 * 5);
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);

        String uuid = player.getUniqueId().toString();
        FileConfiguration config = userData.get(uuid);
        List<String> playerFriend = config.getStringList("Friends");

        if (!playerFriend.isEmpty()) {
            for (String key : playerFriend) {
                Player friend = Bukkit.getPlayer(key);
                ItemStack skull = GameItemList.createSkull(friend);
                for(int i = 0; i < 9 * 6; i++) {
                    inventory.setItem(i, skull);
                }
            }
        }

    }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(3, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        super.onPlayerClickBlock(player, is);

        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        //if (is.getItemMeta().getDisplayName().equals(GameItemList.characterSelector.getItemMeta().getDisplayName())) {
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return super.onInventoryClick(event);
    }
}
