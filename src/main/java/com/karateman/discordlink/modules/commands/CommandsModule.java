package com.karateman.discordlink.modules.commands;

import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.commands.events.CommandsDiscordChatEvent;
import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.DiscordLinkPlugin;

import java.util.logging.Level;

public class CommandsModule implements Module {

    private DiscordLinkPlugin plugin;

    public CommandsModule(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean setup() {

        return false;
    }

    @Override
    public boolean isSetup() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Config.COMMANDS_MODULE.getAsBoolean();
    }

    @Override
    public void runStartup() {
        if(!isEnabled()) return;

        plugin.getJda().addEventListener(new CommandsDiscordChatEvent(plugin));
        plugin.getLogger().log(Level.INFO, "Enabling Commands Module");
    }

    @Override
    public void runShutdown() {
    }

    public boolean validChannel(String channel) {
        String commandChannel = Config.COMMANDS_CHANNEL.getAsString();
        if(commandChannel.equalsIgnoreCase("any")) return true;
        if(commandChannel.equalsIgnoreCase(channel)) return true;
        if(commandChannel.equalsIgnoreCase("gamechat")) {
            if(channel.equalsIgnoreCase(plugin.getGamechatModule().getChannel())) return true;
        }

        return false;
    }
}
