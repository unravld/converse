package net.unraveled.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerDataManager extends ConverseBase {
    private final Map<UUID, PlayerData> loadedData = new HashMap<>();
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public final BukkitScheduler scheduler = Bukkit.getScheduler();

    public PlayerDataManager() {
        File playersFolder = new File(plugin.getDataFolder(), "players");
        //noinspection ResultOfMethodCallIgnored
        playersFolder.mkdirs();

        scheduler.runTaskTimerAsynchronously(plugin, (new Task()), 6000L, 6000L);
    }

    private class Task implements Consumer<BukkitTask> {
        @Override
        public void accept(BukkitTask task) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (loadedData.containsKey(p.getUniqueId())) save(loadedData.get(p.getUniqueId()));
            }
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (loadedData.containsKey(uuid)) {
            return loadedData.get(uuid);
        } else {
            File file = new File(plugin.getDataFolder().toString() + "/players/", uuid + ".json");
            if (file.exists()) {
                try {
                    FileReader fr = new FileReader(file);
                    PlayerData pd = gson.fromJson(fr, PlayerData.class);
                    fr.close();

                    return pd;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public boolean doesPlayerDataExist(UUID uuid) {
        if (plugin.playerDataManager.loadedData.containsKey(uuid)) {
            return true;
        } else {
            File file = new File(plugin.getDataFolder().toString() + "/players/", uuid + ".json");
            return file.exists();
        }

    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId());
    }

    public void cacheExplicitPlayerData(PlayerData data) {
        if (data.getUUID() != null) {
            loadedData.put(data.getUUID(), data);
        } else {
            throw new InvalidPlayerDataException();
        }
    }

    public void save(PlayerData data) {
        if (data.getUUID() != null) {
            UUID uuid = data.getUUID();
            try {
                File file = new File(plugin.getDataFolder().toString() + "/players/", uuid + ".json");
                FileWriter fw = new FileWriter(file);
                gson.toJson(data, fw);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidPlayerDataException();
        }
    }

    public void saveAndRemoveCache(PlayerData data) {
        save(data);
        if (data.getUUID() != null) loadedData.remove(data.getUUID());
    }
}
