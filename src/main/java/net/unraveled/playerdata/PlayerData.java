package net.unraveled.playerdata;

import net.unraveled.ConversePlugin;
import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.commands.Manage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PlayerData {
    private UUID UUID;
    private String lastKnownRank;
    private String lastKnownName;
    private String ip;
    private long playtime;
    private Date lastLoggedIn;
    private Manage.ManagedSettings managedSettings;
    private ArrayList<AbstractBan> bans = new ArrayList<>();

    public PlayerData(Player player) {
        this.UUID = player.getUniqueId();
        this.lastKnownRank = Objects
                .requireNonNull(ConversePlugin.getLuckPermsAPI().getUserManager().getUser(player.getUniqueId()))
                .getPrimaryGroup();
        this.ip = Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress().replace("\\.", "\\_");
        this.playtime = 0;
        this.lastLoggedIn = new Date();
        this.lastKnownName = player.getName();
        this.managedSettings = new Manage.ManagedSettings();
    }

    public void setLastKnownRank(String lastKnownRank) {
        this.lastKnownRank = lastKnownRank;
    }

    public void setManagedSettings(Manage.ManagedSettings managedSettings) {
        this.managedSettings = managedSettings;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public void setLastKnownName(String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
    }

    public Manage.ManagedSettings getManagedSettings() {
        return managedSettings;
    }

    public String getLastKnownRank() {
        return lastKnownRank;
    }

    public long getPlaytime() {
        return playtime;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public String getIp() {
        return ip;
    }

    public UUID getUUID() {
        return UUID;
    }

    public ArrayList<AbstractBan> getBans() {
        return bans;
    }
}
