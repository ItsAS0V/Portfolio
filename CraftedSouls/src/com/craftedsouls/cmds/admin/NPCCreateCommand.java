package com.craftedsouls.cmds.admin;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.NPCData;
import com.craftedsouls.utils.managers.NPCManager;
import com.craftedsouls.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }
        Player player = (Player) sender;
        NPCManager npcManager = CraftedSouls.getNPCManager();
        NPCData npcData = NPCData.getInstance();

        if(!player.hasPermission("cscore.npc")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        if(args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/npccreate <static|moving|banker> [name]");
            return true;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("banker")) {
                npcManager.spawnBanker(player.getLocation());
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have created a banker!");
                return true;
            } else {
                sender.sendMessage(CraftedSouls.getChatManager().incorrectUsage(player, "/npccreate <static|moving|banker> [name]"));
                return true;
            }
        } else {
            if (args[0].equalsIgnoreCase("static")) {

                if (npcData.npcFileExists(args[1])) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "There is already a NPC named " + args[1] + "!");
                    return true;
                }

                npcManager.spawnStaticNPC(args[1], player.getLocation());
                npcData.setup(args[1]);

                //Setup File
                npcData.get(args[1]).set("name", args[1]);
                npcData.save(args[1]);
                npcData.get(args[1]).set("static", true);
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.x", player.getLocation().getX());
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.y", player.getLocation().getY());
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.z", player.getLocation().getZ());
                npcData.save(args[1]);

                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have created a Static NPC named §a" + args[1] + "§7!");
                return true;
            } else if (args[0].equalsIgnoreCase("moving")) {

                if (npcData.npcFileExists(args[1])) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "There is already a NPC named " + args[1] + "!");
                    return true;
                }

                npcManager.spawnNPC(args[1], player.getLocation());
                npcData.setup(args[0]);

                //Setup File
                npcData.get(args[1]).set("name", args[1]);
                npcData.save(args[1]);
                npcData.get(args[1]).set("static", false);
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.x", player.getLocation().getX());
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.y", player.getLocation().getY());
                npcData.save(args[1]);
                npcData.get(args[1]).set("location.z", player.getLocation().getZ());
                npcData.save(args[1]);
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have created a NPC named §a" + args[1] + "§7!");
                return true;
            }
        }
        return true;
    }
}
