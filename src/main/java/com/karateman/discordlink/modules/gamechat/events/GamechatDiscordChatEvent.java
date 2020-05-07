package com.karateman.discordlink.modules.gamechat.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GamechatDiscordChatEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public GamechatDiscordChatEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof MessageReceivedEvent)) return;
        MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;

        if(event.getAuthor().isBot()) return;
        if(!event.getChannel().getId().equalsIgnoreCase(plugin.getGamechatModule().getChannel())) return;
        if(plugin.getCommandsModule().isEnabled()
                && (event.getMessage().getContentRaw().startsWith(Config.COMMANDS_PREFIX.getAsString())
                || event.getMessage().getContentRaw().startsWith(Config.COMMANDS_SILENT_PREFIX.getAsString()))) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                String format = Config.GAMECHAT_SERVER_FORMAT.getAsString();
                if(format.contains("%discord_nick%")) format = format.replace("%discord_nick%", event.getMember().getEffectiveName());
                if(format.contains("%message%")) format = format.replace("%message%", event.getMessage().getContentDisplay());

                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
                }
            }
        }.runTask(plugin);
    }
}
