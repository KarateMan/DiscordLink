package com.karateman.discordlink.modules.commands;

import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.DiscordLinkPlugin;

public class CommandsModule implements Module {

    private DiscordLinkPlugin plugin;

    public CommandsModule(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {

    }

    @Override
    public boolean isSetup() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("commands-module");
    }

    @Override
    public void runStartup() {

    }

    @Override
    public void runShutdown() {

    }
}
