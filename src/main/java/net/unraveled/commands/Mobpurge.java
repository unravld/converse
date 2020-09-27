package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;


@CommandParameters(description = "Purges all mobs in all worlds.", usage ="/<command>", aliases = "mp")
public class Mobpurge extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("converse.mobpurge")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        Util.action(sender, "Purging all mobs");

        int removed = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity ent : world.getLivingEntities()) {
                if (ent instanceof Creature || ent instanceof Ghast || ent instanceof Slime || ent instanceof EnderDragon || ent instanceof Ambient) {
                    ent.remove();
                    removed++;
                }
            }
        }

        return true;
    }

}
