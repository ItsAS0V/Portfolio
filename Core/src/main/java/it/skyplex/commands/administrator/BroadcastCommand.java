package it.skyplex.commands.administrator;

import it.skyplex.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {
    private API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("skyplex.broadcast")) {
                return true;
            }
        }

        if (args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/broadcast [message]");
            return true;
        }

        StringBuilder reason = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        String message = reason.toString().trim();

        api.getAPI().getMessages().broadcast(message);

        return true;
    }
}
