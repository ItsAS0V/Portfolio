package com.craftedsouls.gui.guis;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.LevelData;
import com.craftedsouls.data.Settings;
import com.craftedsouls.data.UserData;
import com.craftedsouls.gui.GameGUI;
import com.craftedsouls.gui.GameGUIManager;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.items.ItemGlow;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.utils.managers.eco.EconomyValueTree;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CharacterGUI extends GameGUI {

    static UserData userData = UserData.getInstance();

    //UUID, LEVEL, XP, CHARSLOT, X, Y, Z, WORLD, FILLED, LOCKED


    public CharacterGUI() { super(); }

    ItemStack selectedCharacter = new ItemStack(Material.ENCHANTED_BOOK);
    ItemStack emptyCharacter = new ItemStack(Material.BOOK_AND_QUILL);
    ItemStack lockedCharacter = new ItemStack(Material.BOOK);
    ItemStack filledCharacter = new ItemStack(Material.BOOK);

    String selectedType = "§8(§aSelected§8)";
    String lockedType = "§8(§cLocked§8)";
    String emptyType = "§8(§6Empty§8)";
    String filledType = "§8(§7Filled§8)";

    @Override
    public void setDefaults(String title, int size) {
        super.setDefaults("§dCharacter Selection", 9);
    }

    @Override
    public int getID() {
        return 2;
    }

    public static ItemStack addGlow(ItemStack item){
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public void onOpenInventory(Player player) {
        //Sound
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);

        super.onOpenInventory(player);
        String uuid = player.getUniqueId().toString();

        FileConfiguration config = userData.get(uuid);

        int slot = userData.getCurrentChar(uuid);


        for(int i = 1; i < 6; i++){

            boolean locked = config.getBoolean("characters." + i + ".locked");
            boolean filled = config.getBoolean("characters." + i + ".filled");
            int level = config.getInt("characters." + i + ".level");
            int xp = config.getInt("characters." + i + ".xp");
            int balance = config.getInt("characters." + i + ".balance");

            int position = (i * 2) - 2;

            EconomyValueTree tree = new EconomyValueTree(balance);
            tree.SimplifyValues();

            ItemStack newItem;
            ItemMeta niMeta;

            if(userData.getCurrentChar(uuid) == i) {
                newItem = selectedCharacter.clone();
                niMeta = newItem.getItemMeta();
                niMeta.setDisplayName("§fCharacter " + i + " " + selectedType);

                niMeta.setLore(Arrays.asList("§7Level: §6" + level, "§7XP: §6" + xp));
            } else {
                if (locked) {
                    newItem = lockedCharacter.clone();
                    niMeta = newItem.getItemMeta();
                    niMeta.setDisplayName("§fCharacter " + i + " " + lockedType);
                    niMeta.setLore(Arrays.asList("§cUnlock by reaching max level"));
                } else if (filled) {
                    newItem = filledCharacter.clone();
                    niMeta = newItem.getItemMeta();
                    niMeta.setDisplayName("§fCharacter " + i + " " + filledType);
                    niMeta.setLore(Arrays.asList("§7Left-click to select", "§7Press Q to delete", "§7Level: §6" + level, "§7XP: §6" + xp));

                    ItemGlow glow = new ItemGlow(1723);
                    niMeta.addEnchant(glow, 1, true);
                } else {
                    newItem = emptyCharacter.clone();
                    niMeta = newItem.getItemMeta();
                    niMeta.setDisplayName("§fCharacter " + i + " " + emptyType);
                    niMeta.setLore(Arrays.asList("§7Left-click to create", "§7Level: §6" + level, "§7XP: §6" + xp));

                }
            }

            newItem.setItemMeta(niMeta);
            inventory.setItem(position, newItem);

        }

    }

    public static void saveLocation(String uuid, Player player) {
        UserData userData = UserData.getInstance();
        int charSlot = userData.getCurrentChar(uuid);

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        String world = player.getLocation().getWorld().getName();

        userData.get(uuid).set("characters." + charSlot + ".location.world", world);
        userData.save(uuid);
        userData.get(uuid).set("characters." + charSlot + ".location.x", x);
        userData.save(uuid);
        userData.get(uuid).set("characters." + charSlot + ".location.y", y);
        userData.save(uuid);
        userData.get(uuid).set("characters." + charSlot + ".location.z", z);
        userData.save(uuid);
    }

    public static void saveInventory(UserData userData, String uuid, Player player) {
        int charSlot = userData.getCurrentChar(uuid);

        userData.get(uuid).set("characters." + charSlot + ".inventory", player.getInventory().getContents());
        userData.save(uuid);
    }

    public static void barSetup(Player player) {
        if(CraftedSouls.getBarManager().getBarStorage().get(player.getUniqueId()) != null) {
            CraftedSouls.getBarManager().removePlayer(player.getUniqueId());
        }

        String uuid = player.getUniqueId().toString();
 
        //Player | health | maxhealth | spirit | maxspirit
        FileConfiguration config = userData.get(uuid);
        int currentChar = userData.getCurrentChar(uuid);
        int health = config.getInt("characters." + currentChar + ".stats.health");
        int maxhealth = config.getInt("characters." + currentChar + ".stats.maxhealth");

        int spirit = config.getInt("characters." + currentChar + ".stats.spirit"); //<
        int maxspirit = config.getInt("characters." + currentChar + ".stats.maxspirit");

        CraftedSouls.getBarManager().setupPlayer(player, health, maxhealth, spirit, maxspirit);
    }


    public static void loadInventory(UserData userData, String uuid, Player player) {
        int charSlot = userData.getCurrentChar(uuid);
        String data = userData.get(uuid).getString("characters." + charSlot + ".inventory");

        List<ItemStack> items = (List<ItemStack>) userData.get(uuid).getList("characters." + charSlot + ".inventory");
        player.getInventory().setContents(items.toArray(new ItemStack[items.size()]));

        player.updateInventory();

        userData.save(uuid);
    }

    public static void loadLocation(UserData userData, String uuid, Player player) {

        int charSlot = userData.getCurrentChar(uuid);

        String world = userData.get(uuid).getString("characters." + charSlot + ".location.world");
        int x = userData.get(uuid).getInt("characters." + charSlot + ".location.x");
        int y = userData.get(uuid).getInt("characters." + charSlot + ".location.y");
        int z = userData.get(uuid).getInt("characters." + charSlot + ".location.z");

        player.teleport(new Location(Bukkit.getWorld(world), x, y, z));
    }


    public void reload(Player player) {

        CraftedSouls.getGUIManager().openGUI(2, player);
    }


    public static void handleCharacterChange(Player player, int slot, boolean filled) {
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        ItemStack playerInformation = GameItemList.playerInformation;
        LevelData levelData = LevelData.getInstance();

        //Display Title
        displayTitle(player, slot);

        //Save
        if(!CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            saveLocation(uuid, player);
            saveInventory(userData, uuid, player);
        }

        if(filled) {
            //Save before change
            saveLocation(uuid, player);
            saveInventory(userData, uuid, player);
        }

        //Change
        userData.get(uuid).set("selectedchar", slot);
        userData.save(uuid);

        //Exit from combat
        if(CraftedSouls.getCombatManager().isPlayerCombatmode(player)) {
            CraftedSouls.getCombatManager().exitCombatmode(player);
        }

        if(filled) {
            //Load
            loadLocation(userData, uuid, player);
            player.getInventory().clear();
            loadInventory(userData, uuid, player);

            //Set Existing XP Values
            int level = userData.get(uuid).getInt("characters." + slot + ".level");
            int userXP = userData.get(uuid).getInt("characters." + slot + ".xp");
            int levelXP = levelData.get().getInt(level + ".RXP");
            float percent = ((float) userXP + 1) / (levelXP + 1);
            player.setExp(percent);
            player.setLevel(level);
        } else {
            //Teleport player to spawn
            player.teleport(player.getWorld().getSpawnLocation());

            //Save as filled
            userData.get(uuid).set("characters." + slot + ".filled", true);
            userData.save(uuid);

            //Player Information
            player.getInventory().clear();
            player.getInventory().setItem(8, playerInformation);;

            //Default Values
            player.setExp(0.0f);
            player.setLevel(1);
        }

        //Exit main menu
        if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            CraftedSouls.getMainMenuManager().exitMainMenu(player);
        }

        //Close out
        player.closeInventory();

        //Secure the Save
        saveLocation(uuid, player);
        saveInventory(userData, uuid, player);

        //Sound
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 2);

        //Bar Setup
        barSetup(player);
    }

    @Override
    public void onPlayerClickBlock(Player player, ItemStack is) {
        super.onPlayerClickBlock(player, is);

        if (!is.hasItemMeta()) {
            return;
        }

        String[] itemName = is.getItemMeta().getDisplayName().split(" ");

        if(itemName[2].equalsIgnoreCase(emptyType)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "§7Character slot created");
            int id = Integer.parseInt(itemName[1]);
            if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
                handleCharacterChange(player, id, false);
            } else {
                CraftedSouls.getCooldownManager().addCharacterChangeCooldown(player.getUniqueId(), id, false);
            }
            player.closeInventory();
        }

        if(itemName[2].equalsIgnoreCase(selectedType)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "§7You're already on this character slot!");
            //Sound
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10, 2);
            return;
        }

        if(itemName[2].equalsIgnoreCase(filledType)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "§7Character slot selected");
            int id = Integer.parseInt(itemName[1]);
            if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
                handleCharacterChange(player, id, true);
            } else {
                CraftedSouls.getCooldownManager().addCharacterChangeCooldown(player.getUniqueId(), id, true);
            }
            player.closeInventory();
        }

        if(itemName[2].equalsIgnoreCase(lockedType)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§7This character slot is locked");

            //Sound
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 10, 2);
        }
    }

    public static void displayTitle(Player player, int slot) {
        player.sendTitle("§7Character §f" + slot, "§fCraftedSouls §7§oV" + CraftedSouls.plugin.getDescription().getVersion(), 20, 40, 20);
    }

    @Override
    public boolean onInventoryClick(InventoryClickEvent event) {
        if(event.getAction() == InventoryAction.DROP_ONE_SLOT) {

            Player player = (Player)event.getWhoClicked();
            String uuid = player.getUniqueId().toString();
            String[] itemName = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
            int id = Integer.parseInt(itemName[1]);

            if(itemName[2].equalsIgnoreCase(filledType)) {
                userData.get(uuid).set("characters." + id + ".deleting", true);
                userData.save(uuid);
                CraftedSouls.getGUIManager().openGUI(8, player);
            } else {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You can only delete filled slots");
            }


            return true;
        }
        return true;
    }
}
