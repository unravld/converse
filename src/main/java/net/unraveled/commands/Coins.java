package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Checks your coin balance.", usage = "/<command>")
public class Coins extends CommandBase {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lbl, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.GRAY + "Couldn't do that.");
            return true;
        }

        Player p = (Player) cs;
        int tCoins = plugin.playerDataManager.getPlayerData(p).getCoins();
        if (tCoins == 0) {
            p.sendMessage(ChatColor.GRAY + "You don't have any coins.");
            return true;
        }
        p.sendMessage(ChatColor.GRAY + "You have " + tCoins + " coins.");
        return true;
    }
}
