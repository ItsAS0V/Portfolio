package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.managers.eco.EconomyValueTree;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EconomyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();

        int charslot = userData.getCurrentChar(uuid);

        if(args.length != 3) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "/economy <set|give> <amount> <player>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[2]);

        if(target == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Cannot find the target");
            return true;
        }

        String tUUID = target.getUniqueId().toString();



        try {

            if (args[0].equalsIgnoreCase("set")) {
                if(player.hasPermission("cscore.economy")) {
                    int amount = Integer.parseInt(args[1]);

                    EconomyValueTree tree = new EconomyValueTree(amount);
                    tree.SimplifyValues();

                    userData.get(tUUID).set("characters." + charslot + ".balance", amount);
                    userData.save(tUUID);

                    //CraftedSouls.getChatManager().sendChat(, Prefix.GENERAL, "§cCopper: §f" + tree.copper + ", §7Silver: §f" + tree.silver + ", §6Gold: §f" + tree.gold);

                    CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have set §6" + target.getName() + "'s §7balance to §f" + amount);
                    CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, "Your balance has been set to §f" + amount + " §7by §6" + player.getName());
                } else {
                    CraftedSouls.getChatManager().incorrectPermissions(player);
                }

            } else if (args[0].equalsIgnoreCase("give")) {
                int amount = Integer.parseInt(args[1]);

                EconomyValueTree tree = new EconomyValueTree(amount);
                tree.SimplifyValues();

                ItemStack copper = GameItemList.copper_eco;
                copper.setAmount(tree.copper);

                ItemStack silver = GameItemList.silver_eco;
                silver.setAmount(tree.silver);

                ItemStack gold = GameItemList.gold_eco;
                gold.setAmount(tree.gold);

                player.getInventory().addItem(copper);
                player.getInventory().addItem(silver);
                player.getInventory().addItem(gold);

                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have given yourself: " + "§cCopper: §f" + tree.copper + ", §7Silver: §f" + tree.silver + ", §6Gold: §f" + tree.gold);
            }
        } catch (Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter the amount as a valid integer!");
        }

        return true;
    }

}
