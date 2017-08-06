package me.itsmas.forgemodblocker.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.util.C;
import me.itsmas.forgemodblocker.util.Permission;
import me.itsmas.forgemodblocker.util.UtilHttp;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Scans for available plugin updates
 */
public class Updater
{
    /**
     * Spiget API query URLs
     */
    private static final String RESOURCE_VERSIONS = "https://api.spiget.org/v2/resources/ForgeModBlocker/versions?size=1&spiget__ua=ForgeModBlocker&sort=-name";
    private static final String RESOURCE_UPDATES = "https://api.spiget.org/v2/resources/ForgeModBlocker/updates?size=1&spiget__ua=ForgeModBlocker&sort=-date";

    /**
     * The delay after which update checking should begin
     */
    private static final long UPDATE_CHECK_DELAY = 20L * 60L;

    /**
     * The amount of time between update checks
     */
    private static final long UPDATE_CHECK_INTERVAL = 20L * 60L * 20L;

    /**
     * The plugin instance
     */
    private final ForgeModBlocker plugin;

    /**
     * Update data
     */
    private Object[] data;

    public Updater(ForgeModBlocker plugin)
    {
        this.plugin = plugin;

        checkUpdates();
        announceUpdates();
    }

    /**
     * Periodically checks for updates
     */
    private void checkUpdates()
    {
        int currentVersion = Integer.parseInt(plugin.getDescription().getVersion().replaceAll("\\.", ""));

        Bukkit.broadcastMessage("cur version: " + currentVersion);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                JsonElement versionElement = UtilHttp.getJsonFromUrl(RESOURCE_VERSIONS);
                JsonElement updateElement = UtilHttp.getJsonFromUrl(RESOURCE_UPDATES);

                if (versionElement == null || updateElement == null)
                {
                    return;
                }

                JsonArray versionArray = versionElement.getAsJsonArray();
                JsonArray updateArray = updateElement.getAsJsonArray();

                JsonObject latestVersion = versionArray.get(0).getAsJsonObject();
                JsonObject latestUpdate = updateArray.get(0).getAsJsonObject();

                String versionName = latestVersion.get("name").getAsString();
                int version = Integer.parseInt(versionName.replaceAll("\\.", ""));

                if (version > currentVersion)
                {
                    String updateTitle = latestUpdate.get("title").getAsString();

                    data = new Object[] {versionName, updateTitle};
                }
            }
        }.runTaskTimerAsynchronously(plugin, UPDATE_CHECK_DELAY, UPDATE_CHECK_INTERVAL);
    }

    /**
     * Periodically broadcasts new updates
     */
    private void announceUpdates()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (data != null)
                {
                    Bukkit.getOnlinePlayers().stream().filter(player -> Permission.hasPermission(player, Permission.UPDATE_NOTIFICATION)).forEach(player ->
                    {
                        player.sendMessage(C.PREFIX + "An update is available:");
                        player.sendMessage(C.PREFIX + "Version: " + data[0]);
                        player.sendMessage(C.PREFIX + "Title: " + data[1]);
                    });
                }
            }
        }.runTaskTimer(plugin, UPDATE_CHECK_DELAY + 200L, UPDATE_CHECK_INTERVAL);
    }
}
