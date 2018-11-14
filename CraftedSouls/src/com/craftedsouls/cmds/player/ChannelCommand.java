package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/channel <global|local|staff>");
            return true;
        }

        if(args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("g")) {
            if(userData.get(uuid).getBoolean("chat.global")) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You are already in the §6Global §7channel!");
                return true;
            }

            if(userData.get(uuid).getBoolean("chat.staff")) {
                userData.get(uuid).set("chat.staff", false);
                userData.save(uuid);
            }

            userData.get(uuid).set("chat.global", true);
            userData.save(uuid);
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have joined the §6Global §7channel. You can only chat every 5 seconds");
        } else if(args[0].equalsIgnoreCase("local") || args[0].equalsIgnoreCase("l")) {
            userData.get(uuid).set("chat.global", false);
            userData.save(uuid);
            if(userData.get(uuid).getBoolean("chat.staff")) {
                userData.get(uuid).set("chat.staff", false);
                userData.save(uuid);
            }
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have joined the §aLocal §7channel");
        } else if(args[0].equalsIgnoreCase("staff") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("ac")) {
            if(!player.hasPermission("cscore.staffchat")) {
                CraftedSouls.getChatManager().incorrectPermissions(player);
                return true;
            }
            if(userData.get(uuid).getBoolean("chat.staff")) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You are already in the §cStaff §7channel!");
                return true;
            }

            userData.get(uuid).set("chat.staff", true);
            userData.save(uuid);
            if(userData.get(uuid).getBoolean("chat.global")) {
                userData.get(uuid).set("chat.global", false);
                userData.save(uuid);
            }
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have joined the §cStaff §7channel");
        }
        return true;
    }
}
