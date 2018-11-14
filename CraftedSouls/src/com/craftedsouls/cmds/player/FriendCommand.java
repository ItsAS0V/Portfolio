package com.craftedsouls.cmds.player;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


public class FriendCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            CraftedSouls.getChatManager().sendConsoleError(sender);
            return true;
        }

        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        UserData userData = UserData.getInstance();
        List<String> playerFriend = userData.get(uuid).getStringList("Friends");
        List<String> playerSent = userData.get(uuid).getStringList("SentFriends");

        if (args.length == 0) {
            CraftedSouls.getChatManager().incorrectUsage(player, "/friend <list|pending|add|remove|accept|deny>");
            return true;
        }

        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("list")) {
                if (playerFriend.isEmpty()) {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bFriends§8 ========");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cYou don't have any friends :(");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cUse /friend add <player>");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bFriends§8 ========");
                    return true;
                }

                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bFriends§8 ========");
                String output = "";

                if (!playerFriend.isEmpty()) {
                    for (String key : playerFriend) {
                        String friend = Bukkit.getPlayer(UUID.fromString(key)).getName();
                        if (friend == null) {
                            output += "§7, " + "§c" + friend;
                        } else {
                            output += "§7, " + "§a" + friend;
                        }
                    }
                }
                output = output.replaceFirst(",", " ");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, output);
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bFriends§8 ========");
            } else if(args[0].equalsIgnoreCase("pending")) {
                List<String> pendingFriend = userData.get(uuid).getStringList("PendingFriends");
                if (pendingFriend.isEmpty() && playerSent.isEmpty()) {
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bPending Friends§8 ========");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cYou don't have any pending or sent friends :(");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§cUse /friend add <player> to send a friend request.");
                    CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bPending Friends§8 ========");
                    return true;
                }

                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bPending Friends§8 ========");
                String output = "";

                if (!pendingFriend.isEmpty()) {
                    for (String key : pendingFriend) {
                        String friend = Bukkit.getPlayer(UUID.fromString(key)).getName();
                        output += "§7, §fIncoming: §7" + "§a" + friend;
                    }
                }

                if (!playerSent.isEmpty()) {
                    for (String key : playerSent) {
                        String friend = Bukkit.getPlayer(UUID.fromString(key)).getName();
                        output += "§7, §fSent: §7" + "§e" + friend;
                    }
                }


                output = output.replaceFirst(",", " ");
                CraftedSouls.getChatManager().sendNoPrefixChat(player, output);
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §bPending Friends§8 ========");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player isn't online!");
                    return true;
                }
                String targetUUID = target.getUniqueId().toString();
                List<String> pendingFriend = userData.get(targetUUID).getStringList("PendingFriends");

                if (playerFriend.contains(target.getUniqueId().toString())) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You already have that player as a friend!");
                    return true;
                }

                //This stops the person receiving
                List<String> pendingFriend2 = userData.get(uuid).getStringList("PendingFriends");
                if(pendingFriend2.contains(target.getUniqueId().toString())) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Use /f accept [user] to accept them!");
                    return true;
                }

                //This stops the person sending
                List<String> pendingFriend3 = userData.get(targetUUID).getStringList("PendingFriends");
                if(pendingFriend3.contains(player.getUniqueId().toString())) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You've already sent a friend request!");
                    return true;
                }



                if (targetUUID.equals(uuid)) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Awh, you're not that lonely! You cannot friend yourself...");
                    return true;
                }

                pendingFriend.add(player.getUniqueId().toString());

                userData.get(targetUUID).set("PendingFriends", pendingFriend);
                userData.save(targetUUID);

                playerSent.add(target.getUniqueId().toString());
                userData.get(uuid).set("SentFriends", playerSent);
                userData.save(uuid);

                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Sent a friend request to " + target.getName() + "!");
                CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, player.getName() + " wants to be your friend! Use /friend accept [username], to accept them!");

            } else if (args[0].equalsIgnoreCase("remove")) {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player isn't online!");
                    return true;
                }
                String targetUUID = target.getUniqueId().toString();
                List<String> targetFriend = userData.get(targetUUID).getStringList("Friends");

                if (!playerFriend.contains(target.getUniqueId().toString())) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You don't have that player as a friend!");
                    return true;
                }

                playerFriend.remove(target.getUniqueId().toString());

                userData.get(uuid).set("Friends", playerFriend);
                userData.save(uuid);

                targetFriend.remove(player.getUniqueId().toString());

                userData.get(targetUUID).set("Friends", targetFriend);
                userData.save(targetUUID);

                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Removed " + target.getName() + " from your /friend list!");
                CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, player.getName() + " removed you from their /friend list!");

            } else if(args[0].equalsIgnoreCase("accept")) {
                Player target = Bukkit.getServer().getPlayer(args[1]); //Player who sent the request
                String targetUUID = target.getUniqueId().toString();

                List<String> pendingFriend = userData.get(uuid).getStringList("PendingFriends"); //Player who sent request's pending list
                List<String> targetFriend = userData.get(targetUUID).getStringList("Friends"); //Player who sent request friend list

                if(!pendingFriend.contains(target.getUniqueId().toString())) { //Checking to see if the player sent a request
                    //v
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player isn't in your pending friends!");
                    return true;
                }

                playerFriend.add(target.getUniqueId().toString());
                userData.get(uuid).set("Friends", playerFriend);
                userData.save(uuid);

                pendingFriend.remove(target.getUniqueId().toString());
                userData.get(uuid).set("PendingFriends", pendingFriend);
                userData.save(uuid);

                playerSent.remove(player.getUniqueId().toString());
                userData.get(targetUUID).set("SentFriends", playerSent);
                userData.save(targetUUID);

                targetFriend.add(player.getUniqueId().toString());
                userData.get(targetUUID).set("Friends", targetFriend);
                userData.save(targetUUID);


                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have accepted the friend request");
                CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, player.getName() + " is now your friend! View them in /friend list");
            } else if(args[0].equalsIgnoreCase("deny")) {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                String targetUUID = target.getUniqueId().toString();
                List<String> pendingFriend = userData.get(uuid).getStringList("PendingFriends");

                if (!pendingFriend.contains(target.getUniqueId().toString())) {
                    CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "That player isn't in your pending friends!");
                    return true;
                }

                pendingFriend.remove(target.getUniqueId().toString());
                userData.save(targetUUID);

                playerSent.remove(player.getUniqueId().toString());
                userData.get(targetUUID).set("SentFriends", playerSent);
                userData.save(targetUUID);

                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "You have denied the friend request");
                CraftedSouls.getChatManager().sendChat(target, Prefix.GENERAL, player.getName() + " has denied your friend request");
            }
        }
        return true;
    }
}