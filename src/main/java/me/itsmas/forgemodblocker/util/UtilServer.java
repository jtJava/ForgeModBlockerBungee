package me.itsmas.forgemodblocker.util;

import me.itsmas.forgemodblocker.ForgeModBlocker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Server utility methods
 */
public class UtilServer
{
    /**
     * The plugin instance
     */
    private static final ForgeModBlocker plugin = JavaPlugin.getPlugin(ForgeModBlocker.class);

    /**
     * Fetches the plugin instance
     *
     * @see #plugin
     * @return The plugin instance
     */
    static ForgeModBlocker getPlugin()
    {
        return plugin;
    }

    static
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        serverVersion = packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    /**
     * The server version
     */
    private static final String serverVersion;

    /**
     * Fetches the server version
     *
     * @see #serverVersion
     * @return The server version
     */
    public static String getServerVersion()
    {
        return serverVersion;
    }

    /**
     * Registers an outgoing message channel
     *
     * @param channel The channel name
     */
    public static void registerOutgoingChannel(String channel)
    {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }

    /**
     * Registers an incoming message channel
     *
     * @param channel The channel name
     * @param messageListener The {@link PluginMessageListener} listening to the channel
     */
    public static void registerIncomingChannel(String channel, PluginMessageListener messageListener)
    {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, messageListener);
    }
}
