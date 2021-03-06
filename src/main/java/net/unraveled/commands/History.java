package net.unraveled.commands;

import net.unraveled.api.abstracts.AbstractBan;
import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@CommandParameters(description = "Searches for a ban by username.", usage = "/<command> <player>")
public class History extends CommandBase {
    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.history")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        OfflinePlayer target = getOfflinePlayer(args[0]);
        if ((!target.hasPlayedBefore() && !target.isOnline()) ||
                !plugin.playerDataManager.doesPlayerDataExist(target.getUniqueId())) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        UUID targetUUID = target.getUniqueId();
        PlayerData pData = plugin.playerDataManager.getPlayerData(targetUUID);
        if (pData.getBans() == null || pData.getBans().isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "Couldn't find any history under that player.");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        ArrayList<AbstractBan> reversedBans = new ArrayList<>(pData.getBans());
        Collections.reverse(reversedBans);
        for (AbstractBan ban : reversedBans) {
            sb.append(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "#").append(ban.getBanId())
                    .append(ChatColor.DARK_GRAY).append("]");
            sb.append("\n");
            sb.append(ChatColor.GRAY + "Issuer: " + ChatColor.GOLD)
                    .append(ban.getIssuer() != null ? Bukkit.getOfflinePlayer(ban.getIssuer())
                            .getName() : "CONSOLE");
            sb.append("\n");
            sb.append(ChatColor.GRAY + "Expiration: " + ChatColor.GOLD)
                    .append(!ban.getBanId().startsWith("P") ? ban.getBanExpiry().toString() : "Never");
            sb.append("\n");
            sb.append(ChatColor.GRAY + "Issued upon: " + ChatColor.GOLD)
                    .append(ban.getIssueDate().toString());
            sb.append("\n");
            sb.append(ChatColor.GRAY + "Reason: " + ChatColor.GOLD)
                    .append(ban.getBanMessage() != null ? ban.getBanMessage() : "No reason");
            sb.append("\n");
        }

        sender.sendMessage(sb.toString());

        return true;
    }
}
