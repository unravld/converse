package net.unraveled.util;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class Recurrent implements Consumer<BukkitTask> {

    private static Long lastRan = null;

    public static Long getLastRan() {
        return lastRan;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        lastRan = System.currentTimeMillis();
    }
}
