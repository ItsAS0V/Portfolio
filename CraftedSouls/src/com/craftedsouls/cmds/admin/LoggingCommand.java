package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoggingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        UserData userData = UserData.getInstance();
        String uuid = player.getUniqueId().toString();

        if(!player.hasPermission("cscore.logging")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/logging <enable|disable>");
            return true;
        }

        if(args[0].equalsIgnoreCase("enable")) {
            userData.get(uuid).set("Logging", true);
            userData.save(uuid);
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Logging has been enabled. You can now see player commands");
            return true;
        } else if(args[0].equalsIgnoreCase("disable")) {
            userData.get(uuid).set("Logging", false);
            userData.save(uuid);
            CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Logging has been disabled. You can no longer see player commands");
        }

        return true;
    }
}
