package me.timpixel.fivelifes;

import me.timpixel.fivelifes.commands.GiveLifeCommand;
import me.timpixel.fivelifes.commands.LifeCommand;
import me.timpixel.fivelifes.commands.SessionCommand;
import me.timpixel.fivelifes.listeners.ChatListener;
import me.timpixel.fivelifes.listeners.DeathListener;
import me.timpixel.fivelifes.listeners.JoinListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FiveLifes extends JavaPlugin {

    private static FiveLifes instance;
    private LifeBase lifeBase;
    private SessionManager sessionManager;
    private LifesAutoSave autoSave;

    public static LifeBase getLifeBase() {
        return instance.lifeBase;
    }

    public static SessionManager getSessionManager() {
        return instance.sessionManager;
    }

    @Override
    public void onEnable() {

        instance = this;

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        config.addDefault("maxLifeCount", 5);
        config.addDefault("saveFileLocation", getDataFolder().getPath());
        config.addDefault("autoSaveFrequencyMinutes", 5);
        config.addDefault("sessionLengthMinutes", 180);
        config.addDefault("giveLifeEnabled", true);
        saveConfig();

        int maxLifeCount = config.getInt("maxLifeCount");
        String saveFileLocation = config.getString("saveFileLocation");
        int autoSaveFrequency = config.getInt("autoSaveFrequencyMinutes") * 60 * 20;
        int sessionLengthMinutes = config.getInt("sessionLengthMinutes");
        boolean isGiveLifeEnabled = config.getBoolean("giveLifeEnabled");

        lifeBase = new LifeBase(saveFileLocation, maxLifeCount);
        sessionManager = new SessionManager(this, sessionLengthMinutes);

        LifeManager lifeManager = new LifeManager(lifeBase, maxLifeCount);
        lifeBase.listeners.add(lifeManager);

        VisualSessionDisplay visualSessionDisplay = new VisualSessionDisplay();
        sessionManager.listeners.add(visualSessionDisplay);

        lifeBase.load();
        autoSave = new LifesAutoSave(lifeBase);
        autoSave.runTaskTimer(this, autoSaveFrequency, autoSaveFrequency);

        getServer().getPluginManager().registerEvents(new DeathListener(lifeBase), this);
        getServer().getPluginManager().registerEvents(new JoinListener(lifeBase), this);
        getServer().getPluginManager().registerEvents(new ChatListener(lifeBase, lifeManager), this);

        registerCommand("life", new LifeCommand(lifeBase));
        registerCommand("session", new SessionCommand(sessionManager));
        registerCommand("give_life", new GiveLifeCommand(isGiveLifeEnabled, lifeBase));
        log(Level.INFO, "Five Lifes plugin loaded successfully");
    }

    @Override
    public void onDisable() {
        lifeBase.save();
        if (autoSave != null)
            autoSave.cancel();
    }

    private void registerCommand(String name, TabExecutor executor) {
        PluginCommand command = getCommand(name);
        if (command != null)
        {
            command.setExecutor(executor);
            command.setTabCompleter(executor);
        }
    }

    public static void log(Level level, String message) {
        instance.getLogger().log(level, message);
    }
}
