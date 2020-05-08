package com.karateman.discordlink.modules.gamechat;

import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.modules.gamechat.events.GamechatDiscordChatEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotChatEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotDeathEvent;
import com.karateman.discordlink.modules.gamechat.events.GamechatSpigotLoginLogoutEvent;
import com.karateman.discordlink.DiscordLinkPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

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
        String message = Config.GAMECHAT_START_FORMAT.getAsString();
        if(plugin.placeholderApiEnabled()) {
            message = PlaceholderAPI.setPlaceholders(null, Config.GAMECHAT_START_FORMAT.getAsString());
        }
        plugin.getDiscordUtils().sendMessage(message, getChannel());
    }

    private void sendStopMessage() {
        String message = Config.GAMECHAT_STOP_FORMAT.getAsString();
        if(plugin.placeholderApiEnabled()) {
            message = PlaceholderAPI.setPlaceholders(null, Config.GAMECHAT_STOP_FORMAT.getAsString());
        }
        plugin.getDiscordUtils().sendMessage(message, getChannel());
    }

    public void sendLoginMessage(Player player , String prefix) {
        String format = Config.GAMECHAT_LOGIN_FORMAT.getAsString();

        if(plugin.placeholderApiEnabled()) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        } else {
            if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
            if(format.contains("%username%")) format = format.replace("%username%", player.getName());
        }

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

        plugin.getDiscordUtils().sendMessage(message.toString(), getChannel());
    }

    public void sendLogoutMessage(Player player, String prefix) {
        String format = Config.GAMECHAT_LOGOUT_FORMAT.getAsString();

        if(plugin.placeholderApiEnabled()) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        } else {
            if(format.contains("%prefix%")) format = format.replace("%prefix%", prefix);
            if(format.contains("%username%")) format = format.replace("%username%", player.getName());
        }

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

        plugin.getDiscordUtils().sendMessage(message.toString(), getChannel());
    }
}
