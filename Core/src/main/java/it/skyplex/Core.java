package it.skyplex;

import it.skyplex.commands.management.*;
import it.skyplex.commands.administrator.*;
import it.skyplex.commands.staff.*;
import it.skyplex.commands.user.*;
import it.skyplex.listeners.*;
import it.skyplex.utils.KitUtils;
import it.skyplex.utils.PermissionUtils;
import it.skyplex.database.ServerFiles;
import it.skyplex.utils.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Core extends JavaPlugin {
    private static Core instance;
    private static ServerFiles server = ServerFiles.getInstance();

    public static HashMap<String, PermissionAttachment> PERMISSIONS = new HashMap<>();
    public static HashMap<UUID, UUID> MSGREPLY = new HashMap<>();
    public static HashMap<UUID, UUID> TPA_REQUESTS = new HashMap<>();

    private static KitUtils kitUtils;
    private static PermissionUtils permissionUtils;
    private static ScoreboardUtils scoreboardUtils;

    @Override
    public void onEnable() {
        setInstance(this);

        registerUtilities();
        registerCommands();
        registerListeners();

        getConfig().options().copyDefaults(true);
        saveConfig();

        server.setup();

        scoreboardUtils.setupTeamScoreboard();
    }

    @Override
    public void onDisable() {
        setInstance(null);

    }

    private void registerCommands() {
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("ckit").setExecutor(new CreateKitCommand());
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("enchant").setExecutor(new EnchantCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("lockdown").setExecutor(new LockdownCommand());
        getCommand("lookup").setExecutor(new LookupCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("meta").setExecutor(new MetaCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("ranks").setExecutor(new RankCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("delhome").setExecutor(new DeleteHomeCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("tpa").setExecutor(new TPACommand());
        getCommand("tpaccept").setExecutor(new TPAcceptCommand());
        getCommand("tpdeny").setExecutor(new TPDenyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("invsee").setExecutor(new InvSeeCommand());
        getCommand("echest").setExecutor(new EnderchestCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("rules").setExecutor(new RulesCommand());
        getCommand("killall").setExecutor(new KillAllCommand());
    }
    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new EventPlayerJoin(), this);
        pluginManager.registerEvents(new EventPlayerLogin(), this);
        pluginManager.registerEvents(new EventPlayerQuit(), this);
        pluginManager.registerEvents(new EventPlayerChat(), this);
        pluginManager.registerEvents(new EventPlayerRespawn(), this);
    }
    private void registerUtilities() {
        kitUtils = new KitUtils();
        permissionUtils = new PermissionUtils();
        scoreboardUtils = new ScoreboardUtils();
    }

    public static Core getInstance() {
        return instance;
    }
    private void setInstance(Core instance) { this.instance = instance; }

    public static KitUtils getKitUtils() {
        return kitUtils;
    }
    public static PermissionUtils getPermissionUtils() { return permissionUtils; }
    public static ScoreboardUtils getScoreboardUtils() { return scoreboardUtils; }
}
