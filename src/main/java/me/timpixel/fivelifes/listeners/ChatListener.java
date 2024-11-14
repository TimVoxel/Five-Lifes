package me.timpixel.fivelifes.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.timpixel.fivelifes.LifeBase;
import me.timpixel.fivelifes.LifeManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener, ChatRenderer {

    private final LifeBase lifeBase;
    private final LifeManager lifeManager;

    public ChatListener(LifeBase lifeBase, LifeManager lifeManager) {
        this.lifeBase = lifeBase;
        this.lifeManager = lifeManager;
    }

    @EventHandler
    private void onChatMessageSent(AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

        int life = lifeBase.get(source);
        Team team = lifeManager.getTeamOfLife(life);

        return sourceDisplayName.color(team.color()).append(team.suffix())
                .append(Component.text(": ").color(NamedTextColor.GRAY))
                .append(message.color(NamedTextColor.WHITE));
    }
}
