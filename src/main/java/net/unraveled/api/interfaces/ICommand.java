package net.unraveled.api.interfaces;

import net.unraveled.ConversePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.List;

public interface ICommand extends TabCompleter {
    void setup(final ConversePlugin plugin, final CommandSender cs, final Class<?> clazz);

    boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args);

    ConversePlugin getPlugin();
}
