package com.karateman.discordlink.modules.gamechat.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.modules.util.RankUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GamechatSpigotLoginLogoutEvent implements Listener {

    private DiscordLinkPlugin plugin;

    public GamechatSpigotLoginLogoutEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getGamechatModule().sendLoginMessage(event.getPlayer(), plugin.getRankUtil().getRankPrefix(event.getPlayer()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        plugin.getGamechatModule().sendLogoutMessage(event.getPlayer(), plugin.getRankUtil().getRankPrefix(event.getPlayer()));
    }
}
