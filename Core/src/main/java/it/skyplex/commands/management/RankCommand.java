package it.skyplex.commands.management;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RankCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("skyplex.ranks")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        if(args.length == 0 || args.length > 3) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/ranks <create|add|remove|prefix|list|reload> <rank> <perm|prefix>");
            return true;
        }
        ServerFiles server = ServerFiles.getInstance();

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("list")) {
                api.getAPI().getMessages().sendMessage(sender, "&7Ranks: &6" + "\n" +
                        ServerFiles.getInstance().getPermissions().getConfigurationSection("groups").getKeys(false).toString()
                        .replace("[", "")
                        .replace(",", "\n")
                        .replace("]", "")
                        .replace(" ", ""));
            } else if(args[0].equalsIgnoreCase("reload")) {
                ServerFiles.getInstance().reloadPermissions();
            } else {
                api.getAPI().getMessages().invalidUse("Skyplex", sender, "/ranks <create|add|remove|prefix|list|reload> <rank> <perm|prefix>");
            }
            return true;
        } else if(args.length == 2) {
            String rank = args[1];
            if(args[0].equalsIgnoreCase("create")) {
                if(server.getPermissions().contains(rank)) {
                    api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cThe rank &f" + rank + " &calready exists!");
                    return true;
                }
                server.getPermissions().set("groups." + rank + ".prefix", "");
                server.getPermissions().set("groups." + rank + ".tab-prefix", "");
                server.getPermissions().set("groups." + rank + ".permissions", Arrays.asList(" "));
                server.savePermissions();
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7You have successfully created the rank &6" + rank);
                return true;
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(rank == null) {
                    api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cThe rank &f" + rank + " &cdoesn't exist!");
                    return true;
                }
                server.getPermissions().set("groups." + rank, null);
                server.savePermissions();
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7You have successfully removed the rank &6" + rank);
            } else {
                api.getAPI().getMessages().invalidUse("Skyplex", sender, "/ranks <create|add|remove|prefix|list|reload> <rank> <perm|prefix>");
            }
            return true;
        } else if(args.length == 3) {
            String rank = args[1];
            if(args[0].equalsIgnoreCase("prefix")) {
                String prefix = args[2];
                if(!server.getPermissions().getConfigurationSection("groups").contains(rank)) {
                    api.getAPI().getMessages().sendMessage("Skyplex", sender, "&eError 404: &cRank not found.");
                    return true;
                }
                server.getPermissions().set("groups." + rank + ".prefix", prefix.replace("_", " "));
                server.savePermissions();
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7You have successfully updated the rank &6" + rank);
            } else if(args[0].equalsIgnoreCase("add")) {
                String perm = args[2];
                List<String> permissions = server.getPermissions().getStringList("groups." + rank + ".permissions");
                permissions.add(perm);
                server.getPermissions().set("groups." + rank + ".permissions", permissions);
                server.savePermissions();
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7You have successfully updated the rank &6" + rank);
            } else if(args[0].equalsIgnoreCase("remove")) {
                String perm = args[2];
                List<String> permissions = server.getPermissions().getStringList("groups." + rank + ".permissions");
                permissions.remove(perm);
                server.getPermissions().set("groups." + rank + ".permissions", permissions);
                server.savePermissions();
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7You have successfully updated the rank &6" + rank);
            } else {
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "/ranks <create|add|remove|prefix|list|reload> <rank> <perm|prefix>");
            }
        } else {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "/ranks <create|add|remove|prefix|list|reload> <rank> <perm|prefix>");
        }
        return true;
    }
}
