package net.unraveled.api.interfaces;

import net.unraveled.ConversePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;

public interface IBan {
    ConversePlugin getPlugin();

    Player getPlayer();

    OfflinePlayer getOfflinePlayer();

    Date getBanDate();

    Long getBanDuration();

    String getBanId();
}
