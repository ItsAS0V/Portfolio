package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerInformationGUI extends GameGUI {

    UserData userData = UserData.getInstance();

    public PlayerInformationGUI() { super(); }

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§3Player Information", 9 * 4);
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void onOpenInventory(Player player) {
        super.onOpenInventory(player);

        String uuid = player.getUniqueId().toString();

        FileConfiguration config = userData.get(uuid);
        int charSlot = userData.getCurrentChar(uuid);
        int level = config.getInt("characters." + charSlot + ".level");
        int xp = config.getInt("characters." + charSlot + ".xp");

        String rankName = ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getParentIdentifiers().get(0));
        ItemStack userInformation = GameItemList.createUserInformation(player.getName(), rankName, level, xp, config.getInt("total"), player);

        //inventory.setItem(0, stats);
        inventory.setItem(4, userInformation);
        inventory.setItem(11, GameItemList.changelog);
        inventory.setItem(15, GameItemList.skills);
        inventory.setItem(22, GameItemList.characterSelector);
        inventory.setItem(29, GameItemList.list);
        inventory.setItem(33, GameItemList.guild);
     }

    public void reload(Player player) {
        CraftedSouls.getGUIManager().openGUI(1, player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        if (!is.hasItemMeta()) {
            return;
        }

        super.onPlayerClickBlock(player, is);

        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        if (is.getItemMeta().getDisplayName().equals(GameItemList.characterSelector.getItemMeta().getDisplayName())) {
            CraftedSouls.getGUIManager().openGUI(2, player);
        } else if(is.getItemMeta().getDisplayName().equals(GameItemList.guild.getItemMeta().getDisplayName())) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "This feature is coming soon!");
            player.closeInventory();
        } else if(is.getItemMeta().getDisplayName().equals(GameItemList.skills.getItemMeta().getDisplayName())) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "This feature is coming soon!");
            player.closeInventory();
        } else if(is.getItemMeta().getDisplayName().equals(GameItemList.list.getItemMeta().getDisplayName())) {
            CraftedSouls.getGUIManager().openGUI(4, player);
        } else if(is.getItemMeta().getDisplayName().equals(GameItemList.changelog.getItemMeta().getDisplayName())) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "This feature is coming soon!");
            player.closeInventory();
        }
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        return true;
    }
}
