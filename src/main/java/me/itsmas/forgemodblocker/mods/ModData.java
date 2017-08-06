package me.itsmas.forgemodblocker.mods;

import java.util.Collections;
import java.util.Map;

/**
 * Data holding a player's mods and their versions
 */
public class ModData
{
    /**
     * Map of mod IDs to versions
     */
    private final Map<String, String> mods;

    public ModData(Map<String, String> mods)
    {
        this.mods = mods;
    }

    /**
     * Fetches the mod ID to version map
     *
     * @see #mods
     * @return An immutable map of mod IDs and their versions
     */
    public Map<String, String> getMods()
    {
        return Collections.unmodifiableMap(mods);
    }
}
