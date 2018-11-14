package com.craftedsouls.utils.managers;

import com.craftedsouls.data.Settings;
import com.craftedsouls.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    Connection conn;

    public String USERNAME;
    public String PASSWORD;

    public void printSQLString(String uuid, String SQL) {
        Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(Prefix.SQL + SQL);

    }

    public void createUser(String uuid, String name, String rank, int bans, int warnings) {

       Player player = Bukkit.getPlayer(UUID.fromString(uuid));

       if(charExist(uuid)) {
           for(int i = 1; i <= 5; i++) {
               createChar(uuid, i, (int)player.getLocation().getX(),  (int)player.getLocation().getY(),
                       (int)player.getLocation().getZ(), player.getLocation().getWorld().getName(), false,  false);
           }
       }

        String query = "INSERT INTO USERS (UUID, USERNAME, CHARSLOT, RANKS, BANS, WARNINGS) VALUES ('" + uuid + "', '" + name + "', '1', '" + rank + "', '" + bans + "', '" + warnings + "');";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }

    public boolean userExist(String uuid) {
        String result = getUserData("UUID", uuid);
        return result == null;
    }

    public void setUserData(String uuid, String dataname, String value) {
        String query = "UPDATE USERS SET " + dataname + "='" + value +"' WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }

    public String getUserData(String dataname, String uuid) {
        String result = null;

        String query = "SELECT " + dataname + " FROM USERS WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        try {
            ResultSet rs = executeQuery(query);
            while (rs.next()) {
                result = rs.getString(dataname);
            }
            if(!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void deleteUserData(String uuid) {

        if(!charExist(uuid)) {
            for(int i = 1; i <= 5; i++) {
                deleteChar(uuid, i);
            }
        }


        String query = "DELETE FROM USERS WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }

    public ResultSet executeQuery(String query) {
        Statement st;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String query) {
        Statement st = null;
        int result = 0;
        try {
            st = conn.createStatement();
            result = st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Location getWarp(String name) {
        Location result = null;

        String query = "SELECT * FROM WARPS WHERE NAME='" + name + "';";

        int X = 0;
        int Y = 0;
        int Z = 0;
        float pitch = 0;
        float yaw = 0;
        World world = null;

        try {
            ResultSet rs = executeQuery(query);
            while (rs.next()) {
                X = rs.getInt("X");
                Y = rs.getInt("Y");
                Z = rs.getInt("Z");
                pitch  = rs.getFloat("PITCH");
                yaw = rs.getFloat("YAW");
                world = Bukkit.getWorld(rs.getString("WORLD"));


            }
            if(!rs.isClosed()) {
                rs.close();
            }
            if(world != null) {
                result = new Location(world, X, Y, Z, pitch, yaw);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Location setWarp(String name, int X, int Y, int Z, float pitch, float yaw, World world, Player player) {
        String worldName = world.getName();
        Location location = null;

        String query = "INSERT INTO WARPS (NAME, X, Y, Z, WORLD, PITCH, YAW) VALUES ('" + name + "', '" + X +
                "', '" + Y + "', '" + Z + "', '" + worldName + "', '" + pitch + "', '" + yaw + "');";

       //player.sendMessage(query);

        executeUpdate(query);

        return location;
    }


    public void deleteWarp(String name) {
        String query = "DELETE FROM WARPS WHERE NAME='" + name + "';";
        executeUpdate(query);
    }


    public void createTicket(int id, boolean accepted, String message, String author) {
        String query = "INSERT INTO TICKETS (ID, ACCEPTED, MESSAGE, AUTHOR) VALUES ('" + id + "', '" + accepted +
                "', '" + message + "', '" + author + "');";

        executeUpdate(query);
    }

    public void acceptTicket(int id) {
        String query = "UPDATE USERS SET ACCEPTED='True' WHERE ID='" + id + "';";

        executeUpdate(query);
    }


    public void deleteTicket(int id) {
        String query = "DELETE FROM USERS WHERE ID='" + id + "';";

        executeUpdate(query);
    }

    /*public void createItem(String name, Material material, int mindamage, int maxdamage, DamageType damageType, int level, short id, Player player) {
        String query = "INSERT INTO ITEMS (NAME, MATERIAL, MINDAMAGE, MAXDAMAGE, TYPE, LEVEL, ID) VALUES ('" + name + "', '" + material +
                "', '" + mindamage + "', '" + maxdamage + "', '" + damageType.toString() + "', '" + level + "', '" + id + "');";

        //printSQLString(player.getUniqueId().toString(), query);

        executeUpdate(query);
    }

    public ItemData getItem(short id) {

        String name = null;
        Material material = null;
        DamageData damageData = null;
        int level = 0;


        String query = "SELECT * FROM ITEMS WHERE ID='" + id + "';";

        try {
            ResultSet rs = executeQuery(query);

            while(rs.next()) {
                name = rs.getString("NAME");
                level = rs.getInt("LEVEL");
                material = Material.getMaterial(rs.getString("MATERIAL").toUpperCase());
                damageData = new DamageData(DamageType.valueOf(rs.getString("TYPE")), rs.getInt("MINDAMAGE"), rs.getInt("MAXDAMAGE"));
            }

            if(!rs.isClosed()) {
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Bukkit.broadcastMessage(name + "," + level + "," + material + "," + damageData);
        return new ItemData(material, name, id, damageData, level);
    }*/

    public void setDevelopment(boolean value) {
        String query = "UPDATE SETTINGS SET DEVELOPMENT='" + value + "'" +  "WHERE point=1;";
        executeUpdate(query);
    }

    public boolean getDevelopment() {
        boolean dev = false;

        String query = "SELECT DEVELOPMENT FROM SETTINGS WHERE point=1;";

        try {
            ResultSet rs = executeQuery(query);

            while(rs.next()) {
                dev = rs.getBoolean("DEVELOPMENT");
            }

            if(!rs.isClosed()) {
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dev;
    }

    public void setDebug(boolean value) {
        String query = "UPDATE SETTINGS SET DEBUG='" + value + "'" +  "WHERE point=1;";
        executeUpdate(query);
    }

    public void setMute(boolean value) {
        String query = "UPDATE SETTINGS SET MUTE='" + value + "'" +  "WHERE point=1;";
        executeUpdate(query);
    }

    public boolean getMute() {

        boolean mute = false;

        String query = "SELECT MUTE FROM SETTINGS WHERE point=1;";

        try {
            ResultSet rs = executeQuery(query);

            while(rs.next()) {
                mute = rs.getBoolean("Mute");
            }

            if(!rs.isClosed()) {
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mute;
    }

    public void createChar(String uuid, int slot, int x, int y, int z, String world, boolean filled, boolean locked) {
        String query = "INSERT INTO CHARACTERS (UUID, LEVEL, CHARSLOT, X, Y, Z, WORLD, FILLED, LOCKED) VALUES ('" + uuid + "', '0', '" + slot + "', '" +
                x + "', '" + y + "', '" + z + "', '" + world + "', '" + filled + "', '" + locked + "');";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }

    public String getCharData(String dataname, String uuid) {
        String result = null;

        String query = "SELECT " + dataname + " FROM CHARACTERS WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        try {
            ResultSet rs = executeQuery(query);
            while (rs.next()) {
                result = rs.getString(dataname);
            }
            if(!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean charExist(String uuid) {
        String result = getCharData("UUID", uuid);
        return result == null;
    }

    public void deleteChar(String uuid, int slot) {

        String query = "DELETE FROM CHARACTERS WHERE UUID='" + uuid + "' AND CHARSLOT='" + slot + "';";
        //printSQLString(uuid, query);
        executeUpdate(query);
    }


    public void setCharData(String uuid, String dataname, String value) {
        String query = "UPDATE CHARACTERS SET " + dataname + "='" + value +"' WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }

    public int getCurrentCharslot(String uuid) {
        int result = 0;


        String query = "SELECT CHARSLOT FROM USERS WHERE UUID='" + uuid + "';";
        //printSQLString(uuid, query);

        try {
            ResultSet rs = executeQuery(query);
            while (rs.next()) {
                result = rs.getInt("CHARSLOT");
            }
            if(!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getCharslotData(String uuid, int charslot, String dataname) {
        String result = "";


        String query = "SELECT " + dataname + " FROM CHARACTERS WHERE UUID='" + uuid + "' AND CHARSLOT='" + charslot + "';";
        //printSQLString(uuid, query);

        try {
            ResultSet rs = executeQuery(query);
            while (rs.next()) {
                result = rs.getString(dataname);
            }
            if(!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setCharslotData(String uuid, int id, String dataname, String value) {
        String query = "UPDATE CHARACTERS SET " + dataname + "='" + value + "' WHERE UUID='" + uuid + "' AND CHARSLOT='" + id + "';";
        //printSQLString(uuid, query);

        executeUpdate(query);
    }
    public String getCurrentCharslotData(String uuid, String dataname) {
        return getCharslotData(uuid, getCurrentCharslot(uuid), dataname);
    }


    public void setCurrentCharslotData(String uuid, String dataname, String value) {
        setCharslotData(uuid, getCurrentCharslot(uuid), dataname, value);
    }


    public void connect() {
        Settings settings = Settings.getInstance();

        USERNAME = "root";
        PASSWORD = "i18R7uty";

        try {
            if(!settings.getData().getBoolean("development")) {
                // Create MSQL Database Connection
                String myDriver = "com.mysql.jdbc.Driver";
                // String myUrl = "jdbc:mysql://localhost/soulsdev?autoReconnect=true&useSSL=false";
                String myUrl = "jdbc:mysql://172.93.54.217:3306/soulsdev?autoReconnect=true&useSSL=false";
                conn = DriverManager.getConnection(myUrl, USERNAME, PASSWORD);

                Class.forName(myDriver);
            } else {
                // Create MSQL Database Connection
                String myDriver = "com.mysql.jdbc.Driver";
                String myUrl = "jdbc:mysql://172.93.54.217:3306/development?autoReconnect=true&useSSL=false";
                conn = DriverManager.getConnection(myUrl, USERNAME, PASSWORD);
            }
        }
        catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
