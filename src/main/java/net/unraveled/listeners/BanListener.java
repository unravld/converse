package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class BanListener extends ConverseBase implements Listener {
    @SuppressWarnings("")
    public BanListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // Main Catch
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID pUUID = event.getUniqueId();
        if (plugin.bans.isBanned(pUUID)) {
            if (!plugin.bans.find(pUUID).getName().equalsIgnoreCase(Bukkit.getOfflinePlayer(pUUID).getName())) {
                plugin.bans.mark(pUUID, Bukkit.getOfflinePlayer(pUUID).getName());
            }
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, plugin.bans.generate(plugin.bans.find(pUUID)));
        }
    }

    // Backup Catch
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        UUID pUUID = event.getPlayer().getUniqueId();
        if (plugin.bans.isBanned(pUUID)) {
            if (!plugin.bans.find(pUUID).getName().equalsIgnoreCase(Bukkit.getOfflinePlayer(pUUID).getName())) {
                plugin.bans.mark(pUUID, Bukkit.getOfflinePlayer(pUUID).getName());
            }
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, plugin.bans.generate(plugin.bans.find(pUUID)));
        }

    }
}
