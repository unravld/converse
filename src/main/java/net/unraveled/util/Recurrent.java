package net.unraveled.util;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Recurrent extends BukkitRunnable {

    private static Long lastRan = null;

    public static Long getLastRan() {
        return lastRan;
    }

    public Recurrent(Plugin plugin) {
        Server server = plugin.getServer();
    }

    @Override
    public void run() {
        lastRan = System.currentTimeMillis();
    }
}
