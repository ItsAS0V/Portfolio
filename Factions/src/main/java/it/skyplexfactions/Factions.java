package it.skyplexfactions;

import it.skyplexfactions.commands.FactionCommand;
import it.skyplexfactions.database.ClaimsFile;
import it.skyplexfactions.listeners.EventInventoryClick;
import it.skyplexfactions.listeners.EventPlayerBuild;
import it.skyplexfactions.listeners.EventPlayerInteractEntity;
import it.skyplexfactions.listeners.EventPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Factions extends JavaPlugin {
    private static Factions instance;
    private static Manager manager;

    ClaimsFile claimsFile = ClaimsFile.getInstance();

    @Override
    public void onEnable() {
        setInstance(this);
        manager = new Manager();
        manager.register();
        claimsFile.setup();

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new EventPlayerInteractEntity(), this);
        pluginManager.registerEvents(new EventPlayerBuild(), this);
        pluginManager.registerEvents(new EventPlayerJoin(), this);
        pluginManager.registerEvents(new EventInventoryClick(), this);

        getCommand("faction").setExecutor(new FactionCommand());

        //manager.getCraftingManager().addRecipes();

        if(Bukkit.getOnlinePlayers().size() > 0) {
            for(Player online : Bukkit.getOnlinePlayers()) {
                manager.getFactionUtils().refreshScoreboard(online);
            }
        }
    }

    @Override
    public void onDisable() {

        manager.unregister();

        setInstance(null);
        manager = null;
    }

    public static Manager getManager() {
        return manager;
    }

    public static Factions getInstance() {
        return instance;
    }
    private void setInstance(Factions instance) { this.instance = instance; }
}
