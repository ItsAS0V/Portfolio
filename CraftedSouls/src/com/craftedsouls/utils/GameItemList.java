package com.craftedsouls.utils;

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
import java.util.List;

public class GameItemList {

    //Player Information
    public static ItemStack characterSelector;
    public static ItemStack playerInformation;
    public static ItemStack upgradeTree;
    public static ItemStack userInformation;
    public static ItemStack guildManagement;
    public static ItemStack friends;
    public static ItemStack stats;
    public static ItemStack bossinfo;
    public static ItemStack staffList;

    //Global
    public static ItemStack empty;

    //Bank
    public static ItemStack withdraw;
    public static ItemStack deposit;
    public static ItemStack economy;
    public static ItemStack back;

    //Eco
    public static ItemStack gold_eco;
    public static ItemStack silver_eco;
    public static ItemStack copper_eco;

    // /Weapons

    public static void createItems() {
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

        upgradeTree = new ItemStack(Material.DOUBLE_PLANT, 1);
        ItemMeta utMeta = upgradeTree.getItemMeta();
        utMeta.setDisplayName("§eSkill Tree");
        List<String> utLore = new ArrayList<>();
        utLore.add("§7This feature is coming soon.");
        utMeta.setLore(utLore);
        upgradeTree.setItemMeta(utMeta);

        guildManagement = new ItemStack(Material.BOW, 1);
        ItemMeta gmMeta = guildManagement.getItemMeta();
        gmMeta.setDisplayName("§bGuilds");
        List<String> gmLore = new ArrayList<>();
        gmLore.add("§7This feature is coming soon.");
        gmMeta.setLore(gmLore);
        guildManagement.setItemMeta(gmMeta);

        friends = new ItemStack(Material.SKULL_ITEM, 1);
        ItemMeta fMeta = friends.getItemMeta();
        fMeta.setDisplayName("§eFriends");
        List<String> fLore = new ArrayList<>();
        fLore.add("§7This feature is coming soon.");
        fMeta.setLore(fLore);
        friends.setItemMeta(fMeta);

        bossinfo = new ItemStack(Material.MOB_SPAWNER, 1);
        ItemMeta biMeta = bossinfo.getItemMeta();
        biMeta.setDisplayName("§6Boss Information");
        List<String> biLore = new ArrayList<>();
        biLore.add("§7This feature is coming soon.");
        biMeta.setLore(biLore);
        bossinfo.setItemMeta(biMeta);

        staffList = new ItemStack(Material.PAPER, 1);
        ItemMeta slMeta = staffList.getItemMeta();
        slMeta.setDisplayName("§cStaff List §8(§aOnline§8)");
        List<String> slLore =new ArrayList<>();
        slLore.add("§7Open the staff list");
        slMeta.setLore(slLore);
        staffList.setItemMeta(slMeta);

        withdraw = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
        ItemMeta wMeta = withdraw.getItemMeta();
        wMeta.setDisplayName("§aWithdraw");
        List<String> wLore = new ArrayList<>();
        wLore.add("§7Withdraw money from the bank.");
        wMeta.setLore(wLore);
        withdraw.setItemMeta(wMeta);

        deposit = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
        ItemMeta dMeta = deposit.getItemMeta();
        dMeta.setDisplayName("§cDeposit");
        List<String> dLore = new ArrayList<>();
        dLore.add("§7Deposit money into the bank.");
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

    }

    public static ItemStack createSkull(Player player) {
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
    }

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

    public static ItemStack createStats(int mobskilled, int totalxp, int quests) {
        stats = new ItemStack(Material.BOOK, 1);
        ItemMeta sMeta = stats.getItemMeta();
        sMeta.setDisplayName("§cStats");
        List<String> sLore = new ArrayList<>();
        sLore.add("§7Mobs Killed: §f" + mobskilled);
        sLore.add("§7Total XP: §f" + totalxp);
        sLore.add("§7Compleated Quests: §f" + quests);
        sMeta.setLore(sLore);
        stats.setItemMeta(sMeta);

        return stats;
    }

    public static ItemStack createEconomy(Player player) {
        economy = new ItemStack(Material.DIAMOND, 1);
        ItemMeta ecoMeta = economy.getItemMeta();
        List<String> ecoLore = new ArrayList<>();
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();
        FileConfiguration config = userData.get(uuid);
        int total = config.getInt("economy.total");

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
}
