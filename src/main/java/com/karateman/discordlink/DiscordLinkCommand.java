package com.karateman.discordlink;

import com.karateman.discordlink.modules.util.LangUtil;
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
        LangUtil langUtil = new LangUtil(plugin);
        if(!sender.hasPermission("discordlink.setup")) {
            sender.sendMessage(ChatColor.RED + langUtil.getMessage("command-no-permission"));
            return false;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bHelp Page 1/1]"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8- &9/DiscordLink &bSetup"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8- &9/DiscordLink &bReload"));
            return false;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-reload")));
            } else if(args[0].equalsIgnoreCase("setup")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup")));

                if(plugin.getGamechatModule().isEnabled()) {
                    if(plugin.getGamechatModule().isSetup()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-gamechat-already-enabled")));
                    } else {
                        if(plugin.getGamechatModule().setup()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-gamechat-success")));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-gamechat-fail")));
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-gamechat-not-enabled")));
                }

                if(plugin.getVerificationModule().isEnabled()) {
                    if(plugin.getVerificationModule().isSetup()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-verification-already-enabled")));
                    } else {
                        if(plugin.getVerificationModule().setup()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-verification-success")));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-verification-fail")));
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-verification-not-enabled")));
                }

                if(plugin.getCommandsModule().isEnabled()) {
                    if(plugin.getCommandsModule().isSetup()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-commands-already-enabled")));
                    } else {
                        if(plugin.getCommandsModule().setup()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-commands-success")));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-commands-fail")));
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-commands-not-enabled")));
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b" + langUtil.getMessage("command-setup-success")));

            }
            return false;
        }

        return false;
    }
}
