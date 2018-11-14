package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TicketCloseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        if(!player.hasPermission("cscore.ticket.close")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        if(args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/ticketclose <id> <message>");
            return true;
        }

        if(settings.getTickets().get("tickets." + args[0]) == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThis ticket has not been created!");
            return true;
        }

        if(!settings.getTickets().getBoolean("tickets." + args[0] + ".accepted")) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThis ticket has not been accepted!");
            return true;
        }
        Player ticket_creator = Bukkit.getServer().getPlayer(UUID.fromString(settings.getTickets().getString("tickets." + args[0] + ".user").toString()));

        settings.getTickets().set("tickets." + args[0], null);

        StringBuilder reason = new StringBuilder("");

        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§aYou have successfully closed a ticket");

        if(ticket_creator == null) {
            return true;
        }
        CraftedSouls.getChatManager().sendChat(ticket_creator, Prefix.GENERAL, "§7Your ticket has been closed by §5" + player.getName() + "§7. Response: §f" + message);

        return true;
    }
}
