package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TicketCreateCommand implements CommandExecutor {


    public int missingNumber(int [] arrA, int size){
        int Sum = 0;
        int Sum_N = size*(size+1)/2;
        for(int i=0;i<arrA.length;i++){
            Sum +=arrA[i];
        }
        return Sum_N-Sum;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();
        Settings settings = Settings.getInstance();

        if(args.length < 1) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/ticket <message>");
            return true;
        }


        //ALG BEGIN

        int ticketID = -1;

        ArrayList<Integer> ticketIDS = new ArrayList<Integer>();

        Collections.sort(ticketIDS);

        //In the ticket config file?
        //http://prntscr.com/em0d5h
        for(String key : settings.getTickets().getConfigurationSection("tickets").getKeys(false)) {
            int currentTicketID = Integer.parseInt(key);
            ticketIDS.add(currentTicketID);
        }

        ticketID = missingNumber(convertIntegers(ticketIDS), ticketIDS.size());

        if(ticketID == -1) {
            ticketID = ticketIDS.size() + 1;
        }

        //ALG END

        CraftedSouls.getDatabaseManager().createTicket(ticketID, false, message(args), player.getUniqueId().toString());


        settings.getTickets().set("tickets." + ticketID + ".user", player.getUniqueId().toString());
        settings.getTickets().set("tickets." + ticketID + ".message", message(args));
        settings.getTickets().set("tickets." + ticketID + ".accepted", false);

        settings.saveTickets();
        CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "§aYou have sucessfully submitted a ticket. [" + ticketID + "]");
        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            if(online.hasPermission("cscore.staff")) {
                CraftedSouls.getChatManager().sendNoPrefixChat(online, "§f" + player.getName() + " §7has submitted a ticket. Use §5/ticketlist §7to view open tickets!");
            }
        }
        return true;
    }

    public String message(String[] args) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            args[i] = args[i].replaceFirst("'", "");
            builder.append(args[i]).append(" ");
        }
        return builder.toString().trim();
    }
}
