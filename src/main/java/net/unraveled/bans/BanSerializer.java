package net.unraveled.bans;

import net.unraveled.ConversePlugin;
import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.api.abstracts.SerializableObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BanSerializer extends SerializableObject<AbstractBan> {
    private final String serialized;
    private final AbstractBan ban;

    public BanSerializer(AbstractBan ban) {
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + ban.getName() + "\n");
        sb.append("uuid: " + ban.getUuid() + "\n");
        sb.append("issuer: " + ban.getPunisher() + "\n");
        sb.append("expiry: " + (new SimpleDateFormat("dd/MMM/yyyy").format(ban.getBanExpiry())) + "\n");
        sb.append("id: " + ban.getBanId() + "\n");
        sb.append("message: " + ban.getBanMessage());

        this.serialized = sb.toString();
        this.ban = ban;
    }

    public BanSerializer(String ban) {
        String[] args = ban.split("\\r?\\n");
        String uuid = args[1];
        String issuer = args[2];
        String date = args[3];
        String id = args[4];
        String message = args[5];

        Player fName;
        UUID fUuid = UUID.fromString(uuid.split(":")[1]);
        if (Bukkit.getPlayer(fUuid).isOnline() || (Bukkit.getPlayer(fUuid) != null)) {
            fName = Bukkit.getPlayer(fUuid);
        } else {
            fName = (Player) Bukkit.getOfflinePlayer(fUuid);
        }
        String fPun = issuer.split(":")[1];
        Date fDate = new Date();
        try {
            fDate = new SimpleDateFormat("dd/MMM/yyyy").parse(date.split(":")[1]);
        } catch (Exception ex) {
            ConversePlugin.server.getLogger().severe(ex.getMessage());
        }
        String fId = id.split(":")[1];
        String fMsg = message.split(":")[1];

        this.serialized = ban;
        this.ban = new SimpleBan(fName, fPun, fDate, fId, fMsg);
    }

    @Override
    public String serialize() {
        return serialized;
    }

    @Override
    public AbstractBan deserialize() {
        return ban;
    }
}
