package com.karateman.discordlink.modules.gamechat;

import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.modules.gamechat.events.GamechatDiscordChatEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotChatEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotDeathEvent;
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
                plugin.getConfig().set(Config.GAMECHAT_ID.getId(), channel.getId());
                plugin.saveConfig();
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
        return Config.GAMECHAT_MODULE.getAsBoolean();
    }

    @Override
    public void runStartup() {
        if(isEnabled() && isSetup()) {
            plugin.getLogger().log(Level.INFO, "Enabling Gamechat Module");
            plugin.getJda().addEventListener(new GamechatDiscordChatEvent(plugin));
            plugin.getServer().getPluginManager().registerEvents(new GamechatSpigotChatEvent(plugin), plugin);
            if(startStopMessages()) sendStartMessage();
            if(loginLogoutMessages()) plugin.getServer().getPluginManager().registerEvents(new GamechatSpigotLoginLogoutEvent(plugin), plugin);
            if(Config.GAMECHAT_DEATH_MESSAGES.getAsBoolean()) plugin.getServer().getPluginManager().registerEvents(new GamechatSpigotDeathEvent(plugin), plugin);
        }
    }

    @Override
    public void runShutdown() {
        if(startStopMessages() && isSetup() && isEnabled()) sendStopMessage();
    }

    public String getChannel() {
        return Config.GAMECHAT_ID.getAsString();
    }

    public boolean startStopMessages() {
        return Config.GAMECHAT_STARTSTOP_MESSAGES.getAsBoolean();
    }

    public boolean loginLogoutMessages() {
        return Config.GAMECHAT_LOGINLOGOUT_MESSAGES.getAsBoolean();
    }

    private void sendStartMessage() {
        plugin.getDiscordUtils().sendMessage(Config.GAMECHAT_START_FORMAT.getAsString(), getChannel());
    }

    private void sendStopMessage() {
        plugin.getDiscordUtils().sendMessage(Config.GAMECHAT_STOP_FORMAT.getAsString(), getChannel());
    }

    public void sendLoginMessage(String username, String prefix) {
        String format = Config.GAMECHAT_LOGIN_FORMAT.getAsString();

        if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
        if(format.contains("%username%")) format = format.replace("%username%", username);

        plugin.getDiscordUtils().sendMessage(format, getChannel());
    }

    public void sendLogoutMessage(String username, String prefix) {
        String format = Config.GAMECHAT_LOGOUT_FORMAT.getAsString();

        if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
        if(format.contains("%username%")) format = format.replace("%username%", username);

        plugin.getDiscordUtils().sendMessage(format, getChannel());
    }
}
