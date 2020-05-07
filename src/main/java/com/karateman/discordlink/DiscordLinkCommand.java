package com.karateman.discordlink;

import org.bukkit.ChatColor;
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
        if(!sender.hasPermission("discordlink.setup")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
            return false;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) plugin.reloadConfig();
            return false;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bBeginning setup of your Discord!"));

        if(plugin.getGamechatModule().isEnabled()) {
            if(plugin.getGamechatModule().isSetup()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bGamechat Module is already setup. Continuing to Verification Module."));
            } else {
                if(plugin.getGamechatModule().setup()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bSuccessfully setup the Gamechat Module!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bGamechat Module failed to setup. Please contact the Developer for assistance."));
                }
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bGamechat Module is not enabled."));
        }

        if(plugin.getVerificationModule().isEnabled()) {
            if(plugin.getVerificationModule().isSetup()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bVerification Module is already setup. Continuing to Commands Module."));
            } else {
                if(plugin.getVerificationModule().setup()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bSuccessfully setup the Verification Module!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &Verification Module failed to setup. Please contact the Developer for assistance."));
                }
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bVerification Module is not enabled."));
        }

        if(plugin.getCommandsModule().isEnabled()) {
            if(plugin.getCommandsModule().isSetup()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bCommands Module is already setup."));
            } else {
                if(plugin.getCommandsModule().setup()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bSuccessfully setup the Commands Module!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bCommands Module failed to setup. Please contact the Developer for assistance."));
                }
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bCommands Module is not enabled."));
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bYour Discord server has been setup! Feel free to move the channels around."));

        return false;
    }
}
