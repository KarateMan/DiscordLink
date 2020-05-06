package com.karateman.discordlink.modules.verification;

import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.DiscordLinkPlugin;

public class VerificationModule implements Module {

    private DiscordLinkPlugin plugin;

    public VerificationModule(DiscordLinkPlugin plugin) {
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
        return plugin.getConfig().getBoolean("verification-module");
    }

    @Override
    public void runStartup() {

    }

    @Override
    public void runShutdown() {

    }
}
