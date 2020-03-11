package me.jaden.forgemodblocker.util;

import java.util.Arrays;
import java.util.logging.Logger;
import lombok.experimental.UtilityClass;

/**
 * Logging utility methods
 */
@UtilityClass
public final class Logs {


    /**
     * @see Logger#info(String)
     */
    public static void info(String msg) {
        UtilServer.getPlugin().getLogger().info(msg);
    }

    /**
     * @see #info(String)
     */
    public static void info(String... messages) {
        Arrays.stream(messages).forEach(Logs::info);
    }

    /**
     * @see Logger#severe(String)
     */
    public static void severe(String msg) {
        UtilServer.getPlugin().getLogger().severe(msg);
    }
}
