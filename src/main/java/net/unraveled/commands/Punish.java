package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.util.Punisher;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Punishes a player.", usage = "/<command> <player> <freeze, block, spoof, dummy>")
public class Punish extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length != 2) return false;

        String name = args[0];
        String type = args[1];

        Player player = Bukkit.getPlayer(name);
        if (!Punisher.getB().contains(type)) {
            sender.sendMessage(ChatColor.GRAY + "Invalid, please use: ["
                    + StringUtils.join(Punisher.getB(), ",")
                    + "]");
            return true;
        }
        if (Punisher.getA().containsKey(player)) {
            Punisher.removeFromA(player);
            sender.sendMessage(ChatColor.GRAY + player.getName() + " has been removed from the punishment list.");
            return true;
        }
        Punisher.addToA(player, type);
        sender.sendMessage(ChatColor.GRAY + player.getName() + " has been added to the punishment list with type " + type + ".");
        return true;
    }
}
