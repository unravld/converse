package net.unraveled;

import org.bukkit.plugin.java.JavaPlugin;

public class Container {
    private final ConversePlugin plugin;

    public Container() {
        plugin = JavaPlugin.getPlugin(ConversePlugin.class);
    }

    public ConversePlugin getPlugin() {
        return plugin;
    }
}
