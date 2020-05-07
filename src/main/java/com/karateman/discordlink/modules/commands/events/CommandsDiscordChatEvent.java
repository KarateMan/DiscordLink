package com.karateman.discordlink.modules.commands.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.commands.data.DiscordCommandSender;
import com.karateman.discordlink.modules.commands.data.DiscordSilentCommandSender;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandsDiscordChatEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public CommandsDiscordChatEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof MessageReceivedEvent)) return;
        MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;

        if(event.getAuthor().isBot()) return;
        if(!plugin.getCommandsModule().isEnabled()) return;
        if(!plugin.getCommandsModule().validChannel(event.getChannel().getId())) return;

        if(!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
            plugin.getDiscordUtils().sendMessage(
                    plugin.getDiscordUtils().getDefaultEmbed("Discord Link")
                            .addField("__**No Permission**__", "You don't have permission to run server commands!", false).build(), event.getChannel().getId());
            return;
        }

        String prefix = Config.COMMANDS_PREFIX.getAsString();
        String silentPrefix = Config.COMMANDS_SILENT_PREFIX.getAsString();

        if(!(event.getMessage().getContentRaw().startsWith(prefix) || event.getMessage().getContentRaw().startsWith(silentPrefix))) return;

        String command = event.getMessage().getContentRaw().startsWith(prefix)
                ? event.getMessage().getContentRaw().replaceFirst(prefix, "")
                : event.getMessage().getContentRaw().replaceFirst(silentPrefix, "");

        if(event.getMessage().getContentRaw().startsWith(prefix)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(new DiscordCommandSender(plugin.getServer(), event.getChannel().getId(), plugin, command), command);
                }
            }.runTask(plugin);
        } else if(event.getMessage().getContentRaw().startsWith(silentPrefix)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(new DiscordSilentCommandSender(plugin.getServer(), event.getAuthor().openPrivateChannel(), plugin, command), command);
                }
            }.runTask(plugin);
        }
    }
}
