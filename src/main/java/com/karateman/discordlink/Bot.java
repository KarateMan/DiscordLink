package com.karateman.discordlink;

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
            String serverName = plugin.getConfig().getString("server-name");
            String serverIp = plugin.getConfig().getString("server-ip");
            JDA jda = new JDABuilder(plugin.getConfig().getString("token")).setActivity(Activity.playing(serverName + " at " + serverIp)).build().awaitReady();
            this.jda = jda;
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        }

    }

    public JDA getJda() {
        return jda;
    }
}
