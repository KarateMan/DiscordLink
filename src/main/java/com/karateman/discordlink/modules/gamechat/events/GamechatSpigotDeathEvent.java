package com.karateman.discordlink.modules.gamechat.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GamechatSpigotDeathEvent implements Listener {

    private DiscordLinkPlugin plugin;

    public GamechatSpigotDeathEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String format = Config.GAMECHAT_DEATH_FORMAT.getAsString();

        if(plugin.placeholderApiEnabled()) {
            format = PlaceholderAPI.setPlaceholders(event.getEntity(), format);
        } else {
            if(format.contains("%username%")) format = format.replace("%username%", event.getEntity().getName());
            if(format.contains("%default%")) format = format.replace("%default%", event.getDeathMessage());
        }

        plugin.getDiscordUtils().sendMessage(format, plugin.getGamechatModule().getChannel());
    }
}
