package me.timpixel.fivelifes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VisualSessionDisplay implements SessionListener {

    private final Title startTitle = Title.title(Component.text("Сессия началась!"), Component.empty());
    private final Title endTitle = Title.title(Component.text("Сессия окончена!"), Component.empty());

    public void onSessionStarted() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.showTitle(startTitle);
    }

    public void onSessionEnded() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.showTitle(endTitle);
    }

    public void onSessionTimeRanOut() {
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.isOp())
                player.sendMessage(Component.text("Сессия завершилась по времени").color(NamedTextColor.YELLOW));
    }

    public void onSessionTick(int tick) {}
}
