package me.itsmas.forgemodblocker.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.util.C;
import me.itsmas.forgemodblocker.util.Logs;
import me.itsmas.forgemodblocker.util.Permission;
import me.itsmas.forgemodblocker.util.UtilHttp;
import me.itsmas.forgemodblocker.util.UtilServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Scans for available plugin updates
 */
public class Updater implements Listener
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
     * Latest update data
     */
    private Object[] data;

    public Updater(ForgeModBlocker plugin)
    {
        this.plugin = plugin;

        UtilServer.registerListener(this);

        currentVersion = Integer.parseInt(plugin.getDescription().getVersion().replaceAll("\\.", ""));

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (checkUpdates())
                {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, UPDATE_CHECK_DELAY, UPDATE_CHECK_INTERVAL);
    }

    /**
     * The current plugin version
     */
    private final int currentVersion;

    /**
     * Periodically checks for updates
     *
     * @return Whether an update was found
     */
    private boolean checkUpdates()
    {
        Logs.info("Checking for updates...");

        JsonElement versionElement = UtilHttp.getJsonFromUrl(RESOURCE_VERSIONS);
        JsonElement updateElement = UtilHttp.getJsonFromUrl(RESOURCE_UPDATES);

        if (versionElement == null || updateElement == null)
        {
            return false;
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
            announceUpdate();

            return true;
        }

        return false;
    }

    /**
     * The message to send to players who join
     */
    private String updateMessage;

    /**
     * Periodically broadcasts new updates
     */
    private void announceUpdate()
    {
        String[] messages = new String[]
        {
            "An update is available:",
            "Version: " + data[0] + " (current: " + plugin.getDescription().getVersion() + ")",
            "Title: " + data[1]
        };

        Logs.info(messages);
        setUpdateMessage(messages);

        UtilServer.broadcast(Permission.UPDATE_NOTIFICATION, false, updateMessage);
    }

    /**
     * Sets the update message
     *
     * @see #updateMessage
     * @param messages The separated messages
     */
    private void setUpdateMessage(String[] messages)
    {
        for (int i = 0; i < messages.length; i++)
        {
            messages[i] = C.PREFIX + messages[i];
        }

        updateMessage = String.join("\n", messages);
    }

    /**
     * Sends the update message to a player
     *
     * @param player The player
     */
    private void sendUpdateMessage(Player player)
    {
        player.sendMessage(updateMessage);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (Permission.hasPermission(player, Permission.UPDATE_NOTIFICATION))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    sendUpdateMessage(player);
                }
            }.runTaskLater(plugin, 20L);
        }
    }
}
