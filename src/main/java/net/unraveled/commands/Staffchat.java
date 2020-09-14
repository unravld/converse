package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Prompts and toggles private discussion with staffmembers.", usage = "/<command> [message]", aliases = "o, sc, ac, adminchat")
public class Staffchat extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.staffchat")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        String message = StringUtils.join(args, " ");
        if (message.length() == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.GRAY + "Staffchat can only be toggled in-game.");
                return true;
            }
            Player p = (Player) sender;
            Util.putStaffChat(p.getUniqueId());
            p.sendMessage(ChatColor.GRAY + "Toggled staffchat " + (Util.isInStaffChat(p.getUniqueId()) ? "on" : "off") + ".");
            return true;
        }
        Util.staffchat(sender, message);
        return true;
    }
}
