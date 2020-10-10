package net.unraveled.api.interfaces;

import net.unraveled.ConversePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public interface IBan {
    UUID getUuid();

    String getName();

    String getPunisher();

    Date getBanDate();

    Long getBanDuration();

    String getBanId();

    String getBanMessage();
}
