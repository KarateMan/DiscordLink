package com.karateman.discordlink.discord;

import com.karateman.discordlink.discord.events.DiscordChatEvent;
import com.karateman.discordlink.discord.events.DiscordReactEvent;
import com.karateman.discordlink.discord.utils.SetupUtils;
import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private JDA jda;
    private DiscordLinkPlugin plugin;

    public Bot(DiscordLinkPlugin plugin) {
        this.plugin = plugin;

        try {
            JDA jda = new JDABuilder(plugin.getConfig().getString("token")).setActivity(Activity.playing("Test Server at TestServer.com")).build().awaitReady();
            this.jda = jda;
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        }

        jda.addEventListener(new DiscordChatEvent(plugin));
        jda.addEventListener(new DiscordReactEvent(plugin));
    }

    public JDA getJda() {
        return jda;
    }
}
