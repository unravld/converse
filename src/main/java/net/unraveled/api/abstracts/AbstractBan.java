package net.unraveled.api.abstracts;

import net.unraveled.ConversePlugin;
import net.unraveled.api.interfaces.IBan;
import net.unraveled.util.ConverseBase;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Date;

public abstract class AbstractBan extends ConverseBase implements IBan {
    protected final Player player;
    protected final String sender;
    protected final Date date;
    protected final Long duration;
    protected final String id;
    protected final String message;

    public AbstractBan(Player player, String sender, Date date, Long duration, String id, String message) {
        this.player = player;
        this.sender = sender;
        this.date = date;
        this.duration = duration;
        this.id = id;
        this.message = message;
    }
}
