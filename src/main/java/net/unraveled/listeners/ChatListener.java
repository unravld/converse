package net.unraveled.listeners;

import net.unraveled.ConversePlugin;
import net.unraveled.util.ConverseBase;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatListener extends ConverseBase implements Listener {
    public Util util = ConversePlugin.util;

    public ChatListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static final List<Player> punished = new ArrayList<>();

    public static boolean isPunished(Player player) {
        return punished.contains(player);
    }

    public static void setPunished(Player player, boolean mute) {
        if (mute) {
            punished.add(player);
            return;
        }
        punished.remove(player);
    }

    @EventHandler
    public void onPlayerChat(@NotNull AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if (isPunished(player)) {
            event.setMessage(ChatColor.BLACK + message);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void staffChat(@NotNull AsyncPlayerChatEvent event) {
        String message = event.getMessage().trim();
        Player p = event.getPlayer();

        if (Util.isInStaffChat(p.getUniqueId())) {
            event.setCancelled(true);
            Util.staffchat(p, message);
        }
    }
}
