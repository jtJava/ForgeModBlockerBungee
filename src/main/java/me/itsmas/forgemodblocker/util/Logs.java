package me.itsmas.forgemodblocker.util;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Logging utility methods
 */
public final class Logs
{
    private Logs(){}

    /**
     * @see Logger#info(String)
     */
    public static void info(String msg)
    {
        UtilServer.getPlugin().getLogger().info(msg);
    }

    /**
     * @see #info(String)
     */
    public static void info(String... messages)
    {
        Arrays.stream(messages).forEach(Logs::info);
    }

    /**
     * @see Logger#severe(String)
     */
    public static void severe(String msg)
    {
        UtilServer.getPlugin().getLogger().severe(msg);
    }
}
