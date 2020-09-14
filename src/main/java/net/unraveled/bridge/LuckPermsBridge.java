package net.unraveled.bridge;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.manager.UserManager;
import net.unraveled.ConversePlugin;
import net.unraveled.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LuckPermsBridge {
    private final ConversePlugin plugin;
    private final LuckPermsApi api = ConversePlugin.getLuckPermsAPI();

    public LuckPermsBridge(ConversePlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isModerator(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.mod"));
    }

    public boolean isAdministrator(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.admin"));
    }

    public boolean isDeveloper(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.developer"));
    }

    public boolean isExecutive(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.executive"));
    }

    public boolean isArchitect(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.architect"));
    }

    public boolean isVoter(UUID player) {
        assert api != null;
        User user = api.getUserManager().getUser(player);
        assert user != null;
        return user.getPrimaryGroup().equalsIgnoreCase(plugin.config.getString("permissions.voter"));
    }

    public boolean isStaff(UUID player) {
        return isModerator(player) || isAdministrator(player) || isDeveloper(player) || isExecutive(player);
    }

    public ChatColor nameColor(Player player) {
        if (player == null) {
            return ChatColor.RESET;
        } else if (isModerator(player.getUniqueId())) {
            return ChatColor.GREEN;
        } else if (isAdministrator(player.getUniqueId())) {
            return ChatColor.YELLOW;
        } else if (isDeveloper(player.getUniqueId())) {
            return ChatColor.LIGHT_PURPLE;
        } else if (isExecutive(player.getUniqueId())) {
            return ChatColor.RED;
        } else if (isArchitect(player.getUniqueId())) {
            return ChatColor.BLUE;
        } else if (isVoter(player.getUniqueId())) {
            return ChatColor.AQUA;
        } else {
            return ChatColor.RESET;
        }
    }

    public String displayRank(Player player) {
        if (player == null) {
            return ChatColor.DARK_AQUA + "C";
        }

        if (isModerator(player.getUniqueId())) {
            return ChatColor.DARK_GREEN + "M";
        } else if (isAdministrator(player.getUniqueId())) {
            return ChatColor.GOLD + "A";
        } else if (isDeveloper(player.getUniqueId())) {
            return ChatColor.DARK_PURPLE + "D";
        } else if (isExecutive(player.getUniqueId())) {
            return ChatColor.DARK_RED + "E";
        } else if (isArchitect(player.getUniqueId())) {
            return ChatColor.DARK_BLUE + "A";
        } else if (isVoter(player.getUniqueId())) {
            return ChatColor.DARK_AQUA + "V";
        } else {
            return "";
        }
    }

    public ChatColor displayRankColor(Player player) {
        if (player == null) {
            return ChatColor.DARK_AQUA;
        }

        if (isModerator(player.getUniqueId())) {
            return ChatColor.DARK_GREEN;
        } else if (isAdministrator(player.getUniqueId())) {
            return ChatColor.GOLD;
        } else if (isDeveloper(player.getUniqueId())) {
            return ChatColor.DARK_PURPLE;
        } else if (isExecutive(player.getUniqueId())) {
            return ChatColor.DARK_RED;
        } else if (isArchitect(player.getUniqueId())) {
            return ChatColor.DARK_BLUE;
        }

        return ChatColor.RESET;
    }

    public void set(UUID uuid, String group) {
        assert api != null;
        UserManager userManager = api.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uuid);

        userFuture.thenAcceptAsync(user ->
        {
            Node oldGroup = api.buildNode("group." + user.getPrimaryGroup()).build();
            Node newGroup = api.buildNode("group." + group).build();
            if (oldGroup == newGroup) {
                return;
            }
            user.unsetPermission(oldGroup);
            user.setPermission(newGroup);
            userManager.saveUser(user);
        });
    }
    //

    public void setPrefix(UUID uuid, String permission) {
        assert api != null;
        UserManager userManager = api.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uuid);

        userFuture.thenAcceptAsync(user ->
        {
            Date expires = Util.parseDateOffset("1m");
            long longExpires = Util.getUnixTime(expires);
            Node prefix = api.buildNode(permission).setExpiry(longExpires).build();
            user.setPermission(prefix);
            userManager.saveUser(user);

        });
    }

    public void allowStaffWorld(UUID uuid) {
        assert api != null;
        UserManager userManager = api.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uuid);

        userFuture.thenAcceptAsync(user ->
        {
            Node permission = api.buildNode("multiverse.access.staffworld").build();
            Node command = api.buildNode("converse.staffworld").build();
            user.setPermission(permission);
            user.setPermission(command);
            userManager.saveUser(user);
        });
    }

    public void disallowStaffWorld(UUID uuid) {
        assert api != null;
        UserManager userManager = api.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uuid);

        userFuture.thenAcceptAsync(user ->
        {
            Node permission = api.buildNode("multiverse.access.staffworld").build();
            Node command = api.buildNode("converse.staffworld").build();
            user.unsetPermission(permission);
            user.unsetPermission(command);
            userManager.saveUser(user);
        });
    }
}
