package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description="Boo.", usage="/<command> <player>")
public class Scare extends CommandBase {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("converse.scare")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        sender.sendMessage(ChatColor.GRAY + "Scared " + player.getName());

        player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 4);
        for (int i = 0; i < 10; ++i) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 0);
        }

        return true;
    }
}
