package me.jaden.forgemodblocker.messaging;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import me.jaden.forgemodblocker.mods.ModData;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Listens to Forge messages through the messaging channel
 */
public class MessageListener implements Listener {
    /**
     * The plugin instance
     */
    private final ModBlockerBungeePlugin plugin;

    public MessageListener(ModBlockerBungeePlugin plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase("FML|HS")) {
            if (event.getData()[0] == 2) {
                System.out.println(event.getReceiver());
                System.out.println(event.getSender());

                ModData modData = getModData(event.getData());
                plugin.getModManager().addPlayer((ProxiedPlayer) event.getSender(), modData);
            }
        }
    }

    /**
     * Fetches a {@link ModData} object from the raw mods data
     *
     * @param data The input data
     * @return A ModData object
     */
    private ModData getModData(byte[] data) {
        Map<String, String> mods = new HashMap<>();

        boolean store = false;
        String tempName = null;

        for (int i = 2; i < data.length; store = !store) {
            int end = i + data[i] + 1;
            byte[] range = Arrays.copyOfRange(data, i + 1, end);

            String string = new String(range);

            if (store) {
                mods.put(tempName, string);
            } else {
                tempName = string;
            }

            i = end;
        }

        return new ModData(mods);
    }
}
