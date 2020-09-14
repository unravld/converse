package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Makes a player's chat messages show up in black.", usage = "/<command> <player>", aliases = "darkchat, graychat")
public class BlackChat extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.blackchat")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (!ChatListener.isPunished(player)) {
            ChatListener.setPunished(player, true);
            sender.sendMessage(ChatColor.GRAY + "Turning on black chat for " + player.getName() + ".");
            return true;
        } else {
            ChatListener.setPunished(player, false);
            sender.sendMessage(ChatColor.GRAY + "Turning off black chat for " + player.getName() + ".");
            return true;
        }
    }
}
