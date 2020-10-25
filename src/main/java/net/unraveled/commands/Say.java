package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(description = "Broadcasts a message to the server.",
        usage = "/<command> <message>",
        aliases = "shout, announce, broadcast")
public class Say extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("converse.say")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        String msg = "";

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "Incorrect syntax.");
            return false;
        } else {
            String builder = ChatColor.AQUA + "[" + "Server:" + sender.getName() + "] " +
                    StringUtils.join(args, ' ');
            Bukkit.broadcastMessage(builder);

        }

        return true;
    }
}
