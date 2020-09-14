package net.unraveled.playerdata;

import net.unraveled.ConversePlugin;
import net.unraveled.bans.BanData;
import net.unraveled.commands.Manage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private UUID UUID;
    private String lastKnownRank;
    private String lastKnownName;
    private String ip;
    private long playtime;
    private int coins;
    private Date lastLoggedIn;
    private List<BanData> bans;
    private Manage.ManagedSettings managedSettings;

    public PlayerData(Player player) {
        this.UUID = player.getUniqueId();
        this.lastKnownRank = ConversePlugin.getLuckPermsAPI().getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
        this.ip = player.getAddress().getAddress().getHostAddress().replace("\\.", "\\_");
        this.coins = 0;
        this.playtime = 0;
        this.lastLoggedIn = new Date();
        this.lastKnownName = player.getName();
        this.managedSettings = new Manage.ManagedSettings();
        this.bans = new ArrayList<>();
    }

    public List<BanData> getBans() {
        return bans;
    }

    public void setBans(List<BanData> bans) {
        this.bans = bans;
    }

    public void setLastKnownRank(String lastKnownRank) {
        this.lastKnownRank = lastKnownRank;
    }

    public void setManagedSettings(Manage.ManagedSettings managedSettings) {
        this.managedSettings = managedSettings;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public Manage.ManagedSettings getManagedSettings() {
        return managedSettings;
    }

    public int getCoins() {
        return coins;
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

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
        if (this.coins < 0) {
            this.coins = 0;
        }
    }
}
