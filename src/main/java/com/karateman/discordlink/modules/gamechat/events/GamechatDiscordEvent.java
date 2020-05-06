package com.karateman.discordlink.modules.gamechat.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GamechatDiscordEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public GamechatDiscordEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof MessageReceivedEvent)) return;
        MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;

        if(event.getAuthor().isBot()) return;
        if(!event.getChannel().getId().equalsIgnoreCase(plugin.getGamechatModule().getChannel())) return;

        String format = plugin.getConfig().getString("gamechat-server-format");
        if(format.contains("%discord_nick%")) format = format.replace("%discord_nick%", event.getMember().getEffectiveName());
        if(format.contains("%message%")) format = format.replace("%message%", event.getMessage().getContentDisplay());

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
    }
}
