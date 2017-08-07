package me.itsmas.forgemodblocker.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Packet utility methods
 */
public final class UtilPacket
{
    private UtilPacket(){}

    /**
     * Creates a PacketPlayOutCustomPayload packet with the given data
     *
     * @param channel The channel the packet will be sent by
     * @param data The data to attach to the packet
     * @return The created packet
     */
    public static PacketContainer createPayloadPacket(String channel, byte... data)
    {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, channel);

        Object serializedBytes = UtilServer.getPlugin().getVersionManager().getSerializedBytes(data);

        if (serializedBytes != null)
        {
            packet.getModifier().write(1, serializedBytes);
        }

        return packet;
    }

    /**
     * Sends a packet to a player using ProtocolLib
     *
     * @param player The player
     * @param packet The packet
     */
    public static void sendPacket(Player player, PacketContainer packet)
    {
        try
        {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        }
        catch (InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
    }
}
