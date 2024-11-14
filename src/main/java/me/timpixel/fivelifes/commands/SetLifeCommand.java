package me.timpixel.fivelifes.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetLifeCommand implements SubCommand {

    private final LifeCommand root;

    public SetLifeCommand(LifeCommand root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 2)
        {
            sender.sendMessage(Component.text("Specify a player name and the number of lifes").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Component.text("Player \"" + args[0] + "\" is not online or does not exist"));
            return true;
        }

        try {
            int value = Integer.parseInt(args[1]);

            if (value > root.getLifeBase().getMaxLifeCount())
            {
                sender.sendMessage(Component.text(value + " is more than the allowed amount, setting to " + root.getLifeBase().getMaxLifeCount()).color(NamedTextColor.YELLOW));
                value = root.getLifeBase().getMaxLifeCount();
            }

            root.getLifeBase().setLifeCount(player, value);
            sender.sendMessage(Component.text("Successfully set the number of lifes of ").append(player.displayName()).append(Component.text(" to " + value)));
            return true;
        }
        catch (NumberFormatException e) {
            sender.sendMessage(Component.text("The number of lifes should be a parsable integer").color(NamedTextColor.RED));
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length > 1)
            return new ArrayList<>();
        return null;
    }
}
