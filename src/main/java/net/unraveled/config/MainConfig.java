package net.unraveled.config;

import java.io.File;

import net.unraveled.ConversePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public class MainConfig extends YamlConfiguration {
    private static MainConfig config;
    private final ConversePlugin plugin;
    private final File file;

    public MainConfig(ConversePlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            saveDefault();
        }
    }

    public void load() {
        try {
            super.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveDefault() {
        plugin.saveResource("config.yml", false);
    }
}
