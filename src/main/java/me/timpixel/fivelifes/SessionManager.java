package me.timpixel.fivelifes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private final JavaPlugin plugin;
    private final int sessionLengthMinutes;
    public final List<SessionListener> listeners;

    private int currentTick;
    private BukkitRunnable timer;

    public SessionManager(JavaPlugin plugin, int sessionLengthMinutes) {
        listeners = new ArrayList<>();
        this.plugin = plugin;
        this.sessionLengthMinutes = sessionLengthMinutes;
    }

    public void start() {

        currentTick = 0;
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };

        timer.runTaskTimer(plugin, 1, 1);

        for (SessionListener listener : listeners)
            listener.onSessionStarted();
    }

    public void stop() {
        timer.cancel();

        for (SessionListener listener : listeners)
            listener.onSessionEnded();
    }

    private void tick() {
        currentTick++;

        for (SessionListener listener : listeners)
            listener.onSessionTick(currentTick);

        if (currentTick == sessionLengthMinutes * 60 * 20) {
            for (SessionListener listener : listeners)
                listener.onSessionTimeRanOut();
        }
    }
}
