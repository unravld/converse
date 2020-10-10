package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@CommandParameters(description = "Temporarily bans a player.", usage = "/<command> <player> <duration> [reason] [-r]", aliases = "eban, banip, ebanip, tempban, etempban")
public class Ban extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.ban")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if (args.length >= 2) {
            OfflinePlayer player = getOfflinePlayer(args[0]);
            if (!player.hasPlayedBefore() && !player.isOnline()) {
                sender.sendMessage(ChatColor.GRAY + "Couldn't find that player.");
                return true;
            }

            if (!Util.isStaff(player)) {
                sender.sendMessage(ChatColor.GRAY + "Couldn't ban that player.");
                return true;
            }

            if (plugin.banManager.isPlayerBanned(player.getUniqueId())) {
                sender.sendMessage(ChatColor.GRAY + "That player is already banned.");
                return true;
            }

            Date expires;
            if (Util.parseDateOffset(args[1]) != null) {
                expires = Util.parseDateOffset(args[1]);
            } else {
                expires = Date.from(new Date().toInstant().plus(1L, ChronoUnit.DAYS));
            }

            String reason;
            boolean rollback = false;
            if (!args[args.length - 1].equalsIgnoreCase("-r")) {
                reason = StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ");
            } else {
                reason = StringUtils.join(ArrayUtils.subarray(args, 2, args.length - 1), " ");
                rollback = true;
            }

            Util.action(sender, "Banning " + player.getName() + (!reason.isEmpty() ? " for: " + reason : ""));
            BanData ban = new BanData();
            if (sender instanceof Player) ban.setStaffUUID(((Player) sender).getUniqueId());
            ban.setPlayerUUID(player.getUniqueId());
            ban.setBanExpiration(expires);
            ban.setBanType(BanType.TEMPORARY);
            if (!reason.isEmpty()) ban.setReason(reason);
            ban.setDateIssued(new Date());
            plugin.banManager.addBan(ban);
            if (player.isOnline()) {
                Player p = Bukkit.getPlayer(player.getUniqueId());
                if (p != null) p.kickPlayer(plugin.banManager.getBanMessage(ban));
            }

            if (rollback) Bukkit.dispatchCommand(sender, "co rb u:" + player.getName() + " t:24h r:global");
        } else return false;
        return true;
    }
}
