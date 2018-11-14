package it.skyplex;

import it.skyplex.utilities.Date;
import it.skyplex.utilities.Messages;
import it.skyplex.utilities.Name;
import org.bukkit.Bukkit;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class APIManager {

    private Date date;
    private Messages messages;
    private Name name;

    public void enable() {
        date = new Date();
        messages = new Messages();
        name = new Name();
    }

    public void disable() {
        date = null;
        messages = null;
        name = null;
    }

    public Date getDate() {
        return date;
    }
    public Messages getMessages() {
        return messages;
    }
    public Name getName() {
        return name;
    }

    public void test() {
        String ip = Bukkit.getIp();
        System.out.println("IP of Server:" + ip);
    }
}
