package net.unraveled.api.abstracts;

import net.unraveled.ConversePlugin;
import net.unraveled.api.interfaces.ICommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandBase implements ICommand {
    protected ConversePlugin plugin;
    protected Server server;

    public CommandBase() {
    }

    @Override
    public void setup(final ConversePlugin plugin, final CommandSender cs, final Class<?> clazz) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Override
    public ConversePlugin getPlugin() {
        return JavaPlugin.getPlugin(ConversePlugin.class);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
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
