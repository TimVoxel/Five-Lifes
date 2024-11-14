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

public class RemoveLifeCommand implements SubCommand {

    private final LifeCommand root;

    public RemoveLifeCommand(LifeCommand root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 1)
        {
            sender.sendMessage(Component.text("Specify a player name and (optionally) the number of lifes").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Component.text("Player \"" + args[0] + "\" is not online or does not exist"));
            return true;
        }

        int number;
        if (args.length == 1) {
            number = 1;
        }
        else {
            try {
                number = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                sender.sendMessage(Component.text("The number of lifes should be a parsable integer").color(NamedTextColor.RED));
                return true;
            }
        }

        root.getLifeBase().removeLifes(player, number);
        sender.sendMessage(Component.text("Successfully removed " + number + " lifes from player ").append(player.displayName()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length > 1)
            return new ArrayList<>();
        return null;
    }
}
