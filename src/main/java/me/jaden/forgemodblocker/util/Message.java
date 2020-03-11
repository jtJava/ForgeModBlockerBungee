package me.jaden.forgemodblocker.util;

import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

/**
 * Configurable messages
 */
public enum Message {
    NO_PERMISSION,
    MODS_COMMAND_USAGE,
    PLAYER_OFFLINE,
    PLAYER_NOT_USING_FORGE,
    PLAYER_MODS,
    MODS_FORMAT,
    PLUGIN_RELOADED;

    private String msg;

    /**
     * Sends a {@link Message} to a {@link CommandSender}
     *
     * @param sender  The sender
     * @param message The message
     * @param params  Optional formatting arguments
     */
    public static void send(CommandSender sender, Message message, Object... params) {
        String msg = message.value() == null ? message.name() : message.value();

        sender.sendMessage(String.format(msg, params));
    }

    /**
     * Initialises the configurable messages
     *
     * @param plugin The plugin instance
     */
    public static void init(ModBlockerBungeePlugin plugin) {
        String messagesPath = "messages";

        for (Message message : values()) {
            String value = plugin.getConfig(messagesPath + "." + message.name().toLowerCase());

            if (value == null) {
                Logs.severe("No value found for message " + message);
                continue;
            }

            message.setValue(ChatColor.translateAlternateColorCodes('&', value));
        }
    }

    /**
     * Sets the value of a message
     *
     * @param msg The message value
     */
    private void setValue(String msg) {
        assert this.msg == null : "Message is already set";

        this.msg = msg;
    }

    /**
     * Fetches the value of a message
     *
     * @return The message value
     */
    public String value() {
        return msg;
    }
}
