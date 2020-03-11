package me.jaden.forgemodblocker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import lombok.Getter;
import me.jaden.forgemodblocker.command.MainCommand;
import me.jaden.forgemodblocker.command.ModsCommand;
import me.jaden.forgemodblocker.mods.ModManager;
import me.jaden.forgemodblocker.util.C;
import me.jaden.forgemodblocker.util.Message;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Main class for the ForgeModBlocker plugin
 *
 * @author Mas281
 * @author Iowa
 * @version 1.0.0
 * @since 1.0
 */
public class ModBlockerBungeePlugin extends Plugin {

    @Getter
    private static ModBlockerBungeePlugin instance;

    private ModManager modManager;

    @Getter
    private Configuration config;

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();
        initConfig();

        Arrays.asList(new MainCommand(this), new ModsCommand(this))
                .forEach(command -> getProxy().getPluginManager().registerCommand(this, command));

        modManager = new ModManager(this);
    }

    public void reload() {
        reloadConfig();

        initConfig();
        getModManager().loadConfigValues();
    }

    @Override
    public void onDisable() {
    }


    private void reloadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Initialises caching of config values
     */
    private void initConfig() {
        C.setPrefix();
        Message.init(this);
    }

    /**
     * Fetches a value from the config
     *
     * @param path The path to the value
     * @return The value at the path or null if not present
     */
    public <T> T getConfig(String path) {
        return getConfig(path, null);
    }

    /**
     * Fetches a value from the config
     *
     * @param path         The path to the value
     * @param defaultValue The default value to return
     * @return The value at the path
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(String path, Object defaultValue) {
        return (T) getConfig().get(path, defaultValue);
    }

    public ModManager getModManager() {
        return modManager;
    }
}
