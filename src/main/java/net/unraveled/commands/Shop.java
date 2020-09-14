package net.unraveled.commands;

import net.unraveled.ConversePlugin;
import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.util.Punisher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "Opens the shop menu.", usage = "/<command>", aliases = "umcshop")
public class Shop extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Couldn't execute that command from console.");
            return true;
        }

        Player p = (Player) sender;
        ConversePlugin.plugin.shop.open(p);
        return true;
    }
}