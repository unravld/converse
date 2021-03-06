package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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

        ChatListener.setPunished(player, !ChatListener.isPunished(player));
        sender.sendMessage(ChatColor.GRAY + "Turning "
                + (ChatListener.isPunished(player) ? "on" : "off")
                + " black chat for " + player.getName() + ".");

        return true;
    }
}
