package me.timpixel.fivelifes;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class LifesAutoSave extends BukkitRunnable {

    private final LifeBase lifeBase;

    public LifesAutoSave(LifeBase lifeBase) {
        this.lifeBase = lifeBase;
    }

    @Override
    public void run() {
        lifeBase.save();
        FiveLifes.log(Level.INFO, "AUTO-SAVE: saved lifes");
    }
}
