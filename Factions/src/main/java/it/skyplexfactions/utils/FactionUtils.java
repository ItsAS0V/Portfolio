package it.skyplexfactions.utils;

import it.skyplex.Core;
import it.skyplex.database.UsersFiles;
import it.skyplexfactions.Factions;
import it.skyplexfactions.database.ClaimsFile;
import it.skyplexfactions.database.FactionsFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.List;

public class FactionUtils {

    private Factions factions = Factions.getInstance();

    /**
     * Faction Utils
     */
    public boolean factionExists(String faction) {
        File factionFile = new File(factions.getDataFolder() + "/factions/" + faction + ".yml");

        if(factionFile.exists()) {
            return true;
        }

        return false;
    }
    public void createFaction(Player player, String faction) {
        UsersFiles usersFiles = UsersFiles.get(player);
        if(factionExists(faction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction already exists!");
            return;
        }
        if(!usersFiles.getFaction().equalsIgnoreCase("Wilderness")) {
            Core.getManager().getMessage().sendMessage(player, "&cYou are already in a faction!");
            return;
        }

        FactionsFiles factionsFiles = FactionsFiles.get(faction);
        List<String> members = factionsFiles.getFile().getStringList("members");
        members.add(player.getName());

        factionsFiles.getFile().set("members", members);
        factionsFiles.getFile().set("leader", player.getName());
        factionsFiles.saveFile();
        usersFiles.setFaction(faction);
        usersFiles.saveFile();

        Core.getManager().getMessage().sendMessage(player, "&7You have created a new faction called &b" + faction + "&7! Please review &c/help factions &7if you need help");
        Core.getManager().getMessage().serverBroadcast("&7There is a new faction in town called &b" + faction);
    }

    //TODO: Add permission, Set members to 'Wilderness'
    public void deleteFaction(Player player, String faction) {
        File factionFile = new File(factions.getDataFolder() + "/factions/" + faction + ".yml");
        UsersFiles usersFiles = UsersFiles.get(player);

        if(!factionExists(faction) || factionFile == null) {
            Core.getManager().getMessage().sendMessage(player, "&cYou aren't in a faction!");
            return;
        }

        factionFile.delete();
        usersFiles.setFaction("Wilderness");
        usersFiles.saveFile();
        Core.getManager().getMessage().serverBroadcast("&7The faction &b" + faction + " &7is no longer with us.");

    }
    public void sendFactionMessage(String faction, String message) {
        FactionsFiles factionsFiles = FactionsFiles.get(faction);
        List<String> members = factionsFiles.getFile().getStringList("members");

        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            if(online.getUniqueId().toString().equalsIgnoreCase(members.toString())) {
                Core.getManager().getMessage().sendMessage(online, message);
            }
        }
    }

    public boolean isAlly(String curFaction, String allyFaction) {
        FactionsFiles factionsFiles = FactionsFiles.get(curFaction);
        if(factionsFiles.getFile().getStringList("allies").contains(allyFaction)) {
            return true;
        }
        return false;
    }
    //TODO: Add permission
    public void setAlly(Player player, String curFaction, String allyFaction) {
        FactionsFiles factionsFiles = FactionsFiles.get(curFaction);

        if(factionExists(allyFaction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction doesn't exist!");
            return;
        }

        if(isAlly(curFaction, allyFaction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction is already an ally!");
            return;
        }

        List<String> allies = factionsFiles.getFile().getStringList("allies");
        allies.add(allyFaction);
        factionsFiles.getFile().set("allies", allies);
        factionsFiles.saveFile();

        sendFactionMessage(curFaction, "&5+ &7Allied faction &a" + allyFaction);
    }
    public void removeAlly(Player player, String curFaction, String allyFaction) {
        FactionsFiles factionsFiles = FactionsFiles.get(curFaction);

        if(factionExists(allyFaction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction doesn't exist!");
            return;
        }

        if(!isAlly(curFaction, allyFaction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction is isn't an ally!");
            return;
        }

        List<String> allies = factionsFiles.getFile().getStringList("allies");
        allies.remove(allyFaction);
        factionsFiles.getFile().set("allies", allies);
        factionsFiles.saveFile();

        sendFactionMessage(curFaction, "&d- &7Removed faction &a" + allyFaction);
    }

    /**
     * Land Claim Utils
     */
    private boolean checkLandClaim(Player player) {
        ClaimsFile claimsFile = ClaimsFile.getInstance();
        Chunk currChunk = player.getLocation().getChunk();
        String xzChunk = currChunk.getX() + "," + currChunk.getZ();

        for(String key : claimsFile.getClaims().getKeys(true)) {
            if(claimsFile.getClaims().getStringList(key).contains(xzChunk)) {
                Core.getManager().getMessage().sendMessage(player, "&cThis land is already claimed!");
                return true;
            }
        }
        return false;
    }
    public boolean checkLandBuild(Player player) {
        UsersFiles usersFiles = UsersFiles.get(player);
        ClaimsFile claimsFile = ClaimsFile.getInstance();
        Chunk currChunk = player.getLocation().getChunk();
        String xzChunk = currChunk.getX() + "," + currChunk.getZ();

        for(String key : claimsFile.getClaims().getKeys(true)) {
            if(claimsFile.getClaims().getStringList(key).contains(xzChunk) && !claimsFile.getClaims().getStringList(usersFiles.getFaction()).contains(xzChunk)) {
                Core.getManager().getMessage().sendMessage(player, "&cYou cannot build in &7" + key + "'s &cland!");
                return false;
            }
        }

        return true;
    }

    public void claimLand(Player player, String faction) {
        UsersFiles usersFiles = UsersFiles.get(player);
        FactionsFiles factionsFiles = FactionsFiles.get(usersFiles.getFaction());
        ClaimsFile claimsFile = ClaimsFile.getInstance();
        Chunk currChunk = player.getLocation().getChunk();

        if(usersFiles.getFaction().equalsIgnoreCase("Wilderness")) {
            Core.getManager().getMessage().sendMessage(player, "&cYou can't claim land for &2Wilderness&c!");
            return;
        }

        if(factionsFiles.getPower() < 20) {
            Core.getManager().getMessage().sendMessage(player, "&cYou don't have enough power to claim land!");
            return;
        }
        if(checkLandClaim(player)) return;

        String xzChunk = currChunk.getX() + "," + currChunk.getZ();
        List<String> factClaims = claimsFile.getClaims().getStringList(faction);

        factClaims.add(xzChunk);
        claimsFile.getClaims().set(faction, factClaims);
        factionsFiles.setPower(factionsFiles.getPower() - 10);
        Core.getManager().getMessage().sendMessage(player, "&aClaimed!");

        claimsFile.saveClaims();
        factionsFiles.saveFile();
    }

       /**
     * User Utils
     */
    public boolean playerInFaction(Player player) {
        UsersFiles usersFiles = UsersFiles.get(player);
        if(!usersFiles.getFaction().equalsIgnoreCase("Wilderness")) {
            return true;
        }
        return false;
    }

    public void joinFaction(Player player, String faction) {
        UsersFiles usersFiles = UsersFiles.get(player);

        if(!factionExists(faction)) {
            Core.getManager().getMessage().sendMessage(player, "&cThat faction doesn't exist!");
            return;
        }
        if(playerInFaction(player)) {
            Core.getManager().getMessage().sendMessage(player, "&cYou are already in a faction! You must leave the one you are currently in to join a new one.");
            return;
        }
        FactionsFiles factionsFiles = FactionsFiles.get(faction);

        usersFiles.setFaction(faction);
        List<String> members = factionsFiles.getFile().getStringList("members");
        members.add(player.getUniqueId().toString());
        factionsFiles.getFile().set("members", members);
        factionsFiles.saveFile();

        sendFactionMessage(faction, "&a+ &b" + player.getName() + " &7joined the faction.");
    }
    public void leaveFaction(Player player) {
        UsersFiles usersFiles = UsersFiles.get(player);

        if(!playerInFaction(player)) {
            Core.getManager().getMessage().sendMessage(player, "&cYou aren't in a faction!");
            return;
        }
        FactionsFiles factionsFiles = FactionsFiles.get(usersFiles.getFaction());

        if(factionsFiles.getFile().getStringList("members").size() == 1) {
            deleteFaction(player, usersFiles.getFaction());
            return;
        }

        sendFactionMessage(usersFiles.getFaction(), "&c- &b" + player.getName() + " &7left the faction.");

        usersFiles.setFaction("Wilderness");
        List<String> members = factionsFiles.getFile().getStringList("members");
        members.remove(player.getUniqueId().toString());
        factionsFiles.getFile().set("members", members);
        factionsFiles.saveFile();
    }

    public void setScoreboard(Player player) {
        UsersFiles usersFiles = UsersFiles.get(player);
        FactionsFiles factionsFiles = FactionsFiles.get(usersFiles.getFaction());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Factions", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lSky&7&lfactions"));

        Score divider1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&7&m---------------"));
        Score divider2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&7&m----------------"));
        Score blank = objective.getScore(" ");

        Score website = objective.getScore(ChatColor.GRAY + "skyplexhub.com");

        Score factionName = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&3&l" + usersFiles.getFaction() + " &8»"));
        Score factionLeader = objective.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &bLeader&7: &f" + factionsFiles.getFile().getString("leader")));
        Score factionMemberCount = objective.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &bMembers&7: &f" + factionsFiles.getFile().getStringList("members").size() + "&7/&f20"));
        Score factionPower = objective.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &bPower&7: &f" + factionsFiles.getFile().getInt("power")));

        Score userName = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&3&l" + player.getName() + " &8»"));
        Score userRank = objective.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &bRank&7: &f" + usersFiles.getRank()));
        Score userBalance = objective.getScore(ChatColor.translateAlternateColorCodes('&', " &8» &bBalance&7: &f$1000"));

        if(!usersFiles.getFaction().equalsIgnoreCase("Wilderness")) {
            divider1.setScore(11);
            factionName.setScore(10);
            factionLeader.setScore(9);
            factionMemberCount.setScore(8);
            factionPower.setScore(7);
            blank.setScore(5);
            userName.setScore(5);
            userRank.setScore(4);
            userBalance.setScore(3);
            divider2.setScore(2);
            website.setScore(1);
        } else {
            divider1.setScore(6);
            userName.setScore(5);
            userRank.setScore(4);
            userBalance.setScore(3);
            divider2.setScore(2);
            website.setScore(1);
        }

        player.setScoreboard(scoreboard);

    }
    public void refreshScoreboard(Player player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(factions, new Runnable() {
            @Override
            public void run() {
                setScoreboard(player);
            }
        }, 20, 20 * 5);
    }

    /**
     * Nexus Utils
     */
}
