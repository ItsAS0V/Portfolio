package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.managers.DatabaseManager;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicketAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();

        Player player = (Player) sender;
        Settings settings = Settings.getInstance();

        if(!player.hasPermission("cscore.ticket.accept")) {
            CraftedSouls.getChatManager().incorrectPermissions(player);
            return true;
        }

        int id = Integer.parseInt(args[0]);

        if(args.length < 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/ticketaccept <id>");
            return true;
        }

        if(settings.getTickets().get("tickets." + args[0]) == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThis ticket has not been created!");
            return true;
    }

        if(settings.getTickets().getBoolean("tickets." + args[0] + ".accepted")) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThis ticket has already been accepted!");
            return true;
        }

        settings.getTickets().set("tickets." + args[0] + ".accepted", true);
        settings.saveTickets();

        databaseManager.acceptTicket(id);
        CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§aYou have accepted a ticket");
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "§f" + player.getName() + " §7has accepted your ticket. Please await their response");

        return true;
    }
}
