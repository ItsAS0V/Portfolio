package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerDevCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Settings settings = Settings.getInstance();

        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;

        if (!(player.hasPermission("cscore.development"))) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/development <true|false>");
            return true;
        }

        boolean value;
        try {
            value = Boolean.parseBoolean(args[0]);
        } catch(Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter a valid value for the development (True/False)");
            settings.getData().set("development", args[0]);
            settings.saveData();
            e.printStackTrace();
            return true;
        }

        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Development is now set to " + args[0] + "!");

        return true;
    }
}
