package me.jaden.forgemodblocker.command;

import java.util.Map;
import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import me.jaden.forgemodblocker.util.Message;
import me.jaden.forgemodblocker.util.Permission;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ModsCommand extends Command {
    private final ModBlockerBungeePlugin plugin;

    public ModsCommand(ModBlockerBungeePlugin plugin) {
        super("mods");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Permission.hasPermission(sender, Permission.MODS_COMMAND)) {
            Message.send(sender, Message.NO_PERMISSION);
            return;
        }

        if (args.length != 1) {
            Message.send(sender, Message.MODS_COMMAND_USAGE);
            return;
        }

        ProxiedPlayer player = plugin.getProxy().getPlayer(args[0]);

        if (player == null) {
            Message.send(sender, Message.PLAYER_OFFLINE);
            return;
        }

        if (!plugin.getModManager().isUsingForge(player)) {
            Message.send(sender, Message.PLAYER_NOT_USING_FORGE, player.getName());
            return;
        }

        Map<String, String> mods = plugin.getModManager().getModData(player).getModsMap();

        Message.send(sender, Message.PLAYER_MODS, player.getName());
        mods.forEach((mod, version) -> Message.send(sender, Message.MODS_FORMAT, mod, version));
    }
}
