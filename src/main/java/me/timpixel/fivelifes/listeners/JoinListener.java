package me.timpixel.fivelifes.listeners;

import me.timpixel.fivelifes.LifeBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final LifeBase lifeBase;

    public JoinListener(LifeBase lifeBase) {
        this.lifeBase = lifeBase;
    }

    @EventHandler
    private void onPlayerJoined(PlayerJoinEvent event) {
        lifeBase.addPlayer(event.getPlayer());
    }
}
