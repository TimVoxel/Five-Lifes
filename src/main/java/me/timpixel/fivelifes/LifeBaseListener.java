package me.timpixel.fivelifes;

import java.util.UUID;

public interface LifeBaseListener {
    void onLifeBaseUpdated(UUID playerId, int lifeCount, int previousCount);
    void onLifeBaseLoaded();
}
