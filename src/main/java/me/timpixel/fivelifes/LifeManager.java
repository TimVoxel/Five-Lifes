package me.timpixel.fivelifes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class LifeManager implements LifeBaseListener {

    private final Team[] lifeTeams;
    private final int maxLifeCount;
    private final LifeBase lifeBase;

    public LifeManager(LifeBase lifeBase, int maxLifeCount) {
        this.lifeBase = lifeBase;
        this.maxLifeCount = maxLifeCount;
        this.lifeTeams = new Team[maxLifeCount + 1];
        this.lifeBase.listeners.add(this);
        setupTeams();
    }

    private void setupTeams() {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (int i = 0; i < maxLifeCount + 1; i++) {

            Life life = Life.get(i);
            if (life == null)
            {
                System.out.println("life of index " + i + " is null");
                continue;
            }

            Team team = scoreboard.getTeam(life.getIdentifier());
            if (team != null)
                team.unregister();

            lifeTeams[i] = scoreboard.registerNewTeam(life.getIdentifier());
            lifeTeams[i].color(life.getColor());
            lifeTeams[i].displayName(life.getDisplayName());
        }
    }

    public void onLifeBaseUpdated(UUID playerID, int value, int previousValue) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerID);
        lifeTeams[previousValue].removePlayer(player);

        Team team = lifeTeams[value];
        if (team == null)
            System.out.println("Team of index " + value + " is null");
        else {
            team.addPlayer(player);
            if (value == 0)
            {
                Player onlinePlayer = Bukkit.getPlayer(playerID);
                if (onlinePlayer != null)
                    MakeDead(onlinePlayer);
            }
        }
    }

    private void MakeDead(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.showTitle(Title.title(Component.text("Жизни кончились..."), Component.text("Вы погибли навсегда...").color(NamedTextColor.GRAY)));
        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
    }

    public void onLifeBaseLoaded() {

        for (Team team : lifeTeams)
            team.removeEntries(team.getEntries());

        for (UUID id : lifeBase.registeredPlayers())
        {
            OfflinePlayer player = Bukkit.getOfflinePlayer(id);
            System.out.println(player);
            System.out.println(player.getName());
            int value = lifeBase.get(id);
            lifeTeams[value].addPlayer(player);
        }
    }
}
