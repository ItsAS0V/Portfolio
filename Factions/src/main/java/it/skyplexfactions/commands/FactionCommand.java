package it.skyplexfactions.commands;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import it.skyplexfactions.Factions;
import it.skyplexfactions.database.FactionsFiles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class FactionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Core.getManager().getMessage().sendConsoleMessage("You must be a player to run this command!");
            return true;
        }
        Player player = (Player) sender;
        UsersFiles usersFiles = UsersFiles.get(player);

        if(args.length == 0) {
            //TODO: Help Message
            Factions.getManager().getItemManager().giveAllReactors(player);
            /*EnderCrystal enderCrystal = (EnderCrystal) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_CRYSTAL);
            enderCrystal.setShowingBottom(false);
            Location obiLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ());

            obiLocation.getBlock().setType(Material.BEDROCK);
            return true;*/
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("leave")) {
                Factions.getManager().getFactionUtils().leaveFaction(player);
                return true;
            }
        }
        if(args.length == 2) {
            if(args[0] == null || args[1] == null) {
                //TODO: Help Message
                return true;
            }

            if(args[0].equalsIgnoreCase("create")) {
                Factions.getManager().getFactionUtils().createFaction(player, args[1]);
                return true;
            }
            else if(args[0].equalsIgnoreCase("delete")) {
                Factions.getManager().getFactionUtils().deleteFaction(player, args[1]);
                return true;
            }
            else if(args[0].equalsIgnoreCase("join")) {
                Factions.getManager().getFactionUtils().joinFaction(player, args[1]);
                return true;
            }
        }
        return true;
    }
}
