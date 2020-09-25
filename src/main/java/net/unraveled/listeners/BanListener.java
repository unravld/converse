package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class BanListener implements Listener {
    private final ConversePlugin plugin;

    @SuppressWarnings("")
    public BanListener(ConversePlugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        UUID pUUID = event.getUniqueId();
        if (plugin.banManager.isPlayerBanned(pUUID)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, plugin.banManager.getBanMessage(plugin.banManager.getLatestBan(pUUID)));
        }
    }
}
