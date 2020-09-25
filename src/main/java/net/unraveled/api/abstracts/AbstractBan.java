package net.unraveled.api.abstracts;

import net.unraveled.Container;
import net.unraveled.ConversePlugin;
import net.unraveled.api.interfaces.IBan;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public abstract class AbstractBan implements IBan {
    protected final ConversePlugin plugin;
    protected final Player player;
    protected final CommandSender sender;
    protected final Server server;
    protected final Date date;
    protected final Long duration;
    protected final String id;

    public AbstractBan(Player player, CommandSender sender, Date date, Long duration, String id) {
        this.player = player;
        this.sender = sender;
        this.plugin = new Container().getPlugin();
        this.server = plugin.getServer();
        this.date = date;
        this.duration = duration;
        this.id = id;
    }

    public abstract void process();
}
