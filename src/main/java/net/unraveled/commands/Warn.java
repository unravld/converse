package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description="Warns a user.", usage="/<command> <player> <reason>")
public class Warn extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("converse.warn")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (sender instanceof Player) {
            if (player.equals(sender)) {
                sender.sendMessage(ChatColor.RED + "You are an idiot. Don't try to do that.");
                return true;
            }
        }

        String reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        player.sendMessage(ChatColor.RED + "You have been warned by " + sender.getName() + ": " + reason);
        sender.sendMessage(ChatColor.GRAY + "Warning successfully sent to " + player.getName() + ".");

        return true;
    }
}
