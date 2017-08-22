package me.itsmas.forgemodblocker.util;

import me.itsmas.forgemodblocker.ForgeModBlocker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Server utility methods
 */
public final class UtilServer
{
    private UtilServer(){}

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

    /**
     * Broadcasts a set of messages to all players with a {@link Permission}
     *
     * @see #broadcast(Permission, boolean, String...)
     * @param permission The required permission
     * @param messages The messages to send
     */
    public static void broadcast(Permission permission, String... messages)
    {
        broadcast(permission, true, messages);
    }

    /**
     * Broadcasts a set of messages to all players with a {@link Permission}
     *
     * @see C#PREFIX
     * @param permission The required permission
     * @param messages The messages to send
     * @param prefix Whether to prefix the messages with the plugin prefix
     */
    public static void broadcast(Permission permission, boolean prefix, String... messages)
    {
        if (prefix)
        {
            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = C.PREFIX + messages[i];
            }
        }

        Bukkit.getOnlinePlayers().stream().filter(player -> Permission.hasPermission(player, permission)).forEach(player -> player.sendMessage(messages));
    }

    /**
     * Registers a {@link Listener}
     *
     * @param listener The listener
     */
    public static void registerListener(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
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
