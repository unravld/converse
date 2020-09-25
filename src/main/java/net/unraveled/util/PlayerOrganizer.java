package net.unraveled.util;

import net.unraveled.Container;
import net.unraveled.ConversePlugin;
import net.unraveled.bridge.LuckPermsBridge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerOrganizer extends Container {
    //TabList Sorting Methods
    private final LuckPermsBridge LPB = getPlugin().lp;
    private final Scoreboard sb;
    final Team op;
    final Team voter;
    final Team arc;
    final Team mod;
    final Team admin;
    final Team dev;
    final Team exec;

    public PlayerOrganizer() {
        super();
        sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        sb.getTeams().forEach(Team::unregister);
        op = sb.registerNewTeam("G-OP");
        voter = sb.registerNewTeam("F-Voter");
        arc = sb.registerNewTeam("E-Arch");
        mod = sb.registerNewTeam("D-Mod");
        admin = sb.registerNewTeam("C-Admin");
        dev = sb.registerNewTeam("B-Dev");
        exec = sb.registerNewTeam("A-Exec");

        exec.setPrefix(colorize("&8[&4E&8] &r"));
        dev.setPrefix(colorize("&8[&5D&8] &r"));
        admin.setPrefix(colorize("&8[&6A&8] &r"));
        mod.setPrefix(colorize("&8[&2M&8] &r"));
        arc.setPrefix(colorize("&8[&1A&8] &r"));
        voter.setPrefix(colorize("&8[&3V&8] &r"));
        op.setPrefix(colorize("&r"));
    }

    public void tabAdd(@NotNull Player p) {

        String pName = p.getName();
        //
        //
        if (LPB.isModerator(p.getUniqueId())) {
            mod.addEntry(pName);
        } else if (LPB.isAdministrator(p.getUniqueId())) {
            admin.addEntry(pName);
        } else if (LPB.isDeveloper(p.getUniqueId())) {
            dev.addEntry(pName);
        } else if (LPB.isExecutive(p.getUniqueId())) {
            exec.addEntry(pName);
        } else if (LPB.isArchitect(p.getUniqueId())) {
            arc.addEntry(pName);
        } else if (LPB.isVoter(p.getUniqueId())) {
            voter.addEntry(pName);
        } else {
            op.addEntry(pName);
        }
    }

    public void tabRemove(Player p) {
        String uuid = p.getUniqueId().toString().trim();

        if (sb.getEntries().contains(uuid)) {
            Team team = sb.getEntryTeam(uuid);
            assert team != null;
            team.removeEntry(uuid);
        }
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}