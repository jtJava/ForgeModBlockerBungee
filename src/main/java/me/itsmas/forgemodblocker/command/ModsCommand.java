package me.itsmas.forgemodblocker.command;

import me.itsmas.forgemodblocker.ForgeModBlocker;
import me.itsmas.forgemodblocker.util.Message;
import me.itsmas.forgemodblocker.util.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ModsCommand implements CommandExecutor
{
    private final ForgeModBlocker plugin;

    public ModsCommand(ForgeModBlocker plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!Permission.hasPermission(sender, Permission.MODS_COMMAND))
        {
            Message.send(sender, Message.NO_PERMISSION);
            return true;
        }

        if (args.length != 1)
        {
            Message.send(sender, Message.MODS_COMMAND_USAGE);
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            Message.send(sender, Message.PLAYER_OFFLINE);
            return true;
        }

        if (!plugin.getModManager().isUsingForge(player))
        {
            Message.send(sender, Message.PLAYER_NOT_USING_FORGE, player.getName());
            return true;
        }

        Map<String, String> mods = plugin.getModManager().getModData(player).getModsMap();

        Message.send(sender, Message.PLAYER_MODS, player.getName());
        mods.forEach((mod, version) -> Message.send(sender, Message.MODS_FORMAT, mod, version));

        return true;
    }
}
