package me.timpixel.fivelifes;

import me.timpixel.fivelifes.commands.LifeCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class FiveLifes extends JavaPlugin {

    @Override
    public void onEnable() {

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        config.addDefault("maxLifeCount", 5);
        config.addDefault("saveFileLocation", getDataFolder().getPath());
        saveConfig();

        int maxLifeCount = config.getInt("maxLifeCount");
        String saveFileLocation = config.getString("saveFileLocation");

        LifeBase lifeBase = new LifeBase(saveFileLocation, maxLifeCount);
        LifeManager lifeManager = new LifeManager(lifeBase, maxLifeCount);

        lifeBase.load();

        registerCommand("life", new LifeCommand(lifeBase));

        System.out.println("Five Lifes plugin loaded successfully");
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
