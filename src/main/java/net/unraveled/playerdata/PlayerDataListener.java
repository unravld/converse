package net.unraveled.playerdata;

import net.unraveled.ConversePlugin;
import net.unraveled.commands.Manage;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;


public class PlayerDataListener extends ConverseBase implements Listener {
    private PlayerDataManager mgr;

    public PlayerDataListener(ConversePlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        mgr = plugin.playerDataManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (mgr.doesPlayerDataExist(e.getPlayer().getUniqueId())) {
            mgr.cacheExplicitPlayerData(mgr.getPlayerData(e.getPlayer()));
        } else {
            mgr.cacheExplicitPlayerData(new PlayerData(e.getPlayer()));
        }
        mgr.getPlayerData(e.getPlayer()).setLastLoggedIn(new Date());
        mgr.getPlayerData(e.getPlayer()).setLastKnownName(e.getPlayer().getName());
        mgr.getPlayerData(e.getPlayer()).setLastKnownRank(ConversePlugin.getLuckPermsAPI().getUserManager().getUser(e.getPlayer().getUniqueId()).getPrimaryGroup());
        if (mgr.getPlayerData(e.getPlayer()).getManagedSettings() == null)
            mgr.getPlayerData(e.getPlayer()).setManagedSettings(new Manage.ManagedSettings());
        if (mgr.getPlayerData(e.getPlayer()).getBans() == null)
            mgr.getPlayerData(e.getPlayer()).setBans(new ArrayList<>());
        mgr.save(mgr.getPlayerData(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        mgr.saveAndRemoveCache(mgr.getPlayerData(e.getPlayer()));
    }
}
