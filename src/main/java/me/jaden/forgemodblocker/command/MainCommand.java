package me.jaden.forgemodblocker.command;

import me.jaden.forgemodblocker.ModBlockerBungeePlugin;
import me.jaden.forgemodblocker.util.Message;
import me.jaden.forgemodblocker.util.Permission;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginDescription;

public class MainCommand extends Command {
    private final ModBlockerBungeePlugin plugin;

    public MainCommand(ModBlockerBungeePlugin plugin) {
        super("fmb", "fmb.info");
        this.plugin = plugin;

        PluginDescription description = plugin.getDescription();
        this.msg = String.format("%s%s version %s%s %sby %s%s", ChatColor.GREEN, description.getName(), ChatColor.YELLOW, description.getVersion(), ChatColor.GREEN, ChatColor.YELLOW, description.getAuthor());
    }

    private final String msg;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Permission.hasPermission(sender, Permission.MAIN_COMMAND)) {
            Message.send(sender, Message.NO_PERMISSION);
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(msg);
            return;
        } else {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!Permission.hasPermission(sender, Permission.RELOAD_COMMAND)) {
                    Message.send(sender, Message.NO_PERMISSION);
                    return;
                }

                plugin.reload();
                Message.send(sender, Message.PLUGIN_RELOADED);

                return;
            }
        }

        sendUsage(sender);
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Commands:");
        sender.sendMessage(ChatColor.YELLOW + "/fmb reload - Reloads the plugin");
    }
}
