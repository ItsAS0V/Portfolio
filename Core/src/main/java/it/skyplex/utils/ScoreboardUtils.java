package it.skyplex.utils;

import it.skyplex.database.ServerFiles;
import it.skyplex.database.UsersFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {

    private Scoreboard scoreboard;
    private Objective objective;
    private Team team;

    ServerFiles files = ServerFiles.getInstance();

    public void setupTeamScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        if(scoreboard.getObjective("CoreTeams") == null) {
            objective = scoreboard.registerNewObjective("CoreTeams", "");
        } else {
            objective = scoreboard.getObjective("CoreTeams");
        }
    }

    public void setTeamScoreboard(Player player) {
        UsersFiles usersFiles = UsersFiles.get(player);
        String rank = usersFiles.getRank();

        Team team;

        if(scoreboard.getTeam(rank) == null) {
            team = scoreboard.registerNewTeam(rank);
            team.setColor(ChatColor.valueOf(files.getPermissions().getString("groups." + rank + ".color")));
        } else {
            team = scoreboard.getTeam(rank);
        }

        team.addPlayer(player);
    }
}
