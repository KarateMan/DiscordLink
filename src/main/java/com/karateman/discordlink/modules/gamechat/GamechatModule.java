package com.karateman.discordlink.modules.gamechat;

import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.modules.gamechat.events.GamechatDiscordEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotChatEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotLoginLogoutEvent;
import com.karateman.discordlink.DiscordLinkPlugin;

import java.util.logging.Level;

public class GamechatModule implements Module {

    private DiscordLinkPlugin plugin;

    public GamechatModule(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean setup() {
        if(isSetup()) return false;

        plugin.getJda().getGuilds().get(0).createCategory("discord link").queue((category) -> {
            category.createTextChannel("gamechat").queue((channel) -> {
                plugin.getConfig().set("gamechat-id", channel.getId());
            });
        });

        runStartup();

        return true;
    }

    @Override
    public boolean isSetup() {
        if(!isEnabled()) return false;
        if(getChannel().equalsIgnoreCase("123")) return false;

        return true;
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("gamechat-module");
    }

    @Override
    public void runStartup() {
        if(isEnabled() && isSetup()) {
            plugin.getLogger().log(Level.INFO, "Enabling Gamechat Module");
            plugin.getJda().addEventListener(new GamechatDiscordEvent(plugin));
            plugin.getServer().getPluginManager().registerEvents(new GamechatSpigotChatEvent(plugin), plugin);
            if(startStopMessages()) sendStartMessage();
            if(loginLogoutMessages()) plugin.getServer().getPluginManager().registerEvents(new GamechatSpigotLoginLogoutEvent(plugin), plugin);
        }
    }

    @Override
    public void runShutdown() {
        if(startStopMessages() && isSetup() && isEnabled()) sendStopMessage();
    }

    public String getChannel() {
        return plugin.getConfig().getString("gamechat-id");
    }

    public boolean startStopMessages() {
        return plugin.getConfig().getBoolean("gamechat-startstop-messages");
    }

    public boolean loginLogoutMessages() {
        return plugin.getConfig().getBoolean("gamechat-loginlogout-messages");
    }

    private void sendStartMessage() {
        plugin.getDiscordUtils().sendMessage(plugin.getConfig().getString("gamechat-start-message"), getChannel());
    }

    private void sendStopMessage() {
        plugin.getDiscordUtils().sendMessage(plugin.getConfig().getString("gamechat-stop-message"), getChannel());
    }

    public void sendLoginMessage(String username, String prefix) {
        String format = plugin.getConfig().getString("gamechat-login-message");

        if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
        if(format.contains("%username%")) format = format.replace("%username%", username);

        plugin.getDiscordUtils().sendMessage(format, getChannel());
    }

    public void sendLogoutMessage(String username, String prefix) {
        String format = plugin.getConfig().getString("gamechat-logout-message");

        if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
        if(format.contains("%username%")) format = format.replace("%username%", username);

        plugin.getDiscordUtils().sendMessage(format, getChannel());
    }
}
