package me.itsmas.forgemodblocker.util;

import me.itsmas.forgemodblocker.ForgeModBlocker;
import org.bukkit.ChatColor;

/**
 * Chat and formatting utilities
 */
public final class C
{
    private C(){}

    /**
     * The plugin prefix
     */
    public static String PREFIX = null;

    /**
     * Sets the plugin prefix
     */
    public static void setPrefix()
    {
        assert PREFIX == null : "Prefix is already set";

        ForgeModBlocker plugin = UtilServer.getPlugin();

        PREFIX = ChatColor.translateAlternateColorCodes('&', plugin.getConfig("prefix", "&c[&6FMB&c]&e")) + " ";
    }

    /**
     * Colours a string
     *
     * @param string The string to colour
     * @return The coloured string
     */
    public static String colour(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
