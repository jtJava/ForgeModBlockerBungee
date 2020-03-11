package me.jaden.forgemodblocker.util;

import net.md_5.bungee.api.CommandSender;

/**
 * Permission nodes for the plugin
 */
public enum Permission {
    ALL("*"),
    UPDATE_NOTIFICATION("update_notification"),
    BYPASS("bypass"),
    MAIN_COMMAND("main_command"),
    RELOAD_COMMAND("reload_command"),
    MODS_COMMAND("mods_command");

    Permission(String name) {
        this.node = "fmb." + name;
    }

    /**
     * The permission's node
     */
    private final String node;

    /**
     * Determines whether a {@link net.md_5.bungee.api.CommandSender} has a {@link Permission}
     *
     * @param sender     The sender
     * @param permission The permission
     * @return Whether the sender has the permission
     */
    public static boolean hasPermission(CommandSender sender, Permission permission) {
        return sender.hasPermission(permission.node);
    }
}
