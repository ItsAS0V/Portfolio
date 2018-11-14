package it.skyplex.utils;

import it.skyplex.database.ServerFiles;
import it.skyplex.database.UsersFiles;
import it.skyplex.Core;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.util.ArrayList;

public class PermissionUtils {
    private Core core = Core.getInstance();

    /**
     * Add permissions to the given user.
     * @param player
     */
    public void addDefaultPermission(Player player) {
        File folder = new File(core.getDataFolder() + File.separator + "users" + File.separator);
        boolean found = false;
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.getName().substring(0, file.getName().length() - 4).equals(player.getUniqueId().toString())) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            return;
        }
        if(Core.PERMISSIONS.get(player.getUniqueId().toString()) == null) {
            Core.PERMISSIONS.put(player.getUniqueId().toString(), player.addAttachment(core));
        }

        UsersFiles user = UsersFiles.get(player);
        ServerFiles server = ServerFiles.getInstance();
        String rank = user.getRank();
        PermissionAttachment permissions = Core.PERMISSIONS.get(player.getUniqueId().toString());

        try {
            ArrayList<String> addGroupPerms = (ArrayList<String>) server.getPermissions().getConfigurationSection("groups").getStringList(rank + ".permissions");
            //ArrayList<String> addUserPerms = (ArrayList<String>) server.getPermissions().getConfigurationSection("players").getStringList(player.getUniqueId().toString() + ".permissions");

            for(String perm : addGroupPerms) {
                if(perm == null) {
                   return;
                }
                permissions.setPermission(perm, true);
            }

            /*for(String perm : addUserPerms) {
                if(perm == null) {
                    return;
                }
                permissions.setPermission(perm, true);
            }*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove permissions from the given user.
     * @param player
     */
    public void removeDefaultPermissions(Player player) {
        PermissionAttachment permissions = Core.PERMISSIONS.get(player.getUniqueId().toString());

        if(permissions == null) {
            return;
        }
        try {
            player.removeAttachment(permissions);
            Core.PERMISSIONS.put(player.getUniqueId().toString(), null);
        } catch (NullPointerException e) {}
    }
}
