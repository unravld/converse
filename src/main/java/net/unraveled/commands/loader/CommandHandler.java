package net.unraveled.commands.loader;

import net.unraveled.ConversePlugin;
import net.unraveled.api.abstracts.CommandBase;
import net.unraveled.util.ConverseBase;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler extends ConverseBase {
    public static final String COMMAND_PATH = plugin.reflect.getLocation();

    public static boolean handle(CommandSender sender, Command cmd, String lbl, String[] args) {
        final Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            Bukkit.getLogger().info(String.format("[PLAYER COMMAND] %s (%s): /%s %s",
                    player.getName(),
                    ChatColor.stripColor(player.getDisplayName()),
                    lbl,
                    StringUtils.join(args, ' ')));
        } else {
            Bukkit.getLogger().info(String.format("[CONSOLE COMMAND] %s: /%s %s",
                    sender.getName(),
                    lbl,
                    StringUtils.join(args, ' ')));
        }

        final CommandBase base;
        try {
            final ClassLoader loader = ConversePlugin.class.getClassLoader();
            base = (CommandBase) loader.loadClass(COMMAND_PATH + "." + cmd.getName()).newInstance();
            base.setup(plugin, sender, base.getClass());
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Couldn't load command: " + cmd.getName());
            Bukkit.getLogger().severe(ex.getMessage());

            sender.sendMessage(ChatColor.GRAY + "Couldn't load command: " + cmd.getName());
            return true;
        }

        try {
            return base.onCommand(sender, cmd, lbl, args);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Command error: " + lbl);
            ex.printStackTrace();
            sender.sendMessage(ChatColor.GRAY + "Command error: " + ex.getMessage());
        }

        return true;
    }
}
