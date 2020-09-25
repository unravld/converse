package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import net.unraveled.commands.Cage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CageListener implements Listener {
    public final Map<UUID, Cage.Cager> cages = new HashMap<>();

    public CageListener(ConversePlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (cages.containsKey(e.getPlayer().getUniqueId())) {
            Cage.Cager cage = cages.get(e.getPlayer().getUniqueId());
            cage.undo();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (cages.containsKey(e.getPlayer().getUniqueId())) {
            Cage.Cager cage = cages.get(e.getPlayer().getUniqueId());
            cage.createCage();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        if (cages.containsKey(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        } else if (!cages.isEmpty()) {
            for (UUID u : cages.keySet()) {
                Cage.Cager c = cages.get(u);
                if (c.previousBlocks.containsKey(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onProcessCommand(PlayerCommandPreprocessEvent e) {
        if (cages.containsKey(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You may not use any commands while caged!");
            e.setCancelled(true);
        }
    }
}
