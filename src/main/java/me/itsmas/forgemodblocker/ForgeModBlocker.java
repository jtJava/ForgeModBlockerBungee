package me.itsmas.forgemodblocker;

import me.itsmas.forgemodblocker.metrics.Metrics;
import me.itsmas.forgemodblocker.mods.ModManager;
import me.itsmas.forgemodblocker.placeholder.Placeholders;
import me.itsmas.forgemodblocker.update.Updater;
import me.itsmas.forgemodblocker.util.C;
import me.itsmas.forgemodblocker.versions.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the ForgeModBlocker plugin
 *
 * @author Mas281
 * @version 1.0
 * @since 06/08/17
 */
public class ForgeModBlocker extends JavaPlugin
{
    private VersionManager versionManager;
    private ModManager modManager;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        C.setPrefix();

        boolean placeholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

        if (placeholderAPI)
        {
            new Placeholders(this);
        }

        new Metrics(this).addCustomChart(new Metrics.SimplePie("using_placeholderapi", () -> Boolean.toString(placeholderAPI)));
        new Updater(this);

        versionManager = new VersionManager(this);
        modManager = new ModManager(this);
    }

    /**
     * Fetches a value from the config
     *
     * @param path The path to the value
     * @param defaultValue The default value to return
     * @return The value at the path
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(String path, Object defaultValue)
    {
        return (T) getConfig().get(path, defaultValue);
    }

    public VersionManager getVersionManager()
    {
        return versionManager;
    }

    public ModManager getModManager()
    {
        return modManager;
    }
}
