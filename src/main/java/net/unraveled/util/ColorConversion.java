package net.unraveled.util;

import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A class designed for converting Bungee ChatColor objects to Bukkit ChatColor objects, and vice-versa.
 */
public class ColorConversion {
    private static final Map<org.bukkit.ChatColor, ChatColor> colors = new HashMap<>();
    private static final Map<ChatColor, org.bukkit.ChatColor> colorMap = new HashMap<>();

    // This is the constructor. How this works should be obvious.
    public ColorConversion() {
        Arrays.stream(org.bukkit.ChatColor.values()).forEachOrdered(translatable -> Arrays.stream(ChatColor.values())
                .forEachOrdered(value -> colors.put(translatable, value)));
        Arrays.stream(ChatColor.values()).forEachOrdered(translatable -> Arrays.stream(org.bukkit.ChatColor.values())
                .forEachOrdered(value -> colorMap.put(translatable, value)));
    }

    /**
     * Convert from Bukkit supplied ChatColor to Bungee ChatColor.
     *
     * @param color The Bukkit ChatColor object to convert.
     * @return The relative Bungee ChatColor object.
     */
    public ChatColor convert(org.bukkit.ChatColor color) {
        return colors.get(color);
    }

    /**
     * Convert from Bungee ChatColor to Bukkit ChatColor.
     *
     * @param color The Bungee ChatColor object to convert.
     * @return The relative Bukkit ChatColor object.
     */
    public org.bukkit.ChatColor convert(ChatColor color) {
        return colorMap.get(color);
    }
}
