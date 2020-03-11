package me.jaden.forgemodblocker.messaging;

import java.util.concurrent.TimeUnit;
import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Handles player joins and quits
 */
public class JoinListener implements Listener {
    /**
     * The plugin instance
     */
    private final ModBlockerBungeePlugin plugin;

    public JoinListener(ModBlockerBungeePlugin plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
        plugin.getProxy().registerChannel("FML|HS");
    }

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (plugin.getModManager().isUsingForge(player)) {
            return;
        }
        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            if (player.isConnected()) {
                sendFmlPacket(player, (byte) -2, (byte) 0);
                sendFmlPacket(player, (byte) 0, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
            }
        }, 2, TimeUnit.SECONDS);
    }

    /**
     * Sends a packet through the FML|HS channel
     *
     * @param player The player to send the packet to
     * @param data   The data to send with the packet
     */
    private void sendFmlPacket(ProxiedPlayer player, byte... data) {
        player.sendData("FML|HS", data);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        plugin.getModManager().removePlayer(event.getPlayer());
    }
}
