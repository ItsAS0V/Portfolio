package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarningCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;


        if (args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/warn <player> <reason>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);


        StringBuilder reason = new StringBuilder("");

        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        String uuid = target.getUniqueId().toString();

        /*if (databaseManager.userExist(uuid)) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player doesn't exist!");
            return true;
        }*/

        if (target == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player isn't online!");
            return true;
        }

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You've given a warning to the player " + target.getName());
        CraftedSouls.getChatManager().sendNoPrefixChat(target, "§8======= §4§lWARNING §8=======");
        CraftedSouls.getChatManager().sendNoPrefixChat(target, "§7You have received a warning from §f" + player.getName() + "§7!");
        CraftedSouls.getChatManager().sendNoPrefixChat(target, "§7Reason: §f" + message);
        CraftedSouls.getChatManager().sendNoPrefixChat(target, "§8(§c1§8) §7Warning point has been added.");
        CraftedSouls.getChatManager().sendNoPrefixChat(target, "§8======= §4§lWARNING §8=======");
        //int warnings = Integer.parseInt(databaseManager.getUserData("WARNINGS", uuid));

        //Fixed.
        return true;
    }
}
