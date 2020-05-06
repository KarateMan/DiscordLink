package com.karateman.discordlink.modules.util;

import com.karateman.discordlink.DiscordLinkPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DiscordUtils {

    private JDA jda;
    private DiscordLinkPlugin plugin;

    public DiscordUtils(JDA jda, DiscordLinkPlugin plugin) {
        this.jda = jda;
        this.plugin = plugin;
    }

    public final EmbedBuilder getDefaultEmbed(String title) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter("DiscordLink");
        embedBuilder.setTitle(title);
        embedBuilder.setColor(3355647);
        return embedBuilder;
    }

    public Message sendMessage(String message, String channelId) {
        return jda.getTextChannelById(channelId).sendMessage(message).complete();
    }

    public Message sendMessage(MessageEmbed embed, String channelId) {
        return jda.getTextChannelById(channelId).sendMessage(embed).complete();
    }

    public void react(String emoji, Message message) {
        message.addReaction(emoji).queue();
    }

}
