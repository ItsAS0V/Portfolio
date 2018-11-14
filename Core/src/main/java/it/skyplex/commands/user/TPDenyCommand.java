package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TPDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if(args.length >= 1) {
            Core.getManager().getMessage().invalidCommandUse(player, "/tpdeny");
            return true;
        }

        Player target = Bukkit.getPlayer(Core.TPA_REQUESTS.get(playerUUID));
        if(target == null) {
            Core.getManager().getMessage().sendMessage(sender, "&cYou must have a pending request!");
            return true;
        }
        UUID targetUUID = target.getUniqueId();

        Core.getManager().getMessage().sendMessage(player, "&7You denied &6" + target.getName() + "&7's request.");
        Core.getManager().getMessage().sendMessage(target, "&6" + player.getName() + "&7 denied your TPA request.");

        Core.TPA_REQUESTS.remove(playerUUID);
        Core.TPA_REQUESTS.remove(targetUUID);

        return true;
    }
}
