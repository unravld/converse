package net.unraveled.util;

import me.lucko.luckperms.api.LuckPermsApi;
import net.unraveled.ConversePlugin;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class ConverseBase {
    protected static final ConversePlugin plugin = JavaPlugin.getPlugin(ConversePlugin.class);
    protected Server server = ConversePlugin.server;
    protected final LuckPermsApi api = ConversePlugin.getLuckPermsAPI();
}
