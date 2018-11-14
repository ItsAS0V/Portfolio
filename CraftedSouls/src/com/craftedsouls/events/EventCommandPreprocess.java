package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.data.Settings;
import com.craftedsouls.data.UserData;
import com.craftedsouls.utils.Prefix;
import com.craftedsouls.utils.managers.DatabaseManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class EventCommandPreprocess implements Listener {


    static HashMap<String, String> helpMessages = new HashMap<String, String>();

    public static void init() {
        helpMessages.put(getFormat("Help", "§f/help") ,"Use this command to bring up a help menu");
        helpMessages.put(getFormat("Message", "§f/message <player> <msg>"), "Use this command to privately message a target player");
        helpMessages.put(getFormat("List", "§f/list"), "Use this command to show players online");
        helpMessages.put(getFormat("Ticket", "§f/ticketcreate <msg>"), "Use this command to submit a ticket to the moderators");
    }


    //Do some colors
    private static String getFormat(String name, String use) {
        return "" + name + ": " + use;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {


        String message = event.getMessage();
        Settings settings = Settings.getInstance();
        Player player = event.getPlayer();
        Date time = Calendar.getInstance().getTime();
        UserData userData = UserData.getInstance();
        DatabaseManager databaseManager = CraftedSouls.getDatabaseManager();

        if(!message.contains("/development") || !message.contains("/dev")) {
            if (CraftedSouls.getMainMenuManager().isInMainMenu(player) && databaseManager.getDevelopment()) {
                CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "You cannot type in the main menu, select a character first");
                event.setCancelled(true);
                return;
            }
        }


        if(!settings.getData().getBoolean("Development")) {
            if (Objects.equals(message, "/help")) {
                event.setCancelled(true);

                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fHelp§8 ========");
                for (String helpMessage : helpMessages.keySet()) {
                    String description = helpMessages.get(helpMessage);

                    TextComponent c = new TextComponent("§7" + helpMessage);
                    c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(description).create()));
                    player.spigot().sendMessage(c);

                }
                CraftedSouls.getChatManager().sendNoPrefixChat(player, "§8======= §fHelp§8 ========");

                return;
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(settings.getLog(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[CMD]" + "[" + time + "] " + player + ": " + message);
            bufferedWriter.newLine();
            fileWriter.flush();
            bufferedWriter.close();
            settings.saveLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            String oUUID = online.getUniqueId().toString();
            if (userData.get(oUUID).getBoolean("Logging")) {
                CraftedSouls.getChatManager().sendChat(online, Prefix.LOGGING, player.getName() + "§8: §7" + message);
            }
        }


        //Custom unknown command
        String cmd = event.getMessage().split(" ")[0];
        HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
        if (topic == null) {
            if(cmd.startsWith("//") || cmd.contains("/stop")) {
                return;
            }
            CraftedSouls.getChatManager().sendChat(player, Prefix.ALERT, "Unknown command");
            event.setCancelled(true);
        }
    }
}
