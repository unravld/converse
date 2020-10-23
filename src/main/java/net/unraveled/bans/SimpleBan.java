package net.unraveled.bans;

import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.config.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SimpleBan extends AbstractBan {
    private final ArrayList<AbstractBan> bans = new ArrayList<>();

    public SimpleBan(Player player, CommandSender sender, Date expires, String id, String message) {
        super(player, sender.getName(), expires, id, message);
    }

    public SimpleBan(Player player, String sender, Date expires, String id, String message) {
        super(player, sender, expires, id, message);
    }

    public SimpleBan(Player player, CommandSender sender, Date expires, String id) {
        this(player, sender, expires, id, "You are banned from this server!");
    }

    public SimpleBan(Player player, String sender, Date expires, String id) {
        this(player, sender, expires, id, "You are banned from this server!");
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getPunisher() {
        return sender;
    }

    @Override
    public Date getBanExpiry() {
        return expiry;
    }

    @Override
    public String getBanId() {
        return id;
    }

    @Override
    public String getBanMessage() {
        return message;
    }
}
