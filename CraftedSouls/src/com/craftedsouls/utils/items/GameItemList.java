package com.craftedsouls.utils.items;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.managers.eco.EconomyValueTree;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameItemList {

    //Player Information
    public static ItemStack characterSelector;
    public static ItemStack playerInformation;
    public static ItemStack skills;
    public static ItemStack userInformation;
    public static ItemStack guild;
    public static ItemStack friends;
    public static ItemStack list;
    public static ItemStack changelog;

    //Global
    public static ItemStack empty;
    public static ItemStack confirm;

    //Bank
    public static ItemStack withdraw;
    public static ItemStack deposit;
    public static ItemStack economy;
    public static ItemStack back;

    //Eco
    public static ItemStack gold_eco;
    public static ItemStack silver_eco;
    public static ItemStack copper_eco;


    public static void createItems() {
        playerInformation = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta piMeta = playerInformation.getItemMeta();
        piMeta.setDisplayName("§dPlayer Information");
        piMeta.setLore(Collections.singletonList("§7Right-click to open"));
        playerInformation.setItemMeta(piMeta);

        gold_eco = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta geMeta = gold_eco.getItemMeta();
        geMeta.setDisplayName("§6Gold");
        gold_eco.setItemMeta(geMeta);

        silver_eco = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta seMeta = silver_eco.getItemMeta();
        seMeta.setDisplayName("§7Silver");
        silver_eco.setItemMeta(seMeta);

        copper_eco = new ItemStack(Material.CLAY_BRICK, 1);
        ItemMeta ceMeta = copper_eco.getItemMeta();
        ceMeta.setDisplayName("§cCopper");
        copper_eco.setItemMeta(ceMeta);

        characterSelector = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta csMeta = characterSelector.getItemMeta();
        csMeta.setDisplayName("§3Character Selector");
        List<String> csLore = new ArrayList<String>();
        csLore.add("§7Open the character selector");
        csMeta.setLore(csLore);
        characterSelector.setItemMeta(csMeta);

        skills = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta sMeta = skills.getItemMeta();
        sMeta.setDisplayName("§eSkills");
        sMeta.setLore(Collections.singletonList("§7This feature is coming soon"));
        skills.setItemMeta(sMeta);

        guild = new ItemStack(Material.SHIELD, 1);
        ItemMeta gmMeta = guild.getItemMeta();
        gmMeta.setDisplayName("§bGuilds");
        gmMeta.setLore(Collections.singletonList("§7This feature is coming soon"));
        guild.setItemMeta(gmMeta);

        friends = new ItemStack(Material.SKULL_ITEM, 1);
        ItemMeta fMeta = friends.getItemMeta();
        fMeta.setDisplayName("§eFriends");
        List<String> fLore = new ArrayList<>();
        fLore.add("§7This feature is coming soon");
        fMeta.setLore(fLore);
        friends.setItemMeta(fMeta);

        list = new ItemStack(Material.PAPER, 1);
        ItemMeta lMeta = list.getItemMeta();
        lMeta.setDisplayName("§aPlayer List");
        lMeta.setLore(Collections.singletonList("§7Click to view online player"));
        list.setItemMeta(lMeta);

        withdraw = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
        ItemMeta wMeta = withdraw.getItemMeta();
        wMeta.setDisplayName("§aWithdraw");
        List<String> wLore = new ArrayList<>();
        wLore.add("§7Withdraw money from the bank");
        wMeta.setLore(wLore);
        withdraw.setItemMeta(wMeta);

        deposit = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
        ItemMeta dMeta = deposit.getItemMeta();
        dMeta.setDisplayName("§cDeposit");
        List<String> dLore = new ArrayList<>();
        dLore.add("§7Deposit money into the bank");
        dMeta.setLore(dLore);
        deposit.setItemMeta(dMeta);

        empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15);
        ItemMeta eMeta = empty.getItemMeta();
        eMeta.setDisplayName(" ");
        empty.setItemMeta(eMeta);

        back = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
        ItemMeta bMeta = back.getItemMeta();
        bMeta.setDisplayName("§7<- §cBack");
        back.setItemMeta(bMeta);

        confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
        ItemMeta cMeta = confirm.getItemMeta();
        cMeta.setDisplayName("§aConfirm");
        List<String> cLore = new ArrayList<>();
        cLore.add("§7Confirm your item amount");
        cMeta.setLore(cLore);
        confirm.setItemMeta(cMeta);

        changelog = new ItemStack(Material.BOOK);
        ItemMeta clMeta = changelog.getItemMeta();
        clMeta.setDisplayName("§c§oChangelog");
        clMeta.setLore(Collections.singletonList("§7Version: §a" + CraftedSouls.plugin.getDescription().getVersion()));
        changelog.setItemMeta(clMeta);
    }

    /*public static ItemStack createSkull(Player player) {
        List<String> lore = new ArrayList<>();
        playerInformation = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta piMeta = (SkullMeta) playerInformation.getItemMeta();
        piMeta.setOwner(player.getName());
        lore.clear();
        lore.add(null);
        lore.add("§7Right-Click to view your information");
        piMeta.setLore(lore);
        piMeta.setDisplayName("§aPlayer Information §8(§cRight-Click§8)");
        playerInformation.setItemMeta(piMeta);

        return playerInformation;
    }*/

    public static ItemStack createUserInformation(String name, String rank, int level, int xp, int totalmoney, Player player) {
        List<String> lore = new ArrayList<>();
        userInformation = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta userMeta = (SkullMeta) userInformation.getItemMeta();
        userMeta.setOwner(player.getName());
        lore.clear();
        lore.add(null);
        lore.add("§7Name: §f" + name);
        lore.add("§7Rank: §f" + rank);
        lore.add("§7Level: §6" + level);
        lore.add("§7XP: §6" + xp);
        lore.add("§7Total Money: §a" + totalmoney);
        userMeta.setLore(lore);
        userMeta.setDisplayName("§aPlayer Information");
        userInformation.setItemMeta(userMeta);

        return userInformation;
    }

    public static ItemStack createEconomy(Player player) {
        economy = new ItemStack(Material.DIAMOND, 1);
        ItemMeta ecoMeta = economy.getItemMeta();
        List<String> ecoLore = new ArrayList<>();
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        FileConfiguration config = userData.get(uuid);
        int total = config.getInt("characters." + userData.getCurrentChar(uuid) + ".balance");

        EconomyValueTree playerMoney = new EconomyValueTree(total);
        playerMoney.SimplifyValues();

        ecoLore.add("§7Bank: ");
        ecoLore.add("§f" + playerMoney.copper + " §cCopper");
        ecoLore.add("§f" + playerMoney.silver + " §7Silver");
        ecoLore.add("§f" + playerMoney.gold + " §6Gold");
        ecoMeta.setDisplayName("§eDenari");
        ecoMeta.setLore(ecoLore);
        economy.setItemMeta(ecoMeta);
        return economy;
    }

    public static ItemStack setCopperAmount(int amount) {
        copper_eco = new ItemStack(Material.CLAY_BRICK, amount);
        ItemMeta ceMeta = copper_eco.getItemMeta();
        ceMeta.setDisplayName("§cCopper");
        copper_eco.setItemMeta(ceMeta);

        return copper_eco;
    }

    public static ItemStack setSilverAmount(int amount) {
        silver_eco = new ItemStack(Material.IRON_INGOT, amount);
        ItemMeta seMeta = silver_eco.getItemMeta();
        seMeta.setDisplayName("§7Silver");
        silver_eco.setItemMeta(seMeta);

        return silver_eco;
    }

    public static ItemStack setGoldAmount(int amount) {
        gold_eco = new ItemStack(Material.GOLD_INGOT, amount);
        ItemMeta geMeta = gold_eco.getItemMeta();
        geMeta.setDisplayName("§6Gold");
        gold_eco.setItemMeta(geMeta);

        return gold_eco;
    }
}
