package me.timpixel.fivelifes;

import me.timpixel.fivelifes.commands.LifeCommand;
import me.timpixel.fivelifes.commands.SessionCommand;
import me.timpixel.fivelifes.listeners.ChatListener;
import me.timpixel.fivelifes.listeners.DeathListener;
import me.timpixel.fivelifes.listeners.JoinListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class FiveLifes extends JavaPlugin {

    private static FiveLifes instance;
    private LifeBase lifeBase;
    private LifesAutoSave autoSave;

    @Override
    public void onEnable() {

        instance = this;

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        config.addDefault("maxLifeCount", 5);
        config.addDefault("saveFileLocation", getDataFolder().getPath());
        config.addDefault("autoSaveFrequencyMinutes", 5);
        config.addDefault("sessionLengthMinutes", 180);
        saveConfig();

        int maxLifeCount = config.getInt("maxLifeCount");
        String saveFileLocation = config.getString("saveFileLocation");
        int autoSaveFrequency = config.getInt("autoSaveFrequencyMinutes") * 60 * 20;
        int sessionLengthMinutes = config.getInt("sessionLengthMinutes");

        lifeBase = new LifeBase(saveFileLocation, maxLifeCount);
        LifeManager lifeManager = new LifeManager(lifeBase, maxLifeCount);

        lifeBase.load();
        autoSave = new LifesAutoSave(lifeBase);
        autoSave.runTaskTimer(this, autoSaveFrequency, autoSaveFrequency);

        getServer().getPluginManager().registerEvents(new DeathListener(lifeBase), this);
        getServer().getPluginManager().registerEvents(new JoinListener(lifeBase), this);
        getServer().getPluginManager().registerEvents(new ChatListener(lifeBase, lifeManager), this);

        registerCommand("life", new LifeCommand(lifeBase));
        registerCommand("session", new SessionCommand(sessionLengthMinutes));
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

    public static void runTaskLater(BukkitRunnable runnable, int delay) {
        runnable.runTaskLater(instance, delay);
    }

    public static void log(Level level, String message) {
        instance.getLogger().log(level, message);
    }
}
