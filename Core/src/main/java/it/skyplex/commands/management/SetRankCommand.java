package it.skyplex.commands.management;

import it.skyplex.API;
import it.skyplex.Core;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import it.skyplex.database.UsersFiles;

import java.util.ArrayList;
import java.util.List;

public class SetRankCommand implements CommandExecutor, TabCompleter {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("skyplex.setrank"))) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }
        if(args.length < 2) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/setrank <player> <rank>");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        ServerFiles server = ServerFiles.getInstance();

        if (target == null) { return true; }

        if(!server.getPermissions().getConfigurationSection("groups").getKeys(false).contains(args[1])) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&eError 404: &cRank not found.");
            return true;
        }

        UsersFiles.get(target).setRank(args[1]);
        UsersFiles.get(target).saveFile();

        Core.getPermissionUtils().removeDefaultPermissions(target);
        Core.getPermissionUtils().addDefaultPermission(target);

        api.getAPI().getMessages().sendStaffMessage("STAFF", ChatColor.AQUA + target.getName() + " &7rank has been updated to &6" + args[1]);

        if(server.getPermissions().getBoolean("groups." + args[1] + ".op")) {
            target.setOp(true);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.setrank")) {
            return null;
        }

        ArrayList<String> toReturn = new ArrayList<>();

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("")) {
                for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                    toReturn.add(online.getName());
                }
            } else {
                for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if(online.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        toReturn.add(online.getName());
                    }
                }
            }
        }

        if(args.length == 2) {
            if(args[1].equalsIgnoreCase("")) {
                for(String rankData : ServerFiles.getInstance().getPermissions().getConfigurationSection("groups").getKeys(false)) {
                    toReturn.add(rankData.toLowerCase());
                }
            } else {
                for(String rankData : ServerFiles.getInstance().getPermissions().getConfigurationSection("groups").getKeys(false)) {
                    if(rankData.toLowerCase().startsWith(args[1].toLowerCase())) {
                        toReturn.add(rankData.toLowerCase());
                    }
                }
            }
        }
        return toReturn;
    }
}
