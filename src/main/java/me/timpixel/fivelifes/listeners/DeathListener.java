package me.timpixel.fivelifes.listeners;

import me.timpixel.fivelifes.LifeBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final LifeBase lifeBase;

    public DeathListener(LifeBase lifeBase) {
        this.lifeBase = lifeBase;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (lifeBase.get(event.getPlayer()) == 0)
            return;

        lifeBase.removeLifes(event.getPlayer(), 1);
    }
}
