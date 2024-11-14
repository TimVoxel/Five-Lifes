package me.timpixel.fivelifes.commands;

import me.timpixel.fivelifes.LifeBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiveLifeCommand implements TabExecutor {

    private final boolean isEnabled;
    private final LifeBase lifeBase;

    public GiveLifeCommand(boolean isEnabled, LifeBase lifeBase) {
        this.isEnabled = isEnabled;
        this.lifeBase = lifeBase;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {

        if (!isEnabled)
        {
            sender.sendMessage(Component.text("Команда отключена на сервере"));
            return true;
        }

        if (!(sender instanceof Player))
            return true;

        if (args.length == 0) {
            sender.sendMessage(Component.text("Укажите игрока, которому хотите передать жизнь").color(NamedTextColor.RED));
            return true;
        }
        Player giver = (Player) sender;
        if (lifeBase.get(giver) == 0) {
            sender.sendMessage(Component.text("У вас нет жизней, вы не можете передать их").color(NamedTextColor.RED));
            return true;
        }

        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null)
        {
            sender.sendMessage(Component.text("Не удалось найти игрока " + args[0]).color(NamedTextColor.RED));
            return true;
        }

        if (receiver.getUniqueId().equals(giver.getUniqueId())) {
            sender.sendMessage(Component.text("Нельзя подарить жизнь самому себе").color(NamedTextColor.RED));
            return true;
        }

        if (lifeBase.get(receiver) == lifeBase.getMaxLifeCount())
        {
            sender.sendMessage(Component.text("У игрока " + receiver.getName() +  " максимальное количество жизней, передать ему еще одну нельзя").color(NamedTextColor.RED));
            return true;
        }

        if (lifeBase.get(receiver) == 0) {
            sender.sendMessage(Component.text("У игрока " + receiver.getName() + " не осталось жизней, подарить ему одну нельзя").color(NamedTextColor.RED));
            return true;
        }

        lifeBase.removeLifes(giver, 1);
        lifeBase.addLifes(receiver, 1);

        receiver.sendMessage(Component.text("Вам подарил жизнь игрок ").append(giver.displayName()));
        giver.sendMessage(Component.text("Вы подарили жизнб игроку ").append(receiver.displayName()));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        return null;
    }
}
