package com.karateman.discordlink.spigot.events;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import com.karateman.discordlink.spigot.util.RankUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpigotJoinLeaveEvent implements Listener {

    private DiscordLinkPlugin plugin;

    public SpigotJoinLeaveEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(plugin.getConfig().getBoolean("gamechat-loginlogout-messages")) {
            TextChannel channel = DiscordLinkPlugin.getBot().getJda().getTextChannelById(plugin.getConfig().getString("gamechat-id"));

            String format = plugin.getConfig().getString("gamechat-login-message");
            if(format.contains("%prefix%")) format = format.replace("%prefix%", RankUtil.getRankPrefix(event.getPlayer()));
            if(format.contains("%username%")) format = format.replace("%username%", event.getPlayer().getName());

            channel.sendMessage(format).queue();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(plugin.getConfig().getBoolean("gamechat-loginlogout-messages")) {
            TextChannel channel = DiscordLinkPlugin.getBot().getJda().getTextChannelById(plugin.getConfig().getString("gamechat-id"));

            String format = plugin.getConfig().getString("gamechat-logout-message");
            if(format.contains("%prefix%")) format = format.replace("%prefix%", RankUtil.getRankPrefix(event.getPlayer()));
            if(format.contains("%username%")) format = format.replace("%username%", event.getPlayer().getName());

            channel.sendMessage(format).queue();
        }
    }

}
