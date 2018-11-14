package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SQLCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("cscore.sql")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
        }

        if(args.length < 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/sql <clear|execute> <arg>");
            return false;
        }

        if (Objects.equals(args[0], "clear")) {

            if(args.length == 1) {
                CraftedSouls.getDatabaseManager().deleteUserData(player.getUniqueId().toString());
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Database information cleared for " + player.getName());
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                CraftedSouls.getDatabaseManager().deleteUserData(target.getUniqueId().toString());
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Database information cleared for " + target.getName());
            }

        } else if(Objects.equals(args[0], "execute")) {
            if (args.length > 1) {
                String SQL = "";
                for(String arg : args) {
                    if(!Objects.equals(arg, "execute")) {
                        SQL += arg + " ";
                    }
                }
                CraftedSouls.getDatabaseManager().executeUpdate(SQL);
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Executed SQL: " + SQL);
            } else {
                CraftedSouls.getChatManager().incorrectUsage(player, "/sql execute <SQL>");
            }
        }

        return false;
    }

}
