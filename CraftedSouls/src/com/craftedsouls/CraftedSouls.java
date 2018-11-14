package com.craftedsouls;

import com.craftedsouls.cmds.ability.SetAbilityCommand;
import com.craftedsouls.cmds.admin.LoggingCommand;
import com.craftedsouls.cmds.admin.*;
import com.craftedsouls.cmds.item.CreateItemCommand;
import com.craftedsouls.cmds.item.GetItemCommand;
import com.craftedsouls.cmds.item.ListItemsCommand;
import com.craftedsouls.cmds.player.*;
import com.craftedsouls.cmds.util.*;
import com.craftedsouls.cmds.admin.DeleteWarpCommand;
import com.craftedsouls.cmds.player.ListWarpCommand;
import com.craftedsouls.cmds.admin.SetWarpCommand;
import com.craftedsouls.cmds.player.WarpCommand;
import com.craftedsouls.data.LevelData;
import com.craftedsouls.gui.guis.*;
import com.craftedsouls.data.Settings;
import com.craftedsouls.events.*;
import com.craftedsouls.gui.GameGUIManager;
import com.craftedsouls.cmds.admin.TicketAcceptCommand;
import com.craftedsouls.cmds.admin.TicketCloseCommand;
import com.craftedsouls.cmds.player.TicketCreateCommand;
import com.craftedsouls.cmds.admin.TicketListCommand;
import com.craftedsouls.utils.items.GameItemList;
import com.craftedsouls.utils.items.ItemGlow;
import com.craftedsouls.utils.managers.*;
import com.craftedsouls.utils.tasks.RegenerationTask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class CraftedSouls extends JavaPlugin {

    /*
        GUI Count: 8
        Next GUI: 9
     */

    public static CraftedSouls plugin;

    static ChatManager chatManager;
    static GameGUIManager guiManager;
    static DatabaseManager databaseManager;
    static RankManager rankManager;
    static NPCManager npcManager;
    static MobManager mobManager;
    static BarManager barManager;
    static XPManager xpManager;
    static DeathManager deathManager;
    static CombatManager combatManager;
    static MainMenuManager mainMenuManager;
    static CooldownManager cooldownManager;

    public static HashMap<UUID, Integer> dead = new HashMap<>();

    Settings settings = Settings.getInstance();
    LevelData levelData = LevelData.getInstance();

    public void onEnable() {

        chatManager = new ChatManager();
        guiManager = new GameGUIManager();
        databaseManager = new DatabaseManager();
        rankManager = new RankManager();
        npcManager = new NPCManager();
        mobManager = new MobManager();
        barManager = new BarManager();
        xpManager = new XPManager();
        deathManager = new DeathManager();
        combatManager = new CombatManager();
        mainMenuManager = new MainMenuManager();
        cooldownManager = new CooldownManager(this);

        plugin = this;
        settings.setup();
        levelData.setup();
        if(!levelData.get().contains("RXP")) {
            levelData.createLevels();
        }

        //MOTD Data
        if(!settings.getData().contains("state") || !settings.getData().contains("news") || !settings.getData().contains("development")) {
            settings.getData().set("state", "Development");
            settings.saveData();
            settings.getData().set("news", "None!");
            settings.saveData();
            settings.getData().set("development", false);
            settings.saveData();
        }

        getLogger().log(Level.INFO, "Connecting to the database...");
        //Connect
        databaseManager.connect();

        getLogger().log(Level.INFO, "Database connected!");

        GameItemList.createItems();

        EventCommandPreprocess.init();

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("chat").setExecutor(new ChatControlCommand());
        getCommand("clearinventory").setExecutor(new ClearInventoryCommand());

        getCommand("delwarp").setExecutor(new DeleteWarpCommand());
        getCommand("development").setExecutor(new ServerDevCommand());
        getCommand("debug").setExecutor(new DebugCommand());
        getCommand("cleardata").setExecutor(new ClearDataCommand());
        getCommand("friend").setExecutor(new FriendCommand());
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("getitem").setExecutor(new GetItemCommand());
        getCommand("listitems").setExecutor(new ListItemsCommand());
        getCommand("createitem").setExecutor(new CreateItemCommand());
        getCommand("channel").setExecutor(new ChannelCommand());

        getCommand("kick").setExecutor(new KickCommand());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("logging").setExecutor(new LoggingCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("opengui").setExecutor(new OpenGUICommand());
        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("teleporthere").setExecutor(new TeleportHereCommand());
        getCommand("ticketaccept").setExecutor(new TicketAcceptCommand());
        getCommand("ticketclose").setExecutor(new TicketCloseCommand());
        getCommand("ticketcreate").setExecutor(new TicketCreateCommand());
        getCommand("ticketlist").setExecutor(new TicketListCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warps").setExecutor(new ListWarpCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("lookup").setExecutor(new LookupCommand());
        getCommand("warn").setExecutor(new WarningCommand());
        getCommand("npccreate").setExecutor(new NPCCreateCommand());
        getCommand("faction").setExecutor(new FactionEasterEgg());
        getCommand("settutorial").setExecutor(new TutorialSpawnCommand());
        getCommand("motd").setExecutor(new MOTDCommand());
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("op").setExecutor(new OPCommand());
        //getCommand("createability").setExecutor(new CreateAbilityCommand());
        getCommand("setability").setExecutor(new SetAbilityCommand());

        getCommand("sql").setExecutor(new SQLCommand());

        pluginManager.registerEvents(new EventPlayerJoin(), this);
        pluginManager.registerEvents(new EventPlayerLeave(), this);
        pluginManager.registerEvents(new EventPlayerChat(), this);
        pluginManager.registerEvents(new EventCommandPreprocess(), this);
        pluginManager.registerEvents(new EventInventoryClick(), this);
        pluginManager.registerEvents(new EventPlayerInteract(), this);
        pluginManager.registerEvents(new EventPlayerDropItem(), this);
        pluginManager.registerEvents(new EventPlayerRespawn(), this);
        pluginManager.registerEvents(new EventEntityDamage(), this);
        pluginManager.registerEvents(new EventPlayerDeath(), this);
        pluginManager.registerEvents(new EventBlockBreak(), this);
        pluginManager.registerEvents(new EventBlockPlace(), this);
        pluginManager.registerEvents(new EventServerListPing(), this);
        pluginManager.registerEvents(new EventPlayerInteractEntity(), this);
        pluginManager.registerEvents(new EventEntityDamageByEntity(), this);
        pluginManager.registerEvents(new EventVehicleMove(), this);
        pluginManager.registerEvents(new EventEntityDeath(), this);
        pluginManager.registerEvents(new EventPlayerLogin(), this);
        pluginManager.registerEvents(new EventInventoryClose(), this);
        pluginManager.registerEvents(new EventMoveItem(), this);
        pluginManager.registerEvents(new EventPlayerMove(), this);
        pluginManager.registerEvents(new EventPlayerTeleport(), this);
        pluginManager.registerEvents(new EventPlayerSwapHandsItems(), this);


        guiManager.addGUI(new CharacterGUI());
        guiManager.addGUI(new StaffListGUI());
        guiManager.addGUI(new PlayerInformationGUI());
        guiManager.addGUI(new BankMainGUI());
        guiManager.addGUI(new BankWithdrawGUI());
        guiManager.addGUI(new BankDepositGUI());
        guiManager.addGUI(new DeleteSlotGUI());

        //Tasks
        BukkitTask regenerationTask = new RegenerationTask(this).runTaskTimer(this, 20, 20);
        BukkitTask cooldownManager = getCooldownManager().runTaskTimer(this, 20, 20);

        for(Player player : Bukkit.getOnlinePlayers()) {
            int health = barManager.getHealthValue(player);
            int maxHealth = barManager.getMaxHealthValue(player);
            int spirit = barManager.getSpiritValue(player);
            int maxSpirit = barManager.getMaxSpiritValue(player);

            barManager.setupPlayer(player, health, maxHealth, spirit, maxSpirit);
        }

        deathManager.deathLoop();

        registerGlow();

    }

    @Override
    public void onDisable() {
        for(UUID uuid : barManager.getBarStorage().keySet()) {
            barManager.getBarStorage().get(uuid)[0].removeAll();
            barManager.getBarStorage().get(uuid)[1].removeAll();
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (combatManager.isPlayerCombatmode(player)) {
                combatManager.exitCombatmode(player);
            }

            String kickmsg = "§cThe server is currently restarting. Please rejoin in a few minutes.";
            if(mainMenuManager.isInMainMenu(player)) {
                player.kickPlayer(kickmsg);

            }
        }

        for(UUID uuid : dead.keySet()) {
            Player newPlayer = Bukkit.getPlayer(uuid);

            if(combatManager.isPlayerCombatmode(newPlayer)) {
                combatManager.exitCombatmode(newPlayer);
            }

            newPlayer.teleport(newPlayer.getWorld().getSpawnLocation());
            newPlayer.setGameMode(GameMode.SURVIVAL);

            dead.remove(newPlayer.getUniqueId());

            newPlayer.setAllowFlight(false);
            newPlayer.setFlying(false);

            newPlayer.getActivePotionEffects().removeAll(newPlayer.getActivePotionEffects());

            CraftedSouls.getChatManager().sendNoPrefixChat(newPlayer, "§aYou have awoken into a new body...");
        }
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ItemGlow glow = new ItemGlow(1723);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ChatManager getChatManager() {
            return chatManager;
    }
    public static GameGUIManager getGUIManager() { return guiManager; }
    public static DatabaseManager getDatabaseManager() { return databaseManager; }
    public static RankManager getRankManager() { return rankManager; }
    public static NPCManager getNPCManager() { return npcManager; }
    public static MobManager getMobManager(){ return mobManager; }
    public static BarManager getBarManager() { return barManager; }
    public static XPManager getXPManager() { return xpManager; }
    public static DeathManager getDeathManager() { return deathManager; }
    public static CombatManager getCombatManager() { return  combatManager; }
    public static MainMenuManager getMainMenuManager() { return  mainMenuManager; }
    public static CooldownManager getCooldownManager() { return cooldownManager; }
}
