package it.skyplex.commands.staff;

import it.skyplex.API;
import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.TimeZone;

public class MuteCommand implements CommandExecutor {
    API api = API.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("skyplex.mute")) {
            api.getAPI().getMessages().noPermission("Skyplex", sender);
            return false;
        }

        if(args.length == 0) {
            api.getAPI().getMessages().invalidUse("Skyplex", sender, "/mute <player> [time] [reason]");
            return false;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        UsersFiles users = UsersFiles.get(target);

        if(target == null) {
            api.getAPI().getMessages().sendMessage("Skyplex", sender, "&eError 404: &cPlayer not found.");
            return false;
        }

        if(users.isMuted()) {
            users.unmute();
            api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&iUnmuted " + users.getPlayer().getName() + "&7&i]");
            api.getAPI().getMessages().sendMessage("Skyplex", target, "&7You are no longer muted.");
            return true;
        }

        if(args.length < 3) {
           api.getAPI().getMessages().invalidUse("Skyplex", sender, "/mute <player> <time> <reason>");
            return false;
        }

        StringBuilder message = new StringBuilder("");
        for(int i = 2; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        String reason = message.toString().trim();
        api.getAPI().getMessages().sendStaffMessage("&7&i[" + sender.getName() + ": &e&i" + (args[1].equals("-") ? "muted" : "temp-muted") + " " + users.getPlayer().getName() + "&7&i]");
        api.getAPI().getMessages().sendMessage("Skyplex", target, "&7You have been muted for &6" + reason);

        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        long time = api.getAPI().getDate().parseDateDiff(args[1], true);

        users.mute(reason, (time <= 0 ? null : new Date(time)));
        users.saveFile();
        return true;
    }
}
