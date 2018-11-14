package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LookupCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.lookup")) {
            api.getAPI().getMessages().noPermission("Skyplex", player);
            return true;
        }

        if(args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", player, "/lookup <user>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            OfflinePlayer offlineTarget = Bukkit.getPlayer(args[0]);
            if(UsersFiles.getOffline(Bukkit.getOfflinePlayer(args[0])).isNew()) {
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cThat player has never joined the server!");
                return true;
            }
            UsersFiles targetData = UsersFiles.getOffline(offlineTarget);
            api.getAPI().getMessages().sendMessage(player, "&7&m----------------------------------------");
            api.getAPI().getMessages().sendMessage(player, " ");
            api.getAPI().getMessages().sendMessage(player, "            &b&l" + offlineTarget.getName() + "&7's Data");
            api.getAPI().getMessages().sendMessage(player, " ");
            api.getAPI().getMessages().sendMessage(player, "            &6&lBasic Information");
            api.getAPI().getMessages().sendMessage(player, "&7Status: &cOffline");
            api.getAPI().getMessages().sendMessage(player, "&7UUID: &6" + offlineTarget.getUniqueId().toString());
            if(player.hasPermission("skyplex.lookup.ip")) {
                api.getAPI().getMessages().sendMessage(player, "&7IP: &6" + targetData.getFile().getString("ip"));
            } else {
                api.getAPI().getMessages().sendMessage(player, "&7IP: &6**.***.**.***");
            }
            api.getAPI().getMessages().sendMessage(player, "&7Rank: &6" + targetData.getFile().getString("rank"));
            api.getAPI().getMessages().sendMessage(player, "&7First Joined: &6" + api.getAPI().getDate().formatDateDiff(targetData.getFile().getLong("firstjoined")));
            api.getAPI().getMessages().sendMessage(player, " ");
            api.getAPI().getMessages().sendMessage(player, "&7&m----------------------------------------");
        }

        UsersFiles targetData = UsersFiles.get(target);
        api.getAPI().getMessages().sendMessage(player, "&7&m----------------------------------------");
        api.getAPI().getMessages().sendMessage(player, " ");
        api.getAPI().getMessages().sendMessage(player, "            &b&l" + target.getName() + "&7's Data");
        api.getAPI().getMessages().sendMessage(player, " ");
        api.getAPI().getMessages().sendMessage(player, "            &6&lBasic Information");
        api.getAPI().getMessages().sendMessage(player, "&7Status: &aOnline");
        api.getAPI().getMessages().sendMessage(player, "&7UUID: &6" + target.getUniqueId().toString());
        if(player.hasPermission("escape.ip")) {
            api.getAPI().getMessages().sendMessage(player, "&7IP: &6" + targetData.getFile().getString("ip"));
        } else {
            api.getAPI().getMessages().sendMessage(player, "&7IP: &6**.***.**.***");
        }
        api.getAPI().getMessages().sendMessage(player, "&7Rank: &6" + targetData.getFile().getString("rank"));
        api.getAPI().getMessages().sendMessage(player, "&7First Joined: &6" + api.getAPI().getDate().formatDateDiff(targetData.getFile().getLong("firstjoined")));
        api.getAPI().getMessages().sendMessage(player, " ");
        api.getAPI().getMessages().sendMessage(player, "&7&m----------------------------------------");

        return true;
    }
}
