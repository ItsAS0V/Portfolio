package com.craftedsouls.events;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class EventVehicleMove implements Listener {

    @EventHandler
    public void onVehicleMove(VehicleCreateEvent event) {
        Entity vehicle = event.getVehicle();
        Entity passenger = event.getVehicle().getPassenger();
        if(vehicle instanceof Boat) {
            if(passenger instanceof Player) {
                Boat boat = (Boat) vehicle;
                Player player = (Player) passenger;
                double newMaxSpeed = 10;
                boat.setMaxSpeed(newMaxSpeed);
                boat.setPassenger(player);
            }
        }
    }
}
