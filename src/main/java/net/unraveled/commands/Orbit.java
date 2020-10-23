package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandParameters(description = "Toggles a player's orbited state.", usage = "/<command> <player> <on | off>")
public class Orbit extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("converse.orbit")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if (args.length == 0) return false;
        Boolean orbitStatus;
        Player targetPlayer=Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }
        UUID targetPlayerUUID=targetPlayer.getUniqueId();
        if(args.length==2){
            boolean newSetting;
            switch(args[1].toLowerCase()){
                case "on":
                    newSetting=true;
                    break;
                case "off":
                    newSetting=false;
                    break;
                default:
                    return false;
            }
            orbitStatus=Util.setOrbit(targetPlayerUUID,newSetting);
        }else orbitStatus=Util.toggleOrbit(targetPlayerUUID);
        sender.sendMessage(ChatColor.GRAY + "Set orbit to " + (orbitStatus ? "on" : "off") + " for player " + args[0] + ".");
        return true;
    }
}
