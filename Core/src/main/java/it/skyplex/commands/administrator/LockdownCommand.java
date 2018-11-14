package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockdownCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ServerFiles server = ServerFiles.getInstance();
        if(!sender.hasPermission("skyplex.lockdown")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        if (server.getData().getBoolean("lockdown")) {
            Bukkit.setWhitelist(false);
            server.getData().set("lockdown", false);
            server.saveData();
            api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iDisabled lockdown&7&i]");
            return true;
        } else {
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if(!online.hasPermission("skyplex.bypass")) {
                    online.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&c&lLOCKDOWN"
                    + "\n" + "\n" + "&7The server is currently in lockdown. Please check our &9Discord &7for more information regarding the incident."));
                }
                Bukkit.setWhitelist(true);
                server.getData().set("lockdown", true);
                server.saveData();
                api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iEnabled lockdown&7&i]");
            }
        }
        return true;
    }

}
