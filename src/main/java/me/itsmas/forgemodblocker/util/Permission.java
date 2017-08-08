package me.itsmas.forgemodblocker.util;

import org.bukkit.entity.Player;

/**
 * Permission nodes for the plugin
 */
public enum Permission
{
    ALL("*", false),
    UPDATE_NOTIFICATION("update_notification", true),
    BYPASS("bypass", false);

    Permission(String name, boolean opDefault)
    {
        this.node = "fmb." + name;
        this.opDefault = opDefault;
    }

    /**
     * The permission's node
     */
    private final String node;

    /**
     * Whether the permission will be given to operators
     */
    private final boolean opDefault;

    /**
     * Determines whether a player has a {@link Permission}
     *
     * @param player The player
     * @param permission The permission
     * @return Whether the player has the permission
     */
    public static boolean hasPermission(Player player, Permission permission)
    {
        return (permission.opDefault && player.isOp())  || player.hasPermission(permission.node) || (permission != ALL && hasPermission(player, ALL));
    }
}
