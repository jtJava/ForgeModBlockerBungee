package me.itsmas.forgemodblocker.versions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.util.UtilServer;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Handles cross-version compatibility
 */
public class VersionManager
{
    /**
     * The plugin instance
     */
    private final ForgeModBlocker plugin;

    public VersionManager(ForgeModBlocker plugin)
    {
        this.plugin = plugin;

        initVersion();
    }

    /**
     * Finds the server version and initialises the {@link #cachedConstructor} field
     */
    private void initVersion()
    {
        Class<?> clazz;

        try
        {
             clazz = Class.forName("net.minecraft.server." + UtilServer.getServerVersion() + ".PacketDataSerializer");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

            plugin.getLogger().severe("Could nto found required PacketDataSerializer class");
            plugin.getLogger().severe("Disabling plugin");

            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        try
        {
            cachedConstructor = clazz.getDeclaredConstructor(ByteBuf.class);
        }
        catch (NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * The constructor for a PacketDataSerializer
     */
    private Constructor<?> cachedConstructor;

    /**
     * Fetches a serialized byte array for use in packet creation
     *
     * @param bytes The bytes to serialize
     * @return The serialized bytes
     */
    public Object getSerializedBytes(byte... bytes)
    {
        try
        {
            return cachedConstructor.newInstance(Unpooled.wrappedBuffer(bytes));
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
