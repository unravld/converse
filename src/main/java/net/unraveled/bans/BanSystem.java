package net.unraveled.bans;

import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.config.FileUtils;
import net.unraveled.util.ConverseBase;

import java.io.IOException;
import java.util.ArrayList;

public class BanSystem extends ConverseBase {
    private final ArrayList<AbstractBan> bans = new ArrayList<>();

    // Constructor
    public BanSystem() {
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

    public void save(AbstractBan ban) {
        addBan(ban);
        try {
            FileUtils banFile = new FileUtils(ban.getName() + ".ban", "bans");
            banFile.write(new BanSerializer(ban).serialize());
        } catch (IOException ex) {
            plugin.getLogger().severe(ex.getMessage());
        }
    }

    public AbstractBan load(String name) {
        FileUtils banFile = new FileUtils(name + ".ban");
        if (!banFile.getFile().exists()) {
            plugin.getLogger().severe("That user does not exist, or is not banned!!");
        }

        String loader;

        try {
            loader = banFile.read();
            return new BanSerializer(loader).deserialize();
        } catch (IOException ex) {
            plugin.getLogger().severe(ex.getMessage());
        }

        return null;
    }
}
