package it.skyplex.commands.user;

import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class RulesCommand implements CommandExecutor {
    ServerFiles serverFiles = ServerFiles.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("&4Only a player can use this command!");
            return true;
        }
        Player player = (Player) sender;

        FileConfiguration rules = serverFiles.getRules();

        List<String> ruleList = rules.getStringList("rules");

        if(ruleList.isEmpty() || ruleList == null) {
            Core.getManager().getMessage().sendMessage(player, "&cThere are no rules set yet.");
            return true;
        }

        Core.getManager().getMessage().sendMessage(player, "&6Rules:");
        Core.getManager().getMessage().sendMessage(player, ruleList.toString().replace("[", "").replace("]", "").replace(",", "\n"));

        return true;
    }
}
