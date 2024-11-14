package me.timpixel.fivelifes.commands;

import me.timpixel.fivelifes.FiveLifes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SessionStartCommand implements SubCommand {

    private final SessionCommand root;

    public SessionStartCommand(SessionCommand root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Title title = Title.title(Component.text("Сессия началась!"), Component.empty());

        for (Player player : Bukkit.getOnlinePlayers())
            player.showTitle(title);

        FiveLifes.runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers())
                    if (player.isOp())
                        player.sendMessage(Component.text("Сессия завершилась по времени").color(NamedTextColor.YELLOW));
            }
        }, root.getSessionLengthMinutes() * 60 * 20);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
