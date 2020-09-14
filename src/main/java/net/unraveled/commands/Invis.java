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
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "Lists or clears any invisible players.", usage = "/<command> [clear]")
public class Invis extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.invis")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        boolean clear = false;
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                Util.action(sender, "Clearing invisibility for all players");
                clear = true;
            } else {
                return false;
            }
        }

        List<String> invisplayers = new ArrayList<>();
        int clears = 0;

        for (Player player : server.getOnlinePlayers()) {
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                invisplayers.add(player.getName());
                if (clear) {
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    clears++;
                }
            }
        }

        if (invisplayers.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "Couldn't find any invisible players.");
            return true;
        }

        if (clear) {
            sender.sendMessage(ChatColor.GRAY + "Cleared the invisibility effect from " + clears + " players: " + StringUtils.join(invisplayers, ", "));
        } else {
            sender.sendMessage(ChatColor.GRAY + "Invisible players (" + invisplayers.size() + "): " + StringUtils.join(invisplayers, ", "));
        }

        return true;
    }
}
