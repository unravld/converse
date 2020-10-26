package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import net.unraveled.playerdata.PlayerData;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;


public class PlaytimeListener extends ConverseBase implements Listener {
    public BukkitScheduler scheduler = Bukkit.getScheduler();
    public final Map<UUID, Long> timeLoggedIn = new HashMap<>();

    public PlaytimeListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        scheduler.runTaskTimerAsynchronously(plugin, (new Task()), 0L, 6000L);
    }

    /**
     * A private task to execute from the scheduler.
     */
    private class Task implements Consumer<BukkitTask> {
        @Override
        public void accept(BukkitTask bukkitTask) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                cachePlayerPlaytime(player);
            }
        }
    }

    public void cachePlayerPlaytime(Player player) {
        Long loggedInAt = timeLoggedIn.get(player.getUniqueId());
        Long currentTimeMillis = System.currentTimeMillis();
        long diffTime = currentTimeMillis - loggedInAt;
        timeLoggedIn.put(player.getUniqueId(), currentTimeMillis);
        PlayerData pData = plugin.playerDataManager.getPlayerData(player);
        pData.setPlaytime(pData.getPlaytime() + diffTime);
        plugin.playerDataManager.save(pData);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        timeLoggedIn.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        cachePlayerPlaytime(e.getPlayer());
    }
}
