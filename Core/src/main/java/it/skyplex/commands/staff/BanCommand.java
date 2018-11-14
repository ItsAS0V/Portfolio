package it.skyplex.commands.staff;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /*if(!sender.hasPermission("skyplex.ban")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        if(args.length < 2) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/ban <player> <reason>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);

        StringBuilder reason = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        String message = reason.toString().trim();

        if(target == null) {
            OfflinePlayer ot = Bukkit.getOfflinePlayer(args[0]);
            if(!ot.hasPlayedBefore()) {
                Core.getManager().getMessage().sendMessage(sender, "&cThat player has never joined the server!");
                return true;
            }
            Core.getManager().getMessage().staffMessage("&7&m----------------------------------------");
            Core.getManager().getMessage().staffMessage(" ");
            Core.getManager().getMessage().staffMessage("                   &c&lPunishment");
            Core.getManager().getMessage().staffMessage(" ");
            Core.getManager().getMessage().staffMessage("&c" + sender.getName() + " &7has banned &b" + args[0]);
            Core.getManager().getMessage().staffMessage("&7Reason: &f" + message);
            Core.getManager().getMessage().staffMessage(" ");
            Core.getManager().getMessage().staffMessage("&7&m----------------------------------------");

            Bukkit.getServer().getBannedPlayers().add(ot);
            UsersFiles uf = UsersFiles.getOffline(ot);

            uf.getFile().set("banned.enabled", true);
            uf.getFile().set("banned.reason", message);
            uf.getFile().set("banned.sender", sender.getName());

            return true;
        }


        Core.getManager().getMessage().staffMessage("&7&m----------------------------------------");
        Core.getManager().getMessage().staffMessage(" ");
        Core.getManager().getMessage().staffMessage("                   &c&lPunishment");
        Core.getManager().getMessage().staffMessage(" ");
        Core.getManager().getMessage().staffMessage("&c" + sender.getName() + " &7has banned &b" + args[0]);
        Core.getManager().getMessage().staffMessage("&7Reason: &f" + message);
        Core.getManager().getMessage().staffMessage(" ");
        Core.getManager().getMessage().staffMessage("&7&m----------------------------------------");

        Bukkit.getServer().getBannedPlayers().add(target);

        UsersFiles uf = UsersFiles.get(target);

        uf.getFile().set("banned.enabled", true);
        uf.getFile().set("banned.reason", message);
        uf.getFile().set("banned.sender", sender.getName());*/

        return true;
    }
}
