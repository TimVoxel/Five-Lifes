package me.timpixel.fivelifes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RootCommand implements TabExecutor {

    private final SubCommand[] subCommands;

    public RootCommand() {
        this.subCommands = getSubCommands();
    }

    protected abstract SubCommand[] getSubCommands();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (args.length == 0) return false;// Logger.error(sender, "No sub action specified");
        for (SubCommand subCommand : subCommands) {
            if (!subCommand.getName().equalsIgnoreCase(args[0])) continue;
            subCommand.onCommand(sender, command, s, Arrays.copyOfRange(args, 1, args.length));
            return true;
        }
        return true; //ogger.error(sender, "Command does not implement sub action " + args[0]);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (SubCommand subCommand : subCommands)
                list.add(subCommand.getName());
            return list;
        }
        else if (args.length > 1) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0])) return subCommand.onTabComplete(sender, command, s, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return null;
    }
}