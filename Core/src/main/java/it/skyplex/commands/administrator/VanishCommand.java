package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UsersFiles users = UsersFiles.get(player);

        if(!player.hasPermission("skyplex.vanish")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if(!users.getFile().getBoolean("vanished")) {
            users.getFile().set("vanished", true);
            users.saveFile();
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
            }
            api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have &aenabled &7vanish.");
            api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iVanished&7&i]");
            return true;
        }
        users.getFile().set("vanished", false);
        users.saveFile();

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(player);
        }
        api.getAPI().getMessages().sendMessage("Skyplex", player, "&7You have &cdisabled &7vanish.");
        api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iUnvanished chat&7&i]");
        return true;
    }
}
