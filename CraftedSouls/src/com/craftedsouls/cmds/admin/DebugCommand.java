package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Settings settings = Settings.getInstance();

        if(!(sender instanceof Player)) {
            return true;
      }

        Player player = (Player) sender;

        if (!player.hasPermission("cscore.debug")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/debug <true|false>");
            return true;
        }

        boolean value = Boolean.parseBoolean(args[0]);

        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();

        databaseManager.setDebug(value);
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Debug is now set to " + value + "!");

        return true;
    }
}
