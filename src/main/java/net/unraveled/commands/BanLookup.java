package net.unraveled.commands;

import net.unraveled.bans.BanData;
import net.unraveled.bans.BanType;
import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

@CommandParameters(description = "Searches for a ban by identifier.", usage = "/<command> <id>", aliases = "bl")
public class BanLookup extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.banlookup")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        String id = args[0];
        if (plugin.banManager.getBanFromID(id) == null) {
            sender.sendMessage(ChatColor.GRAY + "Couldn't find any bans under that identifier.");
            return true;
        }

        BanData ban = plugin.banManager.getBanFromID(id);

        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "#" + ban.getBanID() + ChatColor.DARK_GRAY + "]");
        sb.append("\n");
        sb.append(ChatColor.GRAY + "Player: " + ChatColor.GOLD + Bukkit.getOfflinePlayer(ban.getPlayerUUID()).getName());
        sb.append("\n");
        sb.append(ChatColor.GRAY + "Staffmember: " + ChatColor.GOLD + (ban.getStaffUUID() != null ? Bukkit.getOfflinePlayer(ban.getStaffUUID()).getName() : "CONSOLE"));
        sb.append("\n");
        sb.append(ChatColor.GRAY + "Expiration: " + ChatColor.GOLD + (ban.getBanType() != BanType.PERMANENT ? plugin.banManager.formatDate(ban.getBanExpiration()) : "Never"));
        sb.append("\n");
        sb.append(ChatColor.GRAY + "Issued upon: " + ChatColor.GOLD + plugin.banManager.formatDate(ban.getDateIssued()));
        sb.append("\n");
        sb.append(ChatColor.GRAY + "Reason: " + ChatColor.GOLD + (ban.getReason() != null ? ban.getReason() : "No reason"));
        sb.append("\n");

        sender.sendMessage(sb.toString());

        return true;
    }
}
