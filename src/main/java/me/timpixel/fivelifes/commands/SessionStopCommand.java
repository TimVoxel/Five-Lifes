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

public class SessionStopCommand implements SubCommand {

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Title title = Title.title(Component.text("Сессия окончена!"), Component.empty());

        for (Player player : Bukkit.getOnlinePlayers())
            player.showTitle(title);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return new ArrayList<>();
    }
}