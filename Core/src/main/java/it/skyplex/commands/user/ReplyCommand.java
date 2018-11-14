package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if(args.length == 0) {
            Core.getManager().getMessage().invalidCommandUse(player, "/reply <message>");
            return true;
        }
        StringBuilder reason = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        String message = reason.toString().trim();

        Player target = Bukkit.getPlayer(playerUUID);
        if(target == null) {
            if(Core.MSGREPLY.containsKey(playerUUID)) {
                Core.getManager().getMessage().sendMessage(sender, "&cThe player you were messaging left! You need to message someone new!");
                Core.MSGREPLY.remove(playerUUID);
            }
            Core.getManager().getMessage().sendMessage(sender, "&cYou need to message someone!");
            return true;
        }
        UUID targetUUID = target.getUniqueId();

        Core.getManager().getMessage().sendMessage(player, "&6[Me -> &f" + target.getName() + "&6] &7" + message);
        Core.getManager().getMessage().sendMessage(target, "&6[&f" + player.getName() + " &6-> &6Me] &7" + message);

        Core.MSGREPLY.remove(playerUUID);
        Core.MSGREPLY.remove(targetUUID);
        return true;
    }
}
