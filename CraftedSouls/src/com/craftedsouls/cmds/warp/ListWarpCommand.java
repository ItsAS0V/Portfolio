package com.craftedsouls.cmds.warp;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fWarps§8 ========");
        String output = "";
        for(String key : settings.getWarps().getKeys(false)) {
            output +=  "§7, "+ "§f" + key;
        }
        output = output.replaceFirst(",", " ");
        CraftedSouls.getChatManager().sendNoPrefixChat(player, output);
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fWarps§8 ========");

        return false;
    }
}