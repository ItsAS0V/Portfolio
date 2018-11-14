package it.skyplex.commands.staff;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.kick")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        if(args.length < 2) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/kick <player> <reason>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);

        StringBuilder reason = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        String message = reason.toString().trim();

        if(target == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&eError 404: &cPlayer not found.");
            return true;
        }
        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&b&lSkyplex Punishment"
            + "\n" + "\n&cYour account has been kicked from the server."
            + "\n" + "&7Kicked by: &c" + sender.getName()
            + "\n" + "&7Reason: &e" + message));
        api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iKicked " + target.getName() + "&7&i]");
        return true;
    }
}
