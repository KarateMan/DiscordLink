package com.karateman.discordlink.spigot.events;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import com.karateman.discordlink.spigot.util.RankUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SpigotChatEvent implements Listener {

    private DiscordLinkPlugin plugin;

    public SpigotChatEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        TextChannel channel = DiscordLinkPlugin.getBot().getJda().getTextChannelById(plugin.getConfig().getString("gamechat-id"));

        String format = plugin.getConfig().getString("gamechat-discord-format");
        if(format.contains("%prefix%")) format = format.replace("%prefix%", RankUtil.getRankPrefix(event.getPlayer()));
        if(format.contains("%username%")) format = format.replace("%username%", event.getPlayer().getName());
        if(format.contains("%message%")) format = format.replace("%message%", event.getMessage());

        channel.sendMessage(format).queue();
    }

}
