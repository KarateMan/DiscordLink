package com.karateman.discordlink.discord.utils;

import com.google.gson.Gson;
import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import com.karateman.discordlink.verification.JsonVerification;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SetupUtils {

    private DiscordLinkPlugin plugin;

    public SetupUtils(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    public void setupVerificationChannel() {
        if(plugin.getConfig().getBoolean("verification-module")) {
            if(!plugin.getConfig().getString("verification-channel-id").equalsIgnoreCase("123")) {
                try {
                    TextChannel channel = DiscordLinkPlugin.getBot().getJda().getTextChannelById(plugin.getConfig().getString("verification-channel-id"));
                    if(plugin.getConfig().getString("verification-message").equals("123")) {
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.addField("Verification", "React below to verify\nMake sure that you can receive DMs", false);
                        channel.sendMessage(embed.build()).queue((msg) -> {
                            plugin.getConfig().set("verification-message", msg.getId());
                            plugin.saveConfig();
                            msg.addReaction("✔").queue();
                        });
                    }
                } catch (Exception channelNotFound) {
                    channelNotFound.printStackTrace();
                }
            }

            if(plugin.getConfig().getString("verification-storage").equalsIgnoreCase("json")) {
                try {
                    File verificationsFile = new File("plugins/DiscordLink/verifications.json");

                    if(verificationsFile.exists()) return;

                    verificationsFile.createNewFile();

                    JsonVerification setupJson = new JsonVerification();
                    setupJson.registerCode("temp", "temp1");
                    setupJson.verifyUser("temp", "temp1");

                    OutputStream outputStream = new FileOutputStream(new File("plugins/DiscordLink/verifications.json"));
                    outputStream.write(new Gson().toJson(new JsonVerification()).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
