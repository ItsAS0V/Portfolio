package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.economy")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return true;
        }

        // /economy [set|give|remove] [user] [amount]
        if(args.length < 3) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/economy <set|remove|give> <player> <amount>");
            return true;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception e) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cYou must enter a valid amount!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if(target == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cThat player isn't online!");
            return true;
        }
        UsersFiles usersFiles = UsersFiles.get(target);

        if(args[0].equalsIgnoreCase("set")) {
            usersFiles.setBalance(amount);
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Successfully set &b" + target.getName() + " &7's balance to &7$&b" + amount);
            api.getAPI().getMessages().sendMessage("Skyplex", target, "&7Your balance has been set to &7$&b" + amount);
            return true;
        }
        else if(args[0].equalsIgnoreCase("give")) {
            usersFiles.setBalance(usersFiles.getBalance() + amount);
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Successfully gave &7$&b" + amount + " &7to &b" + target.getName());
            api.getAPI().getMessages().sendMessage("Skyplex", target, "&7Your balance had been set to &7$&b" + amount);
        }
        else if(args[0].equalsIgnoreCase("remove")) {
            if(usersFiles.getBalance() - amount < 0) {
                api.getAPI().getMessages().sendMessage("Skyplex", sender, "&cCan't set the target's balance lower than &7$&b0");
                return true;
            }
            usersFiles.setBalance(usersFiles.getBalance() - amount);
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&7Successfully removed &7$&b" + amount + " &7from &b" + target.getName());
            api.getAPI().getMessages().sendMessage("Skyplex", target, "&7Your balance has been set to &7$&b" + amount);
        }
        return true;
    }
}
