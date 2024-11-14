package me.timpixel.fivelifes.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReloadLifesCommand implements SubCommand {

    private final LifeCommand root;

    public ReloadLifesCommand(LifeCommand root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        root.getLifeBase().load();
        sender.sendMessage(Component.text("Successfully reloaded life data from lifes.txt"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return new ArrayList<>();
    }
}