package it.skyplex.commands.user;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
           api.getAPI().getMessages().sendConsoleMessage("&cYou must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UsersFiles usersFiles = UsersFiles.get(player);

        api.getAPI().getMessages().sendMessage("Skyplex", player, "&aBalance: &f" + usersFiles.getBalance());
        return true;
    }
}
