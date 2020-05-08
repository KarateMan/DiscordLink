package com.karateman.discordlink.modules.discord;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.modules.discord.events.DiscordCommandsDiscordChatEvent;

public class DiscordCommandsModule implements Module {

    private DiscordLinkPlugin plugin;

    public DiscordCommandsModule(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean setup() {
        return true;
    }

    @Override
    public boolean isSetup() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Config.DISCORD_COMMANDS_MODULE.getAsBoolean();
    }

    @Override
    public void runStartup() {
        plugin.getJda().addEventListener(new DiscordCommandsDiscordChatEvent(plugin));
    }

    @Override
    public void runShutdown() {

    }

    public boolean validChannel(String channel) {
        String commandChannel = Config.DISCORD_COMMANDS_CHANNEL.getAsString();
        if(commandChannel.equalsIgnoreCase("any")) return true;
        if(commandChannel.equalsIgnoreCase(channel)) return true;
        if(commandChannel.equalsIgnoreCase("gamechat")) {
            if(channel.equalsIgnoreCase(plugin.getGamechatModule().getChannel())) return true;
        }

        return false;
    }
}
