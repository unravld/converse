package net.unraveled.playerdata;

import net.unraveled.api.abstracts.SerializableObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class PlayerdataSerializer extends SerializableObject<PlayerData> {
    private PlayerData playerDataObject;
    private String playerDataString;

    public PlayerdataSerializer(PlayerData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + data.getLastKnownName() + "\n");
        sb.append("UUID: " + data.getUUID().toString() + "\n");
        sb.append("IP: " + data.getIp() + "\n");
        sb.append("Rank: " + data.getLastKnownRank() + "\n");
        sb.append("Playtime: " + data.getPlaytime() + "\n");
        sb.append("Last Login: " + data.getLastLoggedIn().toString());
        playerDataString = sb.toString();
        playerDataObject = data;
    }

    public PlayerdataSerializer(String data) {
        String[] compoundData = data.split("\\r?\\n");
        String name = compoundData[0].split(":")[1];
        UUID uuid = UUID.fromString(compoundData[1].split(":")[1]);
        String ip = compoundData[2].split(":")[1];
        String rank = compoundData[3].split(":")[1];
        long playtime = Long.parseLong(compoundData[4].split(":")[1]);
        Date login = Date.from(Instant.parse(compoundData[5].split(":")[1]));

        PlayerData fData = new PlayerData((Player) Bukkit.getOfflinePlayer(uuid));
        fData.setLastKnownName(name);
        fData.setIp(ip);
        fData.setUUID(uuid);
        fData.setLastKnownRank(rank);
        fData.setPlaytime(playtime);
        fData.setLastLoggedIn(login);
        playerDataObject = fData;
        playerDataString = data;
    }

    @Override
    public String serialize() {
        return playerDataString;
    }

    @Override
    public PlayerData deserialize() {
        return playerDataObject;
    }
}
