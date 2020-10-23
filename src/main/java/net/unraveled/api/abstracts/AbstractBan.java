package net.unraveled.api.abstracts;

import net.unraveled.api.interfaces.IBan;
import net.unraveled.util.ConverseBase;
import org.bukkit.entity.Player;

import java.util.Date;

public abstract class AbstractBan extends ConverseBase implements IBan {
    protected final Player player;
    protected final String sender;
    protected final Date expiry;
    protected final Date issueDate;
    protected final String id;
    protected final String message;

    public AbstractBan(Player player, String sender, Date expiry, String id, String message) {
        this.player = player;
        this.sender = sender;
        this.expiry = expiry;
        this.id = id;
        this.message = message;
        issueDate = new Date();
    }

    @Override
    public Date getIssueDate() {
        return issueDate;
    }
}
