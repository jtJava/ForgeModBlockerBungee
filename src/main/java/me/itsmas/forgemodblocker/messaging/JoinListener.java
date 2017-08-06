package me.itsmas.forgemodblocker.messaging;

import com.comphenix.protocol.events.PacketContainer;
import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.util.UtilPacket;
import me.itsmas.forgemodblocker.util.UtilServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Handles player joins and quits
 */
public class JoinListener implements Listener
{
    /**
     * The plugin instance
     */
    private final ForgeModBlocker plugin;

    public JoinListener(ForgeModBlocker plugin)
    {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        UtilServer.registerOutgoingChannel("FML|HS");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                sendFmlPacket(player, (byte) -2, (byte) 0);
                sendFmlPacket(player, (byte) 0, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
                sendFmlPacket(player, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
            }
        }.runTaskLater(plugin, 20L);
    }

    /**
     * Sends a packet through the FML|HS channel
     *
     * @param player The player to send the packet to
     * @param data The data to send with the packet
     */
    private void sendFmlPacket(Player player, byte... data)
    {
        PacketContainer packet = UtilPacket.createPayloadPacket("FML|HS", data);

        if (packet != null)
        {
            UtilPacket.sendPacket(player, packet);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        plugin.getModManager().removePlayer(event.getPlayer());
    }
}
