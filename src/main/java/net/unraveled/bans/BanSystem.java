package net.unraveled.bans;

import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.config.FileUtils;
import net.unraveled.util.ConverseBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BanSystem extends ConverseBase {
    private static final ArrayList<AbstractBan> bans = new ArrayList<>();

    // Constructor
    public BanSystem() {
    }

    /**
     * Gets the bans loaded from the ban folder.
     * @return An array of AbstractBan objects.
     */
    public ArrayList<AbstractBan> getBans() {
        return bans;
    }

    /**
     * Adds an AbstractBan object to the array.
     * @param ban The AbstractBan (SimpleBan) object to add to the ban list.
     */
    public void addBan(AbstractBan ban) {
        if (!bans.contains(ban)) bans.add(ban);
    }

    /**
     * Removes an AbstractBan object from the array.
     * @param ban The AbstractBan object to remove.
     */
    public void removeBan(AbstractBan ban) {
        if (bans.contains(ban)) bans.remove(ban);
    }

    /**
     * Saves an AbstractBan object to the current List, and creates a new file, if necessary.
     * @param ban
     */
    public void save(AbstractBan ban) {
        FileUtils banFile = new FileUtils(ban.getName() + ".ban", "bans");
        banFile.write(new BanSerializer(ban).serialize());
        addBan(ban);
    }

    public AbstractBan load(String fileName) {
        FileUtils banFile = new FileUtils(fileName + ".ban", "bans");
        if (!banFile.getFile().exists()) {
            plugin.getLogger().severe("That user does not exist, or is not banned!!");
        }

        return new BanSerializer(banFile.read()).deserialize();
    }

    public boolean deleteBan(String fileName) {
        FileUtils banFile = new FileUtils(fileName + ".ban", "bans");
        getBans().remove(new BanSerializer(banFile.read()).deserialize());
        return banFile.getFile().delete();
    }

    public void loadAll() {
        File file = new File(plugin.getDataFolder(), "bans");
        if (!file.exists()) {
            file.mkdirs();
            return;
        }

        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();
        for (File temp : files) {
            FileUtils f = new FileUtils(temp);
            addBan(new BanSerializer(f.read()).deserialize());
        }
    }

    public String generate(AbstractBan ban) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GRAY + "You are banned!\n");
        sb.append(ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + ban.getBanMessage() + "\n");
        sb.append(ChatColor.WHITE + "Banned by: " + ChatColor.RED + ban.getPunisher() + "\n");
        sb.append(ChatColor.WHITE + "Expiry Date: " + ChatColor.BLUE + ban.getBanExpiry() + "\n");
        sb.append(ChatColor.WHITE + "Ban ID: " + ChatColor.GREEN + ban.getBanId() + "\n");
        sb.append(ChatColor.GOLD + "Appeal at https://www.unraveledmc.com");
        return sb.toString();
    }

    public static boolean isBanned(String playerName) {
        for (AbstractBan ban : bans) {
            if (ban.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBanned(UUID uuid) {
        for (AbstractBan ban : bans) {
            if (ban.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBanned(Player player) {
        for (AbstractBan ban : bans) {
            if (Bukkit.getOfflinePlayer(ban.getUuid()).equals(player)) {
                return true;
            }
            if (Bukkit.getPlayer(ban.getUuid()).equals(player)) {
                return true;
            }
        }
        return false;
    }
}
