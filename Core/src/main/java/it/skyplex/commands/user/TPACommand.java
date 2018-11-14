package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TPACommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if (args.length < 1) {
            Core.getManager().getMessage().invalidCommandUse(sender, "/tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            Core.getManager().getMessage().sendMessage(sender, "&eError 404: &cPlayer not found.");
            return true;
        }
        UUID targetUUID = target.getUniqueId();

        Core.getManager().getMessage().sendMessage(sender, "&7You requested to teleport to &6" + target.getName() + "&7.");
        Core.getManager().getMessage().sendMessage(target, "&6" + sender.getName() + "&7 Has requested to teleport to you.");
        Core.getManager().getMessage().sendMessage(target, "&7Use &a/tpaccept &7to &aaccept &7or &c/tpdeny &7to &cdeny &7the request,");

        Core.TPA_REQUESTS.put(targetUUID, playerUUID);
        return true;
    }
}
