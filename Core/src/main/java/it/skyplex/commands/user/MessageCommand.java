package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MessageCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if(args.length < 2) {
            Core.getManager().getMessage().invalidCommandUse(sender, "/message <player> <message>");
            return true;
        }
        StringBuilder reason = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        String message = reason.toString().trim();

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            Core.getManager().getMessage().sendMessage(sender, "&eError 404: &cPlayer not found.");
            return true;
        }
        UUID targetUUID = target.getUniqueId();

        Core.getManager().getMessage().sendMessage(sender, "&6[Me -> &f" + target.getName() + "&6] &7" + message);
        Core.getManager().getMessage().sendMessage(target, "&6[&f" + sender.getName() + " &6-> &6Me] &7" + message);

        Core.MSGREPLY.put(playerUUID, targetUUID);
        return true;
    }
}
