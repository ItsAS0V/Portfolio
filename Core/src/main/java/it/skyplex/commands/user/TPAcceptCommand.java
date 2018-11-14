package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TPAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player accepter = (Player) sender;
        UUID accepterUUID = accepter.getUniqueId();

        if(args.length >= 1) {
            Core.getManager().getMessage().invalidCommandUse(accepter, "/tpaccept");
            return true;
        }

        Player teleporte = Bukkit.getPlayer(Core.TPA_REQUESTS.get(accepterUUID));
        if(teleporte == null) {
            Core.getManager().getMessage().sendMessage(sender, "&cYou must have a pending request!");
            return true;
        }
        UUID targetUUID = teleporte.getUniqueId();

        Core.getManager().getMessage().sendMessage(accepter, "&7Teleporting &6" + teleporte.getName() + "&7 to you.");
        Core.getManager().getMessage().sendMessage(teleporte, "&7Teleporting to &6" + accepter.getName() + "&7.");

        teleporte.teleport(accepter);

        Core.TPA_REQUESTS.remove(accepterUUID);
        Core.TPA_REQUESTS.remove(targetUUID);

        return true;
    }
}
