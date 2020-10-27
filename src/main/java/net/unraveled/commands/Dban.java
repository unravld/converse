package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.bans.BanType;
import net.unraveled.bans.BanUUID;
import net.unraveled.bans.SimpleBan;
import net.unraveled.commands.loader.Messages;
import net.unraveled.playerdata.PlayerData;
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

@CommandParameters(description = "Bans a player for a day.", usage = "/<command> <player> [reason] [-r]", aliases = "dayban, gtfo")
public class Dban extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.dban")) {
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

            Date expires = Date.from(new Date().toInstant().plus(1L, ChronoUnit.DAYS));

            String reason;
            boolean rollback = false;
            if (!args[args.length - 1].equalsIgnoreCase("-r")) {
                reason = StringUtils.join(org.apache.commons.lang.ArrayUtils.subarray(args, 1, args.length), " ");
            } else {
                reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length - 1), " ");
                rollback = true;
            }

            Util.action(sender, "Banning " + player.getName() + (!reason.isEmpty() ? " for: " + reason : ""));
            if (reason.isEmpty()) reason = "You have been temporarily banned from this server.";
            SimpleBan ban = new SimpleBan((Player) player, sender, expires, BanUUID.newBanID(BanType.TEMPORARY), reason);
            plugin.bans.addBan(ban);

            PlayerData playerData = plugin.playerDataManager.getPlayerData(player.getUniqueId());
            playerData.getBans().add(ban);

            if (player.isOnline()) {
                Player p = Bukkit.getPlayer(player.getUniqueId());
                if (p != null) p.kickPlayer(plugin.bans.generate(ban));
            }

            if (rollback) Bukkit.dispatchCommand(sender, "co rb u:" + player.getName() + " t:24h r:global");
        } else return false;
        return true;
    }
}