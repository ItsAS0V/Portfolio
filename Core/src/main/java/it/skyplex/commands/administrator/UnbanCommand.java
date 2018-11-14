package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.database.UsersFiles;
import it.skyplex.Core;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.unban")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        if(args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/unban <player>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!Bukkit.getServer().getBannedPlayers().contains(target)) {
            api.getAPI().getMessages().sendMessage("Slyplex", sender, "&cThat player is not banned!");
            return true;
        }

        UsersFiles uf = UsersFiles.getOffline(target);

        uf.getFile().set("banned.enabled", false);
        uf.getFile().set("banned.reason", "null");
        uf.getFile().set("banned.sender", "null");

        Bukkit.getServer().getBannedPlayers().remove(target);

        api.getAPI().getMessages().sendStaffMessage("STAFF", ChatColor.YELLOW + sender.getName() + " &7unbanned &b" + target.getName());

        return true;
    }
}
