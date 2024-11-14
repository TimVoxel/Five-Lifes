package me.timpixel.fivelifes;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class LifeBase {

    private final String saveFileLocation;
    private final int maxLifeCount;
    private final Map<UUID, Integer> lifeCount;

    public List<LifeBaseListener> listeners;

    public LifeBase(String saveFileLocation, int maxLifeCount)
    {
        this.saveFileLocation = saveFileLocation;
        this.lifeCount = new HashMap<>();
        this.maxLifeCount = maxLifeCount;
        this.listeners = new ArrayList<>();
    }

    public int getMaxLifeCount() {
        return maxLifeCount;
    }

    public void addPlayer(Player player) {
        UUID playerID = player.getUniqueId();
        int value;

        if (!lifeCount.containsKey(playerID))
        {
            lifeCount.put(playerID, maxLifeCount);
            value = maxLifeCount;
            save();
        }
        else
            value = lifeCount.get(playerID);

        announceUpdate(playerID, value, value);
    }

    public void setLifeCount(Player player, int lifeValue) {
        UUID playerID = player.getUniqueId();

        int previousValue;
        if (lifeCount.containsKey(playerID))
            previousValue = get(player);
        else
            previousValue = lifeValue;

        lifeCount.put(playerID, lifeValue);
        save();
        announceUpdate(playerID, lifeValue, previousValue);
    }

    public void removeLifes(Player player, int number) {
        int count = Math.max(0, get(player) - number);
        setLifeCount(player, count);
    }

    public void addLifes(Player player, int number) {
        int count = Math.min(maxLifeCount, get(player) + number);
        setLifeCount(player, count);
    }

    public int get(Player player)  {
        return get(player.getUniqueId());
    }

    public int get(UUID id) {
        if (!lifeCount.containsKey(id))
            return 0;
        return lifeCount.get(id);
    }

    public Set<UUID> registeredPlayers() {
        return lifeCount.keySet();
    }

    public void save() {
        new Thread(() -> {
            try {
                File file = new File(saveFileLocation, "lifes.txt");
                if (file.exists())
                {
                    boolean isDeleted = file.delete();
                    if (!isDeleted)
                        System.out.println("Failed to delete lifes save file");
                }

                FileWriter writer = new FileWriter(file);

                for (UUID id : lifeCount.keySet()) {
                    int value = lifeCount.get(id);
                    writer.write(id + " " + value + "\n");
                }
                writer.close();
            }
            catch (IOException e) {
                System.out.println("Couldn't create save file for lifes");
            }
        }).start();
    }

    public void load() {
        lifeCount.clear();

        new Thread(() -> {
            try {
                File saveFile = new File(saveFileLocation,"lifes.txt");

                if (!saveFile.exists())
                {
                    System.out.println("Nothing to load, save file does not exist");
                    return;
                }

                FileReader reader = new FileReader(saveFile);
                Scanner scanner = new Scanner(reader);

                while (scanner.hasNextLine()) {

                    String[] fullLine = scanner.nextLine().split(" ");
                    String id = fullLine[0];
                    int lifeValue = Integer.parseInt(fullLine[1]);
                    lifeCount.put(UUID.fromString(id), lifeValue);
                }

                scanner.close();
                reader.close();

                announceLoading();
            }
            catch (IOException e) {
                System.out.println("Couldn't create save file for lifes");
            }
        }).start();
    }

    private void announceLoading() {
        for (LifeBaseListener listener : listeners)
            listener.onLifeBaseLoaded();
    }

    private void announceUpdate(UUID playerID, int lifeValue, int previousValue) {
        for (LifeBaseListener listener : listeners)
            listener.onLifeBaseUpdated(playerID, lifeValue, previousValue);
    }
}