package net.unraveled.commands;

import net.unraveled.ConversePlugin;
import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

@CommandParameters(description = "Describes information about Converse.", usage = "/<command> [reload | debug]")
public class Converse extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            ConversePlugin.BuildProperties build = ConversePlugin.build;
            sender.sendMessage(
                    ChatColor.GRAY + "Converse is a plugin for managing permissions and staff moderation.");
            sender.sendMessage(ChatColor.GRAY + String.format("Version: " + ChatColor.GOLD + "%s.%s.%s",
                    build.version,
                    build.number,
                    build.head));
            sender.sendMessage(String.format(ChatColor.GRAY + "Compiled on " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " by " + ChatColor.GOLD + "%s" + ChatColor.GRAY + ".",
                    build.date,
                    build.author));
            sender.sendMessage(ChatColor.GRAY + "Go to " + ChatColor.GOLD + "https://github.com/unravld/converse" + ChatColor.GRAY + " for more info.");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "debug": {
                if (!sender.hasPermission("converse.debug")) {
                    sender.sendMessage(Messages.NO_PERMISSION);
                    return true;
                }
                Player player = (Player) sender;
                if (plugin.lp.isStaff(player.getUniqueId())) {
                    sender.sendMessage(ChatColor.GRAY + "You are staff.");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "You aren't staff.");
                }
                sender.sendMessage(ChatColor.GRAY + "User directory: " + plugin.getDataFolder() + File.separator + "players" + File.separator + player.getName() + ".yml");
                return true;
            }
            case "reload": {
                if (!sender.hasPermission("converse.reload")) {
                    sender.sendMessage(Messages.NO_PERMISSION);
                    return true;
                }
                plugin.registerConfigs();
                sender.sendMessage(ChatColor.GRAY + "Converse files have been reloaded.");
                return true;
            }
            default:
                return false;
        }
    }
}
