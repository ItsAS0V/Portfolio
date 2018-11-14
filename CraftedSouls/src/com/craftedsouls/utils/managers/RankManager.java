package com.craftedsouls.utils.managers;


import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;


public class RankManager {

    public String getRank(String rank, ChatColor color) {
        String rankString = color + "" + rank;
        return rankString;
    }

    public TextComponent getComponent(String channel, String channelPrefix, String baseString, String userName, String rankName,String levelColor, int level, String guildName) {
        TextComponent componentPrefix = new TextComponent(channelPrefix);
        componentPrefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6§oCraftedSouls §8- §f" + rankName + "\n" + "§b" + userName + "\n" + "\n" + "§7Level: §f" + levelColor + level + "\n" + "§7Guild: §f" + guildName).create()));
        TextComponent componentFinal = new TextComponent(baseString);
        componentPrefix.addExtra(componentFinal);
        return componentPrefix;

        //"§8" + channel + " §8(§f" + userName + "§8)" + "\n" + "§7Rank §8(§c" + rankName + "§8)"

        //CraftedSouls - (Their rank based on permission)
    }

}
