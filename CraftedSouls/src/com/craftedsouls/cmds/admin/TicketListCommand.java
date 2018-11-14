package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TicketListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fTickets§8 ========");
        if(settings.getTickets().getConfigurationSection("tickets").getKeys(false) == null) {
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "There is no tickets.");
        }
        for(String key : settings.getTickets().getConfigurationSection("tickets").getKeys(false)) {
            String id = key;
            String author = settings.getTickets().getString("tickets." + key + ".user");
            String message = settings.getTickets().getString("tickets." + key + ".message");
            String accepted = "" + settings.getTickets().getBoolean("tickets." + key + ".accepted");
            CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8[§e#" + id + "§8] - §7" + Bukkit.getPlayer(UUID.fromString(author)).getDisplayName() + " §8- §f" + message + " §8[§aAccepted: §f" + accepted + "§8]");
        }
        CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fTickets§8 ========");
        return false;
    }
}
