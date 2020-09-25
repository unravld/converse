package net.unraveled.commands;

import net.unraveled.ConversePlugin;
import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Objects;

@CommandParameters(description = "Changes the server mode.", usage = "/<command> [event | dev | staff | off]")
public class Mode extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 1) {
            return false;
        }

        String mode = getPlugin().config.getString("mode");

        if (args.length == 1) {
            switch (args[0]) {
                case "dev": {
                    if (!sender.hasPermission("converse.mode.dev")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    if (Objects.requireNonNull(mode).equalsIgnoreCase("dev")) {
                        plugin.ml.disableDevMode();
                    } else {
                        plugin.ml.enableDevMode();
                    }
                    return true;
                }
                case "event": {
                    if (!sender.hasPermission("converse.mode.event")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    if (Objects.requireNonNull(mode).equalsIgnoreCase("event")) {
                        plugin.ml.disableEventMode();
                    } else {
                        plugin.ml.enableEventMode();
                    }
                    return true;
                }
                case "staff": {
                    if (!sender.hasPermission("converse.mode.staff")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    if (Objects.requireNonNull(mode).equalsIgnoreCase("staff")) {
                        plugin.ml.disableStaffMode();
                    } else {
                        plugin.ml.enableStaffMode();
                    }
                    return true;
                }
                case "off": {
                    if (Objects.requireNonNull(mode).equalsIgnoreCase("default")) {
                        sender.sendMessage(ChatColor.GRAY + "You are already in the default mode.");
                        return true;
                    }
                    getPlugin().config.set("mode", "default");
                    Util.action(sender, "The server has re-opened to everyone.");
                    return true;
                }
                default:
                    return false;
            }
        }
        sender.sendMessage(ChatColor.GRAY + "Available server modes:");
        sender.sendMessage(ChatColor.GOLD + "Dev: " + ChatColor.GRAY + "Plugin testing status for leadership and developers.");
        sender.sendMessage(ChatColor.GOLD + "Staff: " + ChatColor.GRAY + "Restricts the server to staff members only.");
        sender.sendMessage(ChatColor.GOLD + "Event: " + ChatColor.GRAY + "Whitelist all online players and closes the server.");
        sender.sendMessage(ChatColor.GOLD + "Default: " + ChatColor.GRAY + "Normal server functionality.");
        sender.sendMessage(ChatColor.GRAY + "The server is currently running in " + mode + " mode.");
        return true;
    }
}
