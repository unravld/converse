package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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

        String sb = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "#" + ban.getBanID() + ChatColor.DARK_GRAY +
                "]" +
                "\n" +
                ChatColor.GRAY + "Player: " + ChatColor.GOLD +
                Bukkit.getOfflinePlayer(ban.getPlayerUUID()).getName() +
                "\n" +
                ChatColor.GRAY + "Staffmember: " + ChatColor.GOLD +
                (ban.getStaffUUID() != null ? Bukkit.getOfflinePlayer(ban.getStaffUUID()).getName() : "CONSOLE") +
                "\n" +
                ChatColor.GRAY + "Expiration: " + ChatColor.GOLD +
                (ban.getBanType() != BanType.PERMANENT ? plugin.banManager
                        .formatDate(ban.getBanExpiration()) : "Never") +
                "\n" +
                ChatColor.GRAY + "Issued upon: " + ChatColor.GOLD +
                plugin.banManager.formatDate(ban.getDateIssued()) +
                "\n" +
                ChatColor.GRAY + "Reason: " + ChatColor.GOLD +
                (ban.getReason() != null ? ban.getReason() : "No reason") +
                "\n";
        sender.sendMessage(sb);

        return true;
    }
}
