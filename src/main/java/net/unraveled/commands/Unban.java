package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(description = "Unbans a player.",
        usage = "/<command> <player>",
        aliases = "pardon, epardon, unpermban")
public class Unban extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.unban")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (!plugin.playerDataManager.doesPlayerDataExist(offlinePlayer.getUniqueId())) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (!plugin.banManager.isPlayerBanned(offlinePlayer.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "That player isn't banned.");
            return true;
        }

        Util.action(sender, "Unbanning " + offlinePlayer.getName());
        plugin.banManager.removeBanFromHistory(plugin.banManager.getLatestBan(offlinePlayer.getUniqueId()));
        return true;
    }
}
