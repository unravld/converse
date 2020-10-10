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
        sb.append("punisher: " + ban.getPunisher() + "\n");
        sb.append("date: " + (new SimpleDateFormat("dd/MMM/yyyy").format(ban.getBanDate())) + "\n");
        sb.append("duration: " + ban.getBanDuration() + "\n");
        sb.append("id: " + ban.getBanId() + "\n");
        sb.append("message: " + ban.getBanMessage());

        this.serialized = sb.toString();
        this.ban = ban;
    }

    public BanSerializer(String ban) {
        String[] args = ban.split("\\r?\\n");
        String uuid = args[1];
        String punisher = args[2];
        String date = args[3];
        String duration = args[4];
        String id = args[5];
        String message = args[6];

        Player fName;
        UUID fUuid = UUID.fromString(uuid.split(":")[1]);
        if (Bukkit.getPlayer(fUuid).isOnline() || (Bukkit.getPlayer(fUuid) != null)) {
            fName = Bukkit.getPlayer(fUuid);
        } else {
            fName = (Player) Bukkit.getOfflinePlayer(fUuid);
        }
        String fPun = punisher.split(":")[1];
        Date fDate = new Date();
        try {
            fDate = new SimpleDateFormat("dd/MMM/yyyy").parse(date.split(":")[1]);
        } catch (Exception ex) {
            ConversePlugin.server.getLogger().severe(ex.getMessage());
        }
        long fDuration = Long.parseLong(duration.split(":")[1]);
        String fId = id.split(":")[1];
        String fMsg = message.split(":")[1];

        this.serialized = ban;
        this.ban = new BanHandler(fName, fPun, fDate, fDuration, fId, fMsg);
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
