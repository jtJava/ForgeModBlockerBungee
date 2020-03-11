package me.jaden.forgemodblocker.mods;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Data holding a player's mods and their versions
 */
public class ModData {
    /**
     * Map of mod IDs to versions
     */
    private final Map<String, String> mods;

    public ModData(Map<String, String> mods) {
        this.mods = mods;
    }

    /**
     * Fetches the list of mod IDs
     *
     * @return An immutable set of mod IDs
     * @see #mods
     */
    public Set<String> getMods() {
        return Collections.unmodifiableSet(mods.keySet());
    }

    /**
     * Fetches the mods map
     *
     * @return The mods map
     * @see #mods
     */
    public Map<String, String> getModsMap() {
        return Collections.unmodifiableMap(mods);
    }
}
