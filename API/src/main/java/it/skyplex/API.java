package it.skyplex;

import org.bukkit.plugin.java.JavaPlugin;

public class API extends JavaPlugin {
    private static API instance;

    public static API getInstance() { return instance; }
    private void setInstance(API instance) { this.instance = instance; }

    private APIManager manager;

    @Override
    public void onEnable() {
        setInstance(this);
        manager = new APIManager();

        manager.enable();

        manager.test();
    }

    @Override
    public void onDisable() {
        manager.disable();
        setInstance(null);
        manager = null;
    }

    public APIManager getAPI() { return manager; }
}
