package net.unraveled.commands;

import me.lucko.luckperms.api.LuckPermsApi;
import net.unraveled.ConversePlugin;
import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Manages staffmembers.",
        usage = "/<command> <add <player> <rank> | remove <player>>",
        aliases = "saconfig")
public class Staff extends CommandBase {
    private static LuckPermsApi api = ConversePlugin.getLuckPermsAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!sender.hasPermission("converse.staff")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "add": {
                if (args.length < 3) {
                    return false;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                if (args[2].equalsIgnoreCase("moderator") || args[2].equalsIgnoreCase("mod")) {
                    plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.mod"));
                    Util.action(sender, "Adding " + player.getName() + " to Mod");
                    return true;
                } else if (args[2].equalsIgnoreCase("administrator") || args[2].equalsIgnoreCase("admin")) {
                    if (!sender.hasPermission("converse.staff.add.admin")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.admin"));
                    Util.action(sender, "Adding " + player.getName() + " to Admin");
                    return true;
                } else if (args[2].equalsIgnoreCase("developer") || args[2].equalsIgnoreCase("dev")) {
                    if (!sender.hasPermission("converse.staff.add.developer")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.developer"));
                    Util.action(sender, "Adding " + player.getName() + " to Developer");
                    return true;
                } else if (args[2].equalsIgnoreCase("executive") || args[2].equalsIgnoreCase("exec")) {
                    if (!sender.hasPermission("converse.staff.add.executive")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.executive"));
                    Util.action(sender, "Adding " + player.getName() + " to Executive");
                    return true;
                } else if (args[2].equalsIgnoreCase("architect") || args[2].equalsIgnoreCase("arch")) {
                    if (!sender.hasPermission("converse.staff.add.architect")) {
                        sender.sendMessage(Messages.NO_PERMISSION);
                        return true;
                    }
                    plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.architect"));
                    Util.action(sender, "Adding " + player.getName() + " to Architect");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Please enter a valid rank!");
                    return true;
                }
            }
            case "remove": {
                if (!sender.hasPermission("converse.staff.remove")) {
                    sender.sendMessage(Messages.NO_PERMISSION);
                    return true;
                }

                if (args.length < 2) {
                    return false;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                plugin.lp.set(player.getUniqueId(), ConversePlugin.plugin.config.getString("permissions.op"));
                Util.action(sender, "Removing " + player.getName() + " from staff");
                return true;
            }
            default:
                return false;
        }
    }
}
