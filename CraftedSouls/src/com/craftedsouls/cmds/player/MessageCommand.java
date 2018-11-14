package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;

        if(args.length < 2) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/message <player> <message>");
            return true;
        }

        StringBuilder reason = new StringBuilder("");

        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        String message = reason.toString().trim();

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(target == null) {
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "§cThe player §f" + args[0] + " §cisn't online!");
            return true;
        }

        player.sendMessage(Prefix.MESSAGESEND + "[§fMe §7->§f " + target.getName() + "§7] §f" + message);
        target.sendMessage(Prefix.MESSAGESEND + "[§f" + player.getName() + " §7-> §fMe§7] §f" + message);
        return true;
    }
}
