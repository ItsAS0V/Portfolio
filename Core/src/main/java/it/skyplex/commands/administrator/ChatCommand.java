package it.skyplex.commands.administrator;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.ServerFiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    API api = API.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.chat.manage")) {
            api.getAPI().getMessages().noPermission("Core", sender);
            return false;
        }
        if(args.length == 0) {
            api.getAPI().getMessages().invalidUse("Chat", sender, "/chat [mute|clear]");
            return false;
        }
        ServerFiles server = ServerFiles.getInstance();
        if(args[0].equalsIgnoreCase("mute")) {
            if(!server.getData().getBoolean("chat.muted")) {
                server.getData().set("chat.muted", true);
                server.saveData();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    api.getAPI().getMessages().sendMessage("Skyplex", online, "&c&lThe chat has been muted.");
                    api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iMuted chat&7&i]");
                }
                return true;
            } else if(server.getData().getBoolean("chat.muted")){
                server.getData().set("chat.muted", false);
                server.saveData();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    api.getAPI().getMessages().sendMessage("Skyplex", online, "&c&lThe chat has been unmuted.");
                    api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iUnmuted chat&7&i]");
                }
            }
        } else if(args[0].equalsIgnoreCase("clear")) {
            for(int i = 0; i < 200; i++) {
                Bukkit.broadcastMessage(" ");
            }
            for(Player online : Bukkit.getOnlinePlayers()) {
                api.getAPI().getMessages().sendMessage("Skyplex", online, "&c&lThe chat has been cleared.");
                api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iCleared chat&7&i]");
            }
        }
        return true;
    }
}
