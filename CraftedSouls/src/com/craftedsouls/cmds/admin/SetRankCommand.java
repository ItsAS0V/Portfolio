package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        DatabaseManager db = CraftedSouls.getDatabaseManager();


        if (!player.hasPermission("cscore.setrank")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        /*if(!db.getDevelopment()) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Development is currently disabled.");
            return true;
        }*/

        try {
            if (args.length == 0) {
                CraftedSouls.getChatManager().incorrectUsage(player, "/setrank <rank> [player]");
                return true;
            }

            if (Integer.parseInt(args[0]) > 125 || Integer.parseInt(args[0]) < 1) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid range (1-125)");
                return true;
            }

            if (args.length == 1) {
                CraftedSouls.getXPManager().setValues(player, Integer.parseInt(args[0]), 0, true);
            } else {
                Player target = Bukkit.getServer().getPlayer(args[1]);

                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Level for§f " + target.getName() + "§7 set to §f" + args[0] + "§7!");
                CraftedSouls.getXPManager().setValues(target, Integer.parseInt(args[0]), 0, true);
            }

        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter the rank as an integer");
            e.printStackTrace();
        }


        return false;

    }
}