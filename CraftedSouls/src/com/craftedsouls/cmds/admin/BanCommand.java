package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.utils.Prefix;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("cscore.ban")) {
            CraftedSouls.getChatManager().incorrectPermissions(sender);
        }

        if(args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(sender, "/ban <player> <reason>");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);

        BanList list = Bukkit.getBanList(BanList.Type.NAME);

        StringBuilder reason = new StringBuilder("");

        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        UserData userData = UserData.getInstance();

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");

        String kickmsg = "§cYour account has been suspended from the server" + "\n" + "\n" + "§7Suspended by: §a" + sender.getName() + "\n" + "§7Reason: §f" + message
                + "\n" + "§7Date: §a" + format.format(date) + "\n" + "\n" + "§cIf you feel like this was a mistake, please make an appeal at §7§n§ocraftedsouls.com";

        /*if(!databaseManager.userExist(targetUUID) || !userData.userFileExists(targetUUID)) {
           CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player doesn't exist!");
           return true;
        }*/

        if(target == null) {
            CraftedSouls.getChatManager().banMessage(args[0], message);
            list.addBan(args[0], kickmsg, null, sender.getName());
            return true;
        }

        target.kickPlayer(kickmsg);
        list.addBan(target.getName(), kickmsg, null, sender.getName());
        CraftedSouls.getChatManager().banMessage(target.getName(), message);
        return true;
    }
}
