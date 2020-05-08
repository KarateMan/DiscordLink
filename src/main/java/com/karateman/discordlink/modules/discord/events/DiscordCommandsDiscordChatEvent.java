package com.karateman.discordlink.modules.discord.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DiscordCommandsDiscordChatEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public DiscordCommandsDiscordChatEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof MessageReceivedEvent)) return;
        MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;

        if(!plugin.getDiscordCommandsModule().isEnabled()) return;

        String prefix = Config.DISCORD_COMMANDS_PREFIX.getAsString();
        String message = event.getMessage().getContentRaw();

        if(!message.startsWith(prefix)) return;
        message = message.replace(prefix, "");

        if(!plugin.getDiscordCommandsModule().validChannel(event.getChannel().getId())) return;

        if(message.equalsIgnoreCase("list")) {
            if(!Config.DISCORD_COMMANDS_LIST.getAsBoolean()) return;
            new BukkitRunnable() {
                @Override
                public void run() {
                    EmbedBuilder embed = plugin.getDiscordUtils().getDefaultEmbed("Discord Link");

                    String players = "";
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        String name = player.getName().contains("_") ? player.getName().replace("_", "\\_") : player.getName();
                        players += name + "\n";
                    }

                    embed.addField("Online Players", players, false);
                    plugin.getDiscordUtils().sendMessage(embed.build(), event.getChannel().getId());
                }
            }.runTask(plugin);
        }
    }
}
