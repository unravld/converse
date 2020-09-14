package net.unraveled.util;

import me.lucko.luckperms.api.LuckPermsApi;
import net.unraveled.ConversePlugin;
import org.bukkit.Server;

public class ConverseBase {
    protected static ConversePlugin plugin = ConversePlugin.plugin;
    protected Server server = ConversePlugin.server;
    protected LuckPermsApi api = ConversePlugin.getLuckPermsAPI();
}
