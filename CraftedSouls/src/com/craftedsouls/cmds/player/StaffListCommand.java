package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(Bukkit.getServer().getOnlinePlayers().size() < 1) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThere are no players online");
            return true;
        }

        ArrayList<Player> players = new ArrayList<Player>(Bukkit.getServer().getOnlinePlayers());

        StringBuilder list = new StringBuilder();
        int p = 1;

        for(int i = 0; i < players.size(); i++) {
            if(list.length() > 0) {
                if(p == players.size()) {
                    list.append(ChatColor.WHITE + " and " + ChatColor.GRAY);
                } else {
                    list.append(ChatColor.WHITE + ", " + ChatColor.GRAY);
                }
            }

            list.append(players.get(i).getName());
            p++;
        }

        player.sendMessage("§7There are currently §6" + (p - 1) + "§8/§6" + Bukkit.getServer().getMaxPlayers() + " §7online");
        player.sendMessage("§dPlayers: §7" + list.toString());

        return true;
    }
}
