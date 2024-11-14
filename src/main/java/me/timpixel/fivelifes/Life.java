package me.timpixel.fivelifes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Life {

    DEAD,
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    DARK_GREEN;

    public Component getDisplayName() {
        switch (this) {
            case DEAD:
                return Component.text("Мертв").color(NamedTextColor.GRAY);
            case RED:
                return Component.text("1 жизнь").color(NamedTextColor.RED);
            case ORANGE:
                return Component.text("2 жизни").color(NamedTextColor.GOLD);
            case YELLOW:
                return Component.text("3 жизни").color(NamedTextColor.YELLOW);
            case GREEN:
                return Component.text("4 жизни").color(NamedTextColor.GREEN);
            case DARK_GREEN:
                return Component.text("5+ жизней").color(NamedTextColor.DARK_GREEN);
            default:
                return null;
        }
    }

    public NamedTextColor getColor() {
        switch (this) {
            case DEAD:
                return NamedTextColor.GRAY;
            case RED:
                return NamedTextColor.RED;
            case ORANGE:
                return NamedTextColor.GOLD;
            case YELLOW:
                return NamedTextColor.YELLOW;
            case GREEN:
                return NamedTextColor.GREEN;
            case DARK_GREEN:
                return NamedTextColor.DARK_GREEN;
            default:
                return null;
        }
    }

    public static Life get(int count) {
        if (count == 0)
            return DEAD;
        if (count == 1)
            return RED;
        if (count == 2)
            return ORANGE;
        if (count == 3)
            return YELLOW;
        if (count == 4)
            return GREEN;
        if (count >= 5)
            return DARK_GREEN;
        return null;
    }

    public String getIdentifier() {
        return toString().toLowerCase();
    }
}
