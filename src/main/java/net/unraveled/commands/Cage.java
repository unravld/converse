package net.unraveled.commands;

import net.unraveled.commands.loader.CommandBase;
import net.unraveled.commands.loader.CommandParameters;
import net.unraveled.commands.loader.Messages;
import net.unraveled.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@CommandParameters(description = "Encages a player.", usage = "/<command> <<player> [block] | purge>")
public class Cage extends CommandBase {
    public class Cager {
        private UUID uuid;
        private Material cageMaterial;
        public final Map<Location, Material> previousBlocks = new HashMap<>();

        public Cager(Player player, Material cageMaterial) {
            this.uuid = player.getUniqueId();
            this.cageMaterial = cageMaterial;
        }

        public void createCage() {
            final Block center = Objects.requireNonNull(Bukkit.getPlayer(uuid)).getLocation().getBlock();
            for (int xOffset = -2; xOffset <= 2; xOffset++) {
                for (int yOffset = -2; yOffset <= 2; yOffset++) {
                    for (int zOffset = -2; zOffset <= 2; zOffset++) {
                        if (Math.abs(xOffset) != 2 && Math.abs(yOffset) != 2 && Math.abs(zOffset) != 2) {
                            final Block block = center.getRelative(xOffset, yOffset, zOffset);
                            previousBlocks.put(block.getLocation(), block.getType());
                            block.setType(Material.AIR);
                        } else {
                            final Block block = center.getRelative(xOffset, yOffset, zOffset);
                            previousBlocks.put(block.getLocation(), block.getType());
                            block.setType(cageMaterial);
                        }
                    }
                }
            }

        }

        public void undo() {
            for (Location loc : previousBlocks.keySet()) {
                loc.getBlock().setType(previousBlocks.get(loc));
            }
        }
    }

    // Usage: /cage <player> [block]
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("converse.cage")) {
            if (args.length == 0) return false;
            else {
                if (!args[0].equalsIgnoreCase("purge")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!plugin.cgl.cages.containsKey(target.getUniqueId())) {
                            if (Util.canInteract(sender, target.getUniqueId())) {
                                Material cageMaterial = Material.GLASS;
                                if (args.length > 1) {
                                    Material matchedMaterial = Material.matchMaterial(args[1]);
                                    ;
                                    if (matchedMaterial != null) cageMaterial = matchedMaterial;
                                }

                                Cager cage = new Cager(target, cageMaterial);
                                cage.createCage();

                                plugin.cgl.cages.put(target.getUniqueId(), cage);
                                Util.action(sender, "Caging " + target.getName());
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "Couldn't cage this person.");
                            }
                        } else {
                            plugin.cgl.cages.get(target.getUniqueId()).undo();
                            plugin.cgl.cages.remove(target.getUniqueId());
                            Util.action(sender, "Uncaging " + target.getName());
                        }
                    } else {
                        sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                    }
                } else {
                    Util.action(sender, "Uncaging all players");
                    for (UUID u : plugin.cgl.cages.keySet()) {
                        Cager cage = plugin.cgl.cages.get(u);
                        cage.undo();
                    }
                    plugin.cgl.cages.clear();
                }
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSION);
        }
        return true;
    }
}
