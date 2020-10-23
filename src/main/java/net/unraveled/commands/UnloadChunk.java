package net.unraveled.commands;

import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.api.annotations.CommandParameters;
import net.unraveled.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(description = "Unloads any unoccupied chunks.", usage = "/<command>", aliases = "uc")
public class UnloadChunk extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        int numChunks = 0;

        for (World world : server.getWorlds()) {
            numChunks += unloadUnusedChunks(world);
        }

        Util.action(sender.getName(), "Unloading all unused chunks");
        sender.sendMessage(ChatColor.GRAY + "" + numChunks + " chunks unloaded.");
        return true;
    }

    private int unloadUnusedChunks(World world) {
        int numChunks = 0;

        for (Chunk chunk : world.getLoadedChunks()) {
            chunk.unload();
            numChunks++;
        }

        return numChunks;
    }
}
