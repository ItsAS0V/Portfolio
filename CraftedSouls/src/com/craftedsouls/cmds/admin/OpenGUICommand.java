package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenGUICommand  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("cscore.opengui")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/opengui <id>");
            return true;
        }
        int id = -1;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Please enter an integer as an ID");
        }
        if(id < 0) { return true; }
        player.sendMessage("ID Tried to open: " + id);
        CraftedSouls.getGUIManager().openGUI(id, player);

        return false;
    }
}
