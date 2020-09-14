package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.playerdata.PlayerData;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@CommandParameters(description = "Manages player powers.",
        usage = "/<command> <<player> <setting> <on|off> | reset <player> | resetall | list>")
public class Manage extends CommandBase {
    public static class ManagedSettings {
        private boolean commands = true;
        private boolean blockedit = true;
        private boolean movement = true;
        private boolean pvp = true;

        public ManagedSettings() {
        }

        public void setBlockedit(boolean blockedit) {
            this.blockedit = blockedit;
        }

        public void setCommands(boolean commands) {
            this.commands = commands;
        }

        public void setMovement(boolean movement) {
            this.movement = movement;
        }

        public void setPVP(boolean pvp) {
            this.pvp = pvp;
        }

        public boolean isBlockeditDisallowed() {
            return !blockedit;
        }

        public boolean isCommandsDisallowed() {
            return !commands;
        }

        public boolean isMovementDisallowed() {
            return !movement;
        }

        public boolean isPVPDisallowed() {
            return !pvp;
        }
    }

    public enum ManageableSetting {
        COMMANDS, BLOCKEDIT, MOVEMENT, PVP
    }

    // Usage: /manage <player> <setting> <on|off>
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("converse.manageplayer")) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[0]);
                OfflinePlayer oTarget = Bukkit.getOfflinePlayer(args[0]);
                if (target != null) {
                    if (!Util.canInteract(sender, target.getUniqueId())) {
                        sender.sendMessage(ChatColor.GRAY + "Couldn't restrict that player.");
                        return true;
                    }
                }
                String setting = args[1].toUpperCase();
                ManageableSetting matchedSetting = matchSetting(setting);
                if (matchedSetting != null) {
                    if (args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("enabled") || args[2].equalsIgnoreCase("true")) {
                        boolean processed = manageSetting(sender, target, oTarget, matchedSetting, true);
                        if (!processed) sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    } else if (args[2].equalsIgnoreCase("off") || args[2].equalsIgnoreCase("disabled") || args[2].equalsIgnoreCase("false")) {
                        boolean processed = manageSetting(sender, target, oTarget, matchedSetting, false);
                        if (!processed) sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    } else {
                        sender.sendMessage(ChatColor.GRAY + "Acceptable values: " + ChatColor.GRAY + "on or off, enabled or disabled, true or false");
                    }
                } else {
                    sender.sendMessage(ChatColor.GRAY + "Available settings: " + ChatColor.GRAY + Arrays.toString(ManageableSetting.values()));
                }
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatColor.GRAY + "Available settings: " + ChatColor.GRAY + Arrays.toString(ManageableSetting.values()));
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("resetall")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData pData = plugin.playerDataManager.getPlayerData(p);
                    pData.setManagedSettings(new ManagedSettings());
                }

                Util.action(sender, "Removing restrictions for all players");
                return true;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    PlayerData pData = plugin.playerDataManager.getPlayerData(target);
                    pData.setManagedSettings(new ManagedSettings());

                    Util.action(sender, "Removing restrictions for " + target.getName());
                } else {
                    sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                }

                return true;
            } else {
                return false;
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
    }

    public boolean manageSetting(CommandSender executor, @Nullable Player online, OfflinePlayer offline, ManageableSetting setting, boolean value) {
        PlayerData pData;
        if (online != null) {
            pData = plugin.playerDataManager.getPlayerData(online);
        } else if (plugin.playerDataManager.doesPlayerDataExist(offline.getUniqueId())) {
            pData = plugin.playerDataManager.getPlayerData(offline);
        } else {
            pData = null;
        }

        if (pData != null) {
            switch (setting) {
                case COMMANDS:
                    pData.getManagedSettings().setCommands(value);
                    break;
                case BLOCKEDIT:
                    pData.getManagedSettings().setBlockedit(value);
                    break;
                case MOVEMENT:
                    pData.getManagedSettings().setMovement(value);
                    break;
                case PVP:
                    pData.getManagedSettings().setPVP(value);
                    break;
            }

            Util.action(executor, (value ? "Disabling" : "Enabling") + " " + Bukkit.getOfflinePlayer(pData.getUUID()).getName() + "'s restriction for " + setting.toString().toUpperCase());
        } else {
            return false;
        }

        return true;
    }

    private ManageableSetting matchSetting(String s) {
        for (ManageableSetting e : ManageableSetting.values()) {
            if (e.toString().toLowerCase().startsWith(s.toLowerCase())) return e;
        }

        return null;
    }
}
