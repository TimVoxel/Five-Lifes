package me.timpixel.fivelifes;

import org.bukkit.scheduler.BukkitRunnable;

public class LifesAutoSave extends BukkitRunnable {

    private final LifeBase lifeBase;

    public LifesAutoSave(LifeBase lifeBase) {
        this.lifeBase = lifeBase;
    }

    @Override
    public void run() {
        lifeBase.save();
        System.out.println("AUTO-SAVE: saved lifes");
    }
}
