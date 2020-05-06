package com.karateman.discordlink.spigot.commands;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetupCommand {

    public SetupCommand(DiscordLinkPlugin plugin, CommandSender sender) {
        boolean gamechatModule = plugin.getConfig().getBoolean("gamechat-module");
        boolean verificationModule = plugin.getConfig().getBoolean("verification-module");

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bBeginning setup on your discord server!"));

        DiscordLinkPlugin.getBot().getJda().getGuilds().get(0).createCategory("discord link").queue((category -> {
            if(gamechatModule) {
                String gamechatId = plugin.getConfig().getString("gamechat-id");

                if(gamechatId.equalsIgnoreCase("123")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bStarting Gamechat setup!"));
                    category.createTextChannel("gamechat").queue((gamechatChannel) -> {
                        plugin.getConfig().set("gamechat-id", gamechatChannel.getId());
                        plugin.saveConfig();

                        if(verificationModule) {

                            String verificationChannelId = plugin.getConfig().getString("verification-channel-id");
                            String verificationRoleId = plugin.getConfig().getString("verification-role-id");

                            if (verificationChannelId.equalsIgnoreCase("123")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bStarting Verification setup!"));
                                category.createTextChannel("verification").queue((verificationChannel) -> {
                                    plugin.getConfig().set("verification-channel-id", verificationChannel.getId());
                                    plugin.saveConfig();

                                    Role everyone = null;
                                    for (Role role : DiscordLinkPlugin.getBot().getJda().getRoles()) {
                                        if (role.isPublicRole()) everyone = role;
                                    }
                                    gamechatChannel.createPermissionOverride(everyone).setDeny(Permission.MESSAGE_WRITE).queue();

                                    EmbedBuilder embed = new EmbedBuilder();
                                    embed.addField("Verification", "React below to verify\nMake sure that you can receive DMs", false);
                                    verificationChannel.sendMessage(embed.build()).queue((msg) -> {
                                        plugin.getConfig().set("verification-message", msg.getId());
                                        plugin.saveConfig();
                                        msg.addReaction("✔").queue();
                                    });

                                    if (verificationRoleId.equalsIgnoreCase("123")) {
                                        DiscordLinkPlugin.getBot().getJda().getGuilds().get(0).createRole().setMentionable(false).setName("Verified").queue((role -> {
                                            plugin.getConfig().set("verification-role-id", role.getId());
                                            plugin.saveConfig();
                                            gamechatChannel.createPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue();
                                        }));
                                    }
                                });
                            }

                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bSuccessfully setup your Discord!"));
                        }
                    });
                }
            }
        }));
    }
}
