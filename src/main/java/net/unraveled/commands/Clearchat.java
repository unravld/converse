package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(description="Clears the chat.", usage="/<command>", aliases="cc")
public class Clearchat extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.clearchat")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        for (int i=0; i < 100; i++) {
            Bukkit.broadcastMessage(" ");
        }

        Util.action(sender, "Clearing the chat");

        return true;
    }
}
