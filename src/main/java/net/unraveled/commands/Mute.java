package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Mutes a player or purges mutes.", usage = "/<command> <player | purge>", aliases = "stfu, emute")
public class Mute extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.mute")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("purge")) {
            int amount = plugin.mul.getMutedAmount();
            plugin.mul.purgeMuted();
            Util.action(sender, "Unmuting all players");
            sender.sendMessage(ChatColor.GRAY + "Unmuted " + amount + " players.");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (plugin.lp.isStaff(player.getUniqueId())) {
            sender.sendMessage(ChatColor.GRAY + "Couldn't mute that player.");
            return true;
        }
        plugin.mul.setMuted(player, !plugin.mul.isMuted(player));
        Util.action(sender, (plugin.mul.isMuted(player) ? "M" : "Unm") + "uting " + player.getName());
        return true;
    }
}
