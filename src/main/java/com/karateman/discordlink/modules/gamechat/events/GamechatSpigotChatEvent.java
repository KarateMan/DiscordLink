package com.karateman.discordlink.modules.gamechat.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.util.RankUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GamechatSpigotChatEvent implements Listener {

    private DiscordLinkPlugin plugin;

    public GamechatSpigotChatEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        if(!Config.GAMECHAT_INGAME_MESSAGES.getAsBoolean()) return;
        if(event.isCancelled()) return;

        String format = Config.GAMECHAT_DISCORD_FORMAT.getAsString();
        if(plugin.placeholderApiEnabled()) {
            format = PlaceholderAPI.setPlaceholders(event.getPlayer(), format);
        } else {
            if(format.contains("%prefix%")) format = format.replace("%prefix%", plugin.getRankUtil().getRankPrefix(event.getPlayer()));
            if(format.contains("%username%")) format = format.replace("%username%", event.getPlayer().getName());
            format = format.replaceAll("_", "\\_");
        }
        if(format.contains("%message%")) format = format.replace("%message%", event.getMessage());

        StringBuilder message = new StringBuilder();
        boolean removeNext = false;
        for(char c : format.toCharArray()) {
            if(removeNext) {
                removeNext = false;
                continue;
            }

            if(c == '§' || c == '&') {
                removeNext = true;
                continue;
            }

            message.append(c);
        }

        plugin.getDiscordUtils().sendMessage(message.toString(), plugin.getGamechatModule().getChannel());
    }
}
