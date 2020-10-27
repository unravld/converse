package net.unraveled;

import me.lucko.luckperms.api.LuckPermsApi;
import net.unraveled.bans.BanSystem;
import net.unraveled.bridge.LuckPermsBridge;
import net.unraveled.commands.Cage;
import net.unraveled.commands.Converse;
import net.unraveled.commands.loader.CommandHandler;
import net.unraveled.commands.loader.CommandLoader;
import net.unraveled.config.MainConfig;
import net.unraveled.listeners.*;
import net.unraveled.playerdata.PlayerDataListener;
import net.unraveled.playerdata.PlayerDataManager;
import net.unraveled.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

public class ConversePlugin extends JavaPlugin {
    public static final BuildProperties build = new BuildProperties();
    public static Server server;
    public static Util util;
    // Configs
    public MainConfig config;
    public PlayerOrganizer po;
    // LuckPerms
    public LuckPermsBridge lp;
    // Shop
    public ShopListener shl;
    // Listeners
    public BanListener bl;
    public ChatListener cl;
    public ModeListener ml;
    public MuteListener mul;
    public TabListener sl;
    public WorldListener wl;
    public CageListener cgl;
    public PlaytimeListener ptl;
    public PlayerDataListener pdl;
    public ManageListener mgrl;
    public BanSystem bans;

    // Player Data
    public PlayerDataManager playerDataManager;
    // Reflections
    public Reflect reflect;

    @Override
    public void onLoad() {
        server = this.getServer();
        config = new MainConfig(this);
        reflect = new Reflect(Converse.class);
    }

    @Override
    public void onEnable() {
        // Bans
        bans = new BanSystem();
        bans.loadAll();
        // Config
        registerConfigs();
        // BuildProperties
        build.load(this);
        server.getScheduler().runTaskTimer(this, (new Recurrent()), 20L * 60L, 20L * 60L);
        // LuckPerms
        getLuckPermsAPI();
        lp = new LuckPermsBridge(this);
        // Player Data Manager
        playerDataManager = new PlayerDataManager();
        // Listener
        registerListeners();
        util = new Util();
        //Scoreboard for Tablist
        po = new PlayerOrganizer();
        //Commands
        server.getScheduler().runTaskLater(this, (new Load()), 20L);
    }

    private class Load implements Consumer<BukkitTask> {

        @Override
        public void accept(BukkitTask bukkitTask) {
            CommandLoader.getInstance().scan();
        }
    }

    @Override
    public void onDisable() {
        // Unregister configs
        unregisterConfigs();
        Bukkit.getScheduler().cancelTasks(this);

        // Undo cages
        for (UUID u : cgl.cages.keySet()) {
            Cage.Cager cage = cgl.cages.get(u);
            cage.undo();
        }
        cgl.cages.clear();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lbl, @NotNull String[] args) {
        return CommandHandler.handle(sender, cmd, lbl, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        return CommandHandler.tabComplete(sender, cmd, lbl, args);
    }

    public static LuckPermsApi getLuckPermsAPI() {
        RegisteredServiceProvider<LuckPermsApi> provider = Bukkit.getServicesManager()
                .getRegistration(LuckPermsApi.class);
        if (provider != null) {
            return provider.getProvider();
        }
        return null;
    }

    private void registerListeners() {
        bl = new BanListener();
        cl = new ChatListener();
        ml = new ModeListener();
        mul = new MuteListener();
        pdl = new PlayerDataListener();
        sl = new TabListener();
        wl = new WorldListener();
        shl = new ShopListener();
        cgl = new CageListener();
        ptl = new PlaytimeListener();
        mgrl = new ManageListener();
    }

    public void registerConfigs() {
        config.load();
    }

    private void unregisterConfigs() {
        config.save();
    }


    public static class BuildProperties {
        public String author;
        public String version;
        public String number;
        public String date;
        public String head;

        void load(ConversePlugin plugin) {
            try {
                final Properties props;

                try (InputStream in = plugin.getResource("build.properties")) {
                    props = new Properties();
                    props.load(in);
                }

                author = props.getProperty("buildAuthor", "unknown");
                version = props.getProperty("buildVersion", plugin.getDescription().getVersion());
                number = props.getProperty("buildNumber", "1");
                date = props.getProperty("buildDate", "unknown");
                head = props.getProperty("buildHead", "unknown").replace("${git.commit.id.abbrev}", "unknown");
            } catch (IOException ex) {
                server.getLogger().severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                server.getLogger().severe(ex.toString());
            }
        }
    }
}
