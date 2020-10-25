package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.bans.BanType;
import net.unraveled.bans.BanUUID;
import net.unraveled.bans.SimpleBan;
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

import java.util.Date;

@CommandParameters(description = "Permanently bans a player.", usage = "/<command> <player> [reason] [-r]")
public class Permban extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.permban")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if (args.length >= 1) {
            OfflinePlayer player = getOfflinePlayer(args[0]);
            if (!player.hasPlayedBefore() && !player.isOnline()) {
                sender.sendMessage(ChatColor.GRAY + "Couldn't find that player.");
                return true;
            }

            if (!Util.isStaff(player)) {
                sender.sendMessage(ChatColor.GRAY + "Couldn't ban that player.");
                return true;
            }

            if (plugin.bans.isBanned(player.getUniqueId())) {
                sender.sendMessage(ChatColor.GRAY + "That player is already banned.");
                return true;
            }

            String reason;
            boolean rollback = false;
            if (!args[args.length - 1].equalsIgnoreCase("-r")) {
                reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
            } else {
                reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length - 1), " ");
                rollback = true;
            }

            if (reason.isEmpty()) {
                reason = "Not Otherwise Specified";
            }
            SimpleBan ban = new SimpleBan((Player) player, sender, null, BanUUID.newBanID(BanType.PERMANENT), reason);
            plugin.bans.addBan(ban);
            Util.action(sender,
                    "Permanently banning " + player.getName() + (!reason.isEmpty() ? " for: " + reason : ""));
            if (player.isOnline()) {
                Player p = Bukkit.getPlayer(player.getUniqueId());
                if (p != null) p.kickPlayer(ban.getBanMessage());
            }

            if (rollback) Bukkit.dispatchCommand(sender, "co rb u:" + player.getName() + " t:24h r:global");
        } else return false;
        return true;
    }
}
