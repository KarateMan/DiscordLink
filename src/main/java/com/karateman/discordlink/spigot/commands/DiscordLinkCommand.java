package com.karateman.discordlink.spigot.commands;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordLinkCommand implements CommandExecutor {

    private DiscordLinkPlugin plugin;

    public DiscordLinkCommand(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("discordlink.setup")) return false;

        if(args[0].equalsIgnoreCase("setup")) new SetupCommand(plugin, sender);

        return false;
    }
}
