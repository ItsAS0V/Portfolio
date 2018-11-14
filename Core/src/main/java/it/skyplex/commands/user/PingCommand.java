package it.skyplex.commands.user;

import it.skyplex.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        Random rand = new Random();

        int i = rand.nextInt(1 + 100) + 1;

        if (i <= 10) {
            Core.getManager().getMessage().sendMessage(sender, "&7You Scored!");
        } else if (i <= 21 && i > 10) {
            Core.getManager().getMessage().sendMessage(sender, "&7You Missed!");
        } else {
            Core.getManager().getMessage().sendMessage(sender, "&7Pong!");
        }

        return true;
    }
}
