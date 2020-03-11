package me.jaden.forgemodblocker.util;

import lombok.experimental.UtilityClass;

/**
 * String final utility methods
 */
@UtilityClass
public class UtilString {

    /**
     * Joins an array of strings from a certain index
     *
     * @param args       The array to join
     * @param startIndex The index to start at
     * @return The combined string
     */
    public static String combine(String[] args, int startIndex) {
        StringBuilder builder = new StringBuilder();

        for (int i = startIndex; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }

        return builder.toString();
    }
}
