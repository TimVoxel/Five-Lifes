package me.timpixel.fivelifes;

import me.timpixel.fivelifes.commands.LifeCommand;
import me.timpixel.fivelifes.listeners.DeathListener;
import me.timpixel.fivelifes.listeners.JoinListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class FiveLifes extends JavaPlugin {

    private LifeBase lifeBase;
    private LifesAutoSave autoSave;

    @Override
    public void onEnable() {

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        config.addDefault("maxLifeCount", 5);
        config.addDefault("saveFileLocation", getDataFolder().getPath());
        config.addDefault("autoSaveFrequencyMinutes", 5);
        saveConfig();

        int maxLifeCount = config.getInt("maxLifeCount");
        String saveFileLocation = config.getString("saveFileLocation");
        int autoSaveFrequency = config.getInt("autoSaveFrequencyMinutes") * 60 * 20;

        lifeBase = new LifeBase(saveFileLocation, maxLifeCount);
        LifeManager lifeManager = new LifeManager(lifeBase, maxLifeCount);

        lifeBase.load();
        autoSave = new LifesAutoSave(lifeBase);
        autoSave.runTaskTimer(this, autoSaveFrequency, autoSaveFrequency);

        getServer().getPluginManager().registerEvents(new DeathListener(lifeBase), this);
        getServer().getPluginManager().registerEvents(new JoinListener(lifeBase), this);

        registerCommand("life", new LifeCommand(lifeBase));
        System.out.println("Five Lifes plugin loaded successfully");
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
}
