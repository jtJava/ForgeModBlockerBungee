package me.jaden.forgemodblocker.util;

import lombok.experimental.UtilityClass;
import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import net.md_5.bungee.api.ChatColor;

/**
 * Chat and formatting utilities
 */
@UtilityClass
public final class C {

    /**
     * The plugin prefix
     */
    public static String PREFIX = null;

    /**
     * Sets the plugin prefix
     */
    public static void setPrefix() {
        assert PREFIX == null : "Prefix is already set";

        ModBlockerBungeePlugin plugin = UtilServer.getPlugin();

        PREFIX = C.colour(plugin.getConfig("prefix", "&c[&6FMB&c]&e")) + " ";
    }

    /**
     * Colours a string
     *
     * @param string The string to colour
     * @return The coloured string
     */
    public static String colour(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
