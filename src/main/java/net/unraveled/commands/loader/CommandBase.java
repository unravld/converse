package net.unraveled.commands.loader;

import net.unraveled.ConversePlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class CommandBase {
    protected ConversePlugin plugin;
    protected Server server;

    public CommandBase() {}

    abstract public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args);

    public void setup(final ConversePlugin plugin, final CommandSender cs, final Class<?> clazz) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    public ConversePlugin getPlugin() {
        return JavaPlugin.getPlugin(ConversePlugin.class);
    }

    public Player getPlayer(final String partial) {
        List<Player> matcher = server.matchPlayer(partial);
        if (matcher.isEmpty()) {
            for (Player p : server.getOnlinePlayers()) {
                if (p.getDisplayName().toLowerCase().contains(partial.toLowerCase())) {
                    return p;
                }
            }
            return null;
        } else {
            return matcher.get(0);
        }
    }

    public OfflinePlayer getOfflinePlayer(final String name) {
        Player p = getPlayer(name);
        if (p != null) return p;
        return Bukkit.getOfflinePlayer(name);
    }
}
