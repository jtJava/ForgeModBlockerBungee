package me.itsmas.forgemodblocker.messaging;

import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.mods.ModData;
import me.itsmas.forgemodblocker.util.UtilServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Listens to Forge messages through the messaging channel
 */
public class MessageListener implements PluginMessageListener
{
    /**
     * The plugin instance
     */
    private final ForgeModBlocker plugin;

    public MessageListener(ForgeModBlocker plugin)
    {
        this.plugin = plugin;

        UtilServer.registerIncomingChannel("FML|HS", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data)
    {
        // ModList has ID 2
        if (data[0] == 2)
        {
            ModData modData = getModData(data);
            plugin.getModManager().addPlayer(player, modData);
        }
    }

    /**
     * Fetches a {@link ModData} object from the raw mods data
     *
     * @param data The input data
     * @return A ModData object
     */
    private ModData getModData(byte[] data)
    {
        Map<String, String> mods = new HashMap<>();

        boolean store = false;
        String tempName = null;

        for (int i = 2; i < data.length; store = !store)
        {
            int end = i + data[i] + 1;
            byte[] range = Arrays.copyOfRange(data, i + 1, end);

            String string = new String(range);

            if (store)
            {
                mods.put(tempName, string);
            }
            else
            {
                tempName = string;
            }

            i = end;
        }

        return new ModData(mods);
    }
}
