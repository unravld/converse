package net.unraveled.bans;

import net.unraveled.api.abstracts.AbstractBan;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class BanHandler extends AbstractBan {
    private final ArrayList<AbstractBan> bans = new ArrayList<>();

    public BanHandler(Player player, CommandSender sender, Date date, long duration, String id, String message) {
        super(player, sender.getName(), date, duration, id, message);
    }

    public BanHandler(Player player, String sender, Date date, long duration, String id, String message) {
        super(player, sender, date, duration, id, message);
    }

    public BanHandler(Player player, CommandSender sender, Date date, long duration, String id) {
        this(player, sender, date, duration, id, "You are banned from this server!");
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
    public Date getBanDate() {
        return date;
    }

    @Override
    public Long getBanDuration() {
        return duration;
    }

    @Override
    public String getBanId() {
        return id;
    }

    @Override
    public String getBanMessage() {
        return message;
    }

    public ArrayList<AbstractBan> getBans() {
        return bans;
    }

    public void addBan(AbstractBan ban) {
        if (!bans.contains(ban)) bans.add(ban);
    }

    public void removeBan(AbstractBan ban) {
        if (bans.contains(ban)) bans.remove(ban);
    }

    @Override
    public void save() {
        if (player.isOnline()) {
            player.kickPlayer(message);
        }

        addBan(this);
        // TODO: Write YAML files per user in a Bans folder.
    }

    @Override
    public void load() {
        // TODO: Load from YAML files per user in a Bans folder.
    }
}
