package com.craftedsouls.events;

import com.craftedsouls.CraftedSouls;
import com.craftedsouls.utils.Prefix;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;


public class EventPlayerMove implements Listener {

    //    --- w ---
    //    Pupper woof woof woof hard wood woof woof woof
    //    --- w ---

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(CraftedSouls.getCooldownManager().cooldownCharacterChange.containsKey(player.getUniqueId())) {
            if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                CraftedSouls.getCooldownManager().removeCharacterChangeCooldown(player.getUniqueId());
                CraftedSouls.getChatManager().sendChat(player, Prefix.GENERAL, "Your character change has been cancelled because you moved");
                return;
            }
        }

        if(CraftedSouls.getMainMenuManager().isInMainMenu(player)) {
            event.setTo(player.getLocation());
            return;
        }

        if(CraftedSouls.dead.containsKey(player.getUniqueId())) {

            if(event.getFrom().add(0, 2, 0).getBlock().getType().isSolid()) {
                player.setGameMode(GameMode.SPECTATOR);
            } else {
                player.setGameMode(GameMode.ADVENTURE);
            }
            player.setVelocity(new Vector(0, 0.35, 0));
        }
    }
}
