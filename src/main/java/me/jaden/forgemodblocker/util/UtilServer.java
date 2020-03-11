package me.jaden.forgemodblocker.util;

import lombok.experimental.UtilityClass;
import me.jaden.forgemodblocker.ModBlockerBungeePlugin;


/**
 * Server utility methods
 */

@UtilityClass
public final class UtilServer {


    /**
     * The plugin instance
     */
    private static final ModBlockerBungeePlugin plugin = ModBlockerBungeePlugin.getInstance();

    /**
     * Fetches the plugin instance
     *
     * @return The plugin instance
     * @see #plugin
     */
    static ModBlockerBungeePlugin getPlugin() {
        return plugin;
    }

    /**
     * Broadcasts a set of messages to all players with a {@link Permission}
     *
     * @param permission The required permission
     * @param messages   The messages to send
     * @see #broadcast(Permission, boolean, String...)
     */
    public static void broadcast(Permission permission, String... messages) {
        broadcast(permission, true, messages);
    }

    /**
     * Broadcasts a set of messages to all players with a {@link Permission}
     *
     * @param permission The required permission
     * @param messages   The messages to send
     * @param prefix     Whether to prefix the messages with the plugin prefix
     * @see C#PREFIX
     */
    public static void broadcast(Permission permission, boolean prefix, String... messages) {
        if (prefix) {
            for (int i = 0; i < messages.length; i++) {
                messages[i] = C.PREFIX + messages[i];
            }
        }

        getPlugin().getProxy().getPlayers().stream().filter(player -> Permission.hasPermission(player, permission)).forEach(player -> player.sendMessages(messages));
    }

}
